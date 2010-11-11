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
    public RootlessTree(Collection<T> data) {
        for(T d : data) {
            addRoot(d);
        }
    }
    public int size() {return size;}
    public void addRoot(T data) {
            roots.add(new Node(null, data));
            size++;
    }
    public void addChild(T parent, T childData) {
        Node n=find(parent, roots);
        n.children.add(new Node(n, childData));
                size++;
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
    public boolean dataExists(T data) {
        return find(data, roots).data.equals(data);
    }
    private Vector<Node> getRoots(){
        return roots;
    }
    public void addTree(RootlessTree<T> t) {
        roots.addAll(t.getRoots());
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

    class Node {
        Node parent;
        T data;
        Vector<Node> children;
        Node(Node parent, T data) {
            children=new Vector<Node>();
         this.data=data;
         this.parent=parent;
        }
        public void addChild(Node n) {
            children.add(n);
        }
    }
}
