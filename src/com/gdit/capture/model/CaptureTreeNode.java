/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

/**
 *
 * @author Bahi
 */
import com.gdit.capture.test.*;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.IconNode;

public class CaptureTreeNode extends LazyLoadingTreeNode {

    public CaptureTreeNode(Object userObject, DefaultTreeModel tree) {
        super(userObject, tree);
    }

    @Override
    public IconNode[] loadChildren(DefaultTreeModel model) {
        ArrayList<IconNode> list = new ArrayList<IconNode>();
//		for (int i = 0; i < 5; i++) {
//			list.add(new FindChildrenTreeNode(
//					"Node " + i,
//					model));
//			try {
////				Thread.sleep(250);
//			} catch (Exception e) {
//				break;
//			}
//                                child = new IconNode(sonNode, false, scaleImage(delete.getImage(), 16, 16), son.getPath());
//
//		}
        ImageIcon delete = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
        Capture doc = (Capture) userObject;
        for (Capture page : doc.getCaptures()) {
            if (page.getRefused()) {
                list.add(new IconNode(page, true, delete, "delete"));
            } else {
                list.add(new IconNode(page));
//                list.add(new IconNode(page, true, delete, "delete"));
            }
        }
//		list.add(new DefaultMutableTreeNode("Leaf"));
        return list.toArray(new IconNode[list.size()]);
    }
}
