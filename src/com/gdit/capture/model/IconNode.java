/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;
import java.util.Hashtable;
import javax.swing.AbstractCellEditor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 *
 * @author bahy
 */
public class IconNode extends DefaultMutableTreeNode {

    protected Icon icon;
    protected String iconName;
    protected Object userObject;

    public IconNode() {
        this(null);
    }

    public IconNode(Object userObject) {
        this(userObject, true, null, null);
    }

    public IconNode(Object userObject, boolean allowsChildren, Icon icon, String iconName) {
        super(userObject, allowsChildren);
        this.icon = icon;
        this.userObject = userObject;
        this.iconName = iconName;


    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getIconName() {
        if (iconName != null) {
            return iconName;
//        } else if (userObject != null && userObject.toString()!=null) {
//            String str = userObject.toString();
//            int index = str.lastIndexOf(".");
//            if (index != -1) {
//                return str.substring(++index);
//            } else {
//                return null;
//            }
        }
        return "";
    }

    public void setIconName(String name) {
        iconName = name;
    }

      
    
}
