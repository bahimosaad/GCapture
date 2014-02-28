/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author bahy
 */
public class ImageListModel extends DefaultTreeModel {

    public ImageListModel(TreeNode root) {
        super(root);
        this.root = (DefaultMutableTreeNode) root;
    }

    public void addBaseNode(DefaultMutableTreeNode node) {
        root.add(node);
    }

    public void addLeaf(DefaultMutableTreeNode parent, DefaultMutableTreeNode child) {

//        if (parent.getChildCount() > 0 && parent.getLastChild() != null) {
//            if (child.getUserObject() instanceof ImageItem) {
//                DefaultMutableTreeNode lastChild = parent.getFirstLeaf();
//                if (lastChild.getUserObject() instanceof Capture) {
//                    Capture lastCapture = (Capture) lastChild.getUserObject();
//                    if (lastCapture.getId() > ((ImageItem) child.getUserObject()).getCapture().getId()) {
//                        System.out.println(" Last Child Order   " + parent.getIndex(lastChild) + "   " + lastCapture.getName());
//                        parent.insert(child, parent.getIndex(lastChild));
//                    }
//                } else if (lastChild.getUserObject() instanceof ImageItem) {
//                    ImageItem lastCapture = (ImageItem) lastChild.getUserObject();
//                    if (lastCapture.getCapture().getId() > ((ImageItem) child.getUserObject()).getCapture().getId()) {
//                        System.out.println(" Last Child Order   " + parent.getIndex(lastChild) + "   " + lastCapture.getName());
//                        parent.insert(child, parent.getIndex(lastChild));
//                    }
//                }
//            } else {
//                parent.add(child);
//            }
//        } else {
            parent.add(child);
      //  }
    }
    private DefaultMutableTreeNode root;
    private static final long serialVersionUID = 1L;
    private List<Object> baseNodes;
}
