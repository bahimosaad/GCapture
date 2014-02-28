package com.indexing;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a node of the Tree<T> class. The Node<T> is also a container, and
 * can be thought of as instrumentation to determine the location of the type T
 * in the Tree<T>.
 */
public class Node<T> {
 
    public T data;
    public Set<Node<T>> children;
 
    /**
     * Default ctor.
     */
    public Node() {
        super();
    }
 
    /**
     * Convenience ctor to create a Node<T> with an instance of T.
     * @param data an instance of T.
     */
    public Node(T data) {
        this();
        setData(data);
    }
     
    /**
     * Return the children of Node<T>. The Tree<T> is represented by a single
     * root Node<T> whose children are represented by a Set<Node<T>>. Each of
     * these Node<T> elements in the Set can have children. The getChildren()
     * method will return the children of a Node<T>.
     * @return the children of Node<T>
     */
    public Set<Node<T>> getChildren() {
        if (this.children == null) {
            return new LinkedHashSet<Node<T>>();
        }
        return this.children;
    }
 
    /**
     * Sets the children of a Node<T> object. See docs for getChildren() for
     * more information.
     * @param children the Set<Node<T>> to set.
     */
    public void setChildren(Set<Node<T>> children) {
        this.children = children;
    }
 
    /**
     * Returns the number of immediate children of this Node<T>.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }
     
    /**
     * Adds a child to the list of children for this Node<T>. The addition of
     * the first child will create a new Set<Node<T>>.
     * @param child a Node<T> object to set.
     */
    public boolean addChild(Node<T> child) {
        if (children == null) {
            children = new LinkedHashSet<Node<T>>();
        }
        return children.add(child);
    }

    public Node<T> getNode(Node<T> node){
        Node<T> requiredNode = null;
        for (Iterator<Node<T>> it = children.iterator(); it.hasNext();) {
            Node<T> node1 = it.next();
            if (node.equals(node1)){
                requiredNode = node1;
                break;
            }
        }
        return requiredNode;
    }

    /**
     * Inserts a Node<T> at the specified position in the child list. Will     * throw an ArrayIndexOutOfBoundsException if the index does not exist.
     * @param index the position to insert at.
     * @param child the Node<T> object to insert.
     * @throws IndexOutOfBoundsException if thrown.
     */
//    public void insertChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
//        if (index == getNumberOfChildren()) {
//            // this is really an append
//            addChild(child);
//            return;
//        } else {
//            children.get(index); //just to throw the exception, and stop here
//            children.add(index, child);
//        }
//    }
     
    /**
     * Remove the Node<T> element at index index of the Set<Node<T>>.
     * @param index the index of the element to delete.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }
 
    public T getData() {
        return this.data;
    }
 
    public void setData(T data) {
        this.data = data;
    }
     
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(getData().toString()).append(",[");
        int i = 0;
        for (Node<T> e : getChildren()) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(e.getData().toString());
            i++;
        }
        sb.append("]").append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node<T> other = (Node<T>) obj;
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }

    
}

