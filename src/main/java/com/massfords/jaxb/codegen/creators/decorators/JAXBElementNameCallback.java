package com.massfords.jaxb.codegen.creators.decorators;

import com.massfords.jaxb.VisitorPlugin;
import com.massfords.jaxb.codegen.CodeGenOptions;
import com.sun.codemodel.JAnnotationValue;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.massfords.jaxb.codegen.creators.Utils.annotateGenerated;


public final class JAXBElementNameCallback {

    private JAXBElementNameCallback() {
    }

    private static final String SETTER = "setJAXBElementName";
    private static final String GETTER = "getJAXBElementName";
    private static final String FIELD = "jaxbElementName";

    public static void create(VisitorPlugin.InitialState state, CodeGenOptions options) {
        Outline outline = state.outline();
        JDefinedClass clazz = outline.getClassFactory().createInterface(options.packageForVisitor(), "Named", null);
        annotateGenerated(clazz, options);
        clazz.method(JMod.PUBLIC, void.class, SETTER).param(QName.class, "name");
        clazz.method(JMod.PUBLIC, QName.class, GETTER);

        Set<ClassOutline> named = onlyNamed(outline, state.allClasses(), options);

        JClass jaxbElementClass = outline.getCodeModel().ref(options.getJAXBElementClass())
                .narrow(outline.getCodeModel().ref(Object.class).wildcard());

        named.forEach(classOutline -> {
            JDefinedClass implClass = classOutline.implClass;
            // implement the interface
            implClass._implements(clazz);

            // code: @XmlTransient
            // code: private QName jaxbElementName;
            implClass.field(JMod.PRIVATE, QName.class, FIELD).annotate(options.getXmlTransient());

            // code:  public void setJAXBElementName(QName name) {
            // code:      this.jaxbElementName = name;
            // code:  }
            JMethod setter = implClass.method(JMod.PUBLIC, void.class, SETTER);
            setter.param(QName.class, "name");
            setter.body().assign(JExpr._this().ref(FIELD), JExpr.ref("name"));
            // code: public QName getJAXBElementName() {
            // code:    return this.jaxbElementName;
            // code: }
            JMethod getter = implClass.method(JMod.PUBLIC, QName.class, GETTER);
            getter.body()._return(JExpr._this().ref(FIELD));

            // code: public void afterUnmarshal(Unmarshaller u, Object parent) {
            // code:    if (parent instanceof JAXBElement) {
            // code:       this.jaxbElementName = ((JAXBElement)parent).getName()
            // code:    }
            // code: }
            JMethod after = implClass.method(JMod.PUBLIC, void.class, "afterUnmarshal");
            after.param(options.getUnmarshallerClass(), "u");
            after.param(Object.class, "parent");
            after.body()._if(JExpr.ref("parent")._instanceof(jaxbElementClass))
                    ._then().assign(JExpr._this().ref(FIELD),
                            JExpr.invoke(JExpr.cast(jaxbElementClass, JExpr.ref("parent")), "getName"));
        });
    }

    private static Set<JDefinedClass> identifyCandidates(Outline outline, CodeGenOptions options) {

        // phase one: identify all the candidates and update the ObjectFactories with the setter call
        // phase two: only include instances that don't have a JDefinedClass as their super
        // phase three: add all the markings

        JClass qNameClass = outline.getCodeModel().ref(QName.class);
        Set<JDefinedClass> candidates = new LinkedHashSet<>();
        outline.getAllPackageContexts().forEach(po -> {
            // locate the object factory
            JDefinedClass of = outline.getPackageContext(po._package()).objectFactory();
            of.methods()
                    .stream()
                    .filter(method -> method.type().binaryName().startsWith(options.getJAXBElementClass().getName()))
                    .filter(method -> ((JClass) method.type()).getTypeParameters().size() == 1)
                    .filter(method -> ((JClass) method.type()).getTypeParameters().get(0) instanceof JDefinedClass)
                    .filter(method -> !((JClass) method.type()).getTypeParameters().get(0).isAbstract())
                    .forEach(method -> {
                        JType retType = method.type();
                        JClass clazz = (JClass) retType;
                        List<JClass> typeParameters = clazz.getTypeParameters();
                        Class<?> xmlElementDecl = options.getXmlElementDecl();
                        method.annotations().stream()
                                .filter(au -> au.getAnnotationClass().fullName().equals(xmlElementDecl.getName()))
                                .map(au -> new QName(
                                        annotationValueToString(au.getAnnotationMembers().get("namespace")),
                                        annotationValueToString(au.getAnnotationMembers().get("name"))))
                                .findFirst()
                                .ifPresent(qn -> {
                                    method.body().pos(0);
                                    method.body().invoke(method.params().get(0), SETTER)
                                            .arg(JExpr._new(qNameClass)
                                                    .arg(qn.getNamespaceURI())
                                                    .arg(qn.getLocalPart()));
                                });
                        JDefinedClass dc = (JDefinedClass) typeParameters.get(0);
                        candidates.add(dc);
                    });
        });
        return candidates;
    }

    private static Set<ClassOutline> filterSubclasses(Collection<ClassOutline> all, Set<JDefinedClass> candidates) {
        // mapping the class to the outline
        Map<JDefinedClass, ClassOutline> classToOutline = all.stream()
                .collect(Collectors.toMap(co -> co.implClass, Function.identity()));

        Set<ClassOutline> filtered = new LinkedHashSet<>();
        all.forEach(co -> {
            if (candidates.contains(co.implClass)) {
                JDefinedClass jdc = co.implClass;
                JDefinedClass base = jdc._extends() instanceof JDefinedClass ? (JDefinedClass) jdc._extends() : null;
                if (base != null) {
                    while (base._extends() instanceof JDefinedClass) {
                        base = (JDefinedClass) base._extends();
                    }
                    filtered.add(classToOutline.get(base));
                } else {
                    filtered.add(co);
                }
            }
        });
        return filtered;
    }


    private static Set<ClassOutline> onlyNamed(Outline outline, Collection<ClassOutline> sorted,
                                               CodeGenOptions options) {
        Set<JDefinedClass> candidates = identifyCandidates(outline, options);
        return filterSubclasses(sorted, candidates);
    }

    private static String annotationValueToString(JAnnotationValue ns) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        JFormatter jf = new JFormatter(pw, "");
        ns.generate(jf);
        pw.flush();
        String s = sw.toString();
        return s.substring(1, s.length() - 1);
    }
}
