package org.example.visitor;

import jakarta.annotation.Generated;

import org.example.imported.ImportedData;
import org.example.imported.ImportedType;
import org.example.simple.ChoiceElement;
import org.example.simple.ComplexObject;
import org.example.simple.HasJAXBElement;
import org.example.simple.Problem;
import org.example.simple.Recursive;
import org.example.simple.TSimpleRequest;
import org.example.simple.TSimpleResponse;

@Generated("Generated by jaxb-visitor")
public class TraversingVisitor<R, E extends Throwable>
        implements Visitor<R, E> {

    private boolean traverseFirst;
    private Visitor<R, E> visitor;
    private Traverser<E> traverser;
    private TraversingVisitorProgressMonitor progressMonitor;

    public TraversingVisitor(Traverser<E> aTraverser, Visitor<R, E> aVisitor) {
        traverser = aTraverser;
        visitor = aVisitor;
    }

    public boolean getTraverseFirst() {
        return traverseFirst;
    }

    public void setTraverseFirst(boolean aVisitor) {
        traverseFirst = aVisitor;
    }

    public Visitor<R, E> getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor<R, E> aVisitor) {
        visitor = aVisitor;
    }

    public Traverser<E> getTraverser() {
        return traverser;
    }

    public void setTraverser(Traverser<E> aVisitor) {
        traverser = aVisitor;
    }

    public TraversingVisitorProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    public void setProgressMonitor(TraversingVisitorProgressMonitor aVisitor) {
        progressMonitor = aVisitor;
    }

    @Override
    public R visit(ImportedData aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(ImportedType aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(ImportedType.Message aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(ChoiceElement aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(ComplexObject aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(ComplexObject.LocalElement aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(HasJAXBElement aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(Problem aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(Recursive aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(TSimpleRequest aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

    @Override
    public R visit(TSimpleResponse aBean)
            throws E {
        if (traverseFirst == true) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        R returnVal;
        returnVal = aBean.accept(getVisitor());
        if (progressMonitor != null) {
            progressMonitor.visited(aBean);
        }
        if (traverseFirst == false) {
            getTraverser().traverse(aBean, this);
            if (progressMonitor != null) {
                progressMonitor.traversed(aBean);
            }
        }
        return returnVal;
    }

}
