/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 *
 * @author Bahi
 */
public class TextNodeEditor extends AbstractCellEditor implements TreeCellEditor {

    TextNodeRenderer renderer = new TextNodeRenderer();
    ChangeEvent changeEvent = null;
    JTree tree;

    public TextNodeEditor(JTree tree) {
        this.tree = tree;
    }

    public Object getCellEditorValue() {
        JTextField txtField = renderer.getLeafRenderer();
        txtField.setSize(20, 5);
        CaptureNode checkBoxNode = new CaptureNode(txtField.getText());
        return checkBoxNode;
    }

     
    
    public boolean isCellEditable(EventObject event) {
        boolean returnValue = false;
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                    mouseEvent.getY());
            if (path != null) {
                Object node = path.getLastPathComponent();
                if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                    Object userObject = treeNode.getUserObject();
                    returnValue = ((treeNode.isLeaf()) && (userObject instanceof CaptureNode));
                }
            }
        }
        return returnValue;
    }

    public Component getTreeCellEditorComponent(JTree t, Object value,
            boolean selected, boolean expanded, boolean leaf, int row) {

        Component editor = renderer.getTreeCellRendererComponent(tree, value,
                true, expanded, leaf, row, true);

        // editor always selected / focused
        KeyListener itemListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
          public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
//           
                 if(e.getKeyChar() == KeyEvent.VK_ENTER){
                     System.out.println("Enter");
                      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                      CaptureNode node = (CaptureNode) treeNode.getUserObject();
                      node.setName(renderer.getLeafRenderer().getText());
                      Capture cap = node.getCapture();
                      cap.setName(renderer.getLeafRenderer().getText());
                      CaptureHome dao = new CaptureHome();
                      dao.attachDirty(cap);
                      CaptureHome.close();
                 }
            }
            
        };
        if (editor instanceof JTextField) {
            ((JTextField) editor).addKeyListener(itemListener);
        }

        return editor;
    }
}
