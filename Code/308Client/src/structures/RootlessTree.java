package structures;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author TJ
 */
public class RootlessTree<T> {
    private Vector<Node> roots;
    private int size=0;
    public RootlessTree() {
        roots=new Vector<Node>();
    }
    private RootlessTree(Vector<Node> r) {
        roots=r;
        size=count(r);
    }
    private RootlessTree(Node r) {
        roots=new Vector<Node>();
        roots.add(r);
        size=1+count(r.children);
    }
    public RootlessTree(Collection<T> data) {
        for(T d : data) {
            addRoot(d);
        }
    }
    public static void mergeTrees(RootlessTree to, RootlessTree addition, Object parent, int level) {
       Vector c=addition.getRoots();
       int j=0;
        for(Object c2 : c) {
            int i;
            if((i=to.dataExists(c2))!=-1 && level>i) {
                    to.changeParent(c2, parent);
            } else {
                    to.addChild(parent, c2);
                    mergeTrees(to, addition.getSubtree(j), c2, level+1);
            }
            j++;
        }

    }
    public RootlessTree<T> getTreeWithRoot(int index) {
        return new RootlessTree<T>(roots.get(index));
    }
    public int count() {return count(roots);}
    private int count(Vector<Node> n) {
        int count=0;
        for(Node n2 : n) {
            count+=1+count(n2.children);
        }
        return count;
    }
    public int getMaxLevel() {
        return getMaxLevel(roots);
    }
    public int getMaxLevel(int i) {
        return getMaxLevel(roots.get(i));
    }
    private int getMaxLevel(Node n) {
            int max=1;
           for(Node n2 : n.children) {
               int tmp=getMaxLevel(n2)+1;
               if(tmp>max) {
                   max=tmp;
               }
           }
           return max;
    }
    private int getMaxLevel(Vector<Node> n) {
           int max=0;
           for(Node n2 : n) {
               int tmp=getMaxLevel(n2.children)+1;
               if(tmp>max) {
                   max=tmp;
               }
           }
           return max;
    }
    public int size() {return size;}
    public void addRoot(T data) {
            roots.add(new Node(null, data));
            size++;
    }
    public void addChild(T parent, T childData) {
        if(parent==null) {
            addRoot(childData);
        } else {
            Node n=find(parent, roots);
            if(n!=null) {
            n.children.add(new Node(n, childData));
                    size++;
            }
        }
    }
    public void addChildren(T parent, Collection<T> childData) {
        Node n=find(parent, roots);
        for(T data : childData) {
            n.children.add(new Node(n, data));
                    size++;
        }
    }
    public int removeData(T data) {
        Node n=find(data, roots);
        if(n.children.size()>0) {return -1;} else {
            n.parent.children.remove(n);
            n.parent=null;
            size--;
            return 0;
        }

    }
    public RootlessTree<T> getSubtree(int index) {return new RootlessTree(roots.get(index).children);}
    public int dataExists(T data) {
        Node n=find(data, roots);
        return (n==null?-1:n.level);
    }
    public void changeParent(T data, T newParent) {
        Node parent=find(newParent, roots);
        Node child=find(data, roots);
        if( child!=null) {//if data dosnt exist, nothing to change
            if(child.parent!=null) {
                    child.parent.children.remove(child);
            }
             child.parent=parent;

            if(parent!=null) {
               parent.children.add(child);
            } else {
                roots.add(child);
            }
        }
    }
    public int numRoots() {return roots.size();}
    public Vector<T> getRoots(){
        Vector<T> rootData =new Vector<T>();
        for(Node n : roots) {
            rootData.add(n.data);
        }
        return rootData;
    }
    public void addTree(RootlessTree<T> t) {
        roots.addAll(t.roots);
        size+=t.size();
    }
    public void addTree(RootlessTree<T> t, T parent) {
        Node n=find(parent, roots);
        if(n!=null && t!=null) {
                for(Node n2 : t.roots) {
                    n2.parent=n;
                    n.children.add(n2);
                }
                size+=t.size();
                updateLevels(n.children);
        }
    }
    public boolean equals(RootlessTree<T> a) {
        return equals(roots, a.roots);
    }
    private boolean equals(Vector<Node> n, Vector<Node> n2) {
        if(n.size()!=n2.size()){return false;}
        Iterator<Node> it1=n.iterator();
        Iterator<Node> it2=n2.iterator();
        while(it1.hasNext()) {
            Node next1=it1.next();
            Node next2=it2.next();
            if(!next1.data.equals(next2.data) || !equals(next1.children, next2.children))
            {
                return false;
            }
        }
        return true;
    }
    private Node find(T data, Vector<Node> children) {
        for(Node t : children) {
            if(t.data.equals(data)) {
                return t;
            }else if(t.children.size()>0) {
                    Node n= find(data, t.children);
                    if(n!=null) {return n;}
            }
        }
        return null;
    }
    public void upadteLevels() {updateLevels(roots);}
    private void updateLevels(Vector<Node> nodes) {
        for(Node n : nodes) {
            if(n.parent!=null){n.level=n.parent.level+1;}
            updateLevels(n.children);
        }
    }
    class Node {
        Node parent;
        T data;
        int level;
        Vector<Node> children;
        Node(Node parent, T data) {
            children=new Vector<Node>();
         this.data=data;
         this.parent=parent;
         if(parent==null){
             level=0;
         } else {
            level=parent.level;
         }
        }

        public void addChild(Node n) {
            children.add(n);
        }
    }
}
