
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class LeafCellEditor extends DefaultTreeCellEditor {

	  public LeafCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
	    super(tree, renderer);
	  }

	  public LeafCellEditor(JTree tree, DefaultTreeCellRenderer renderer,TreeCellEditor editor) {
	    super(tree, renderer, editor);
	  }

	  public boolean isCellEditable(EventObject event) {
	    boolean returnValue = super.isCellEditable(event);
	    if (returnValue) {
	      Object node = tree.getLastSelectedPathComponent();
	      if ((node != null) && (node instanceof TreeNode)  ) {
	    	  TreeNode treeNode = (TreeNode) node;
		        returnValue = treeNode.isLeaf();
	    	  if(((DefaultMutableTreeNode)node).getUserObject().toString().equals("ehab") ){
	    		  returnValue = false;
	    	  }
	      }
	    }
	    return returnValue;
	  }
	}
