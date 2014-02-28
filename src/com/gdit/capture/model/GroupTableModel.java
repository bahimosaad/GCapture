/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

/**
 *
 * @author bahy
 */
import com.gdit.capture.entity.Groups;
import com.gdit.capture.entity.Roles;
import com.jidesoft.grid.DefaultTableModelWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bahy
 */
public class GroupTableModel extends DefaultTableModel{

    private List<Groups> groups = new ArrayList<Groups>();
    String columnNames[] ;
    private ResourceBundle bundle ;
    JTable currentTable = null;

    public GroupTableModel(JTable currentTable,List<Groups> groups,ResourceBundle bundle){
        this.bundle = bundle;
        this.groups = groups;
        this.currentTable = currentTable;
        columnNames =  new String[]{bundle.getString("group.name"),bundle.getString("edit"),bundle.getString("delete")};

    }

    @SuppressWarnings("finally")
	public List<Groups> getDataModel() {
		try {
			if (groups == null) {
				groups= new ArrayList<Groups>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return groups;
		}
	}

    public void addRow(Groups group) {
		try {
			groups.add(group);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
    public void removeRow(int rowIndex) {
		try {
			groups.remove(rowIndex);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public int getRowCount() {
        if(groups!=null)
		return groups.size();
        else
            return 0;
	}

     @Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

		Object value = null;
		try {
			Groups currentBean = groups.get(rowIndex);
			if (columnIndex == 0) {
				value = currentBean.getGroupName();
			} else if (columnIndex == 1) {
				value = bundle.getString("edit");
			} else if (columnIndex == 2) {
				value = bundle.getString("delete");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return value;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			Groups currentBean = groups.get(rowIndex);
			if (columnIndex == 0) {
				currentBean.setGroupName(aValue.toString());
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

 

//    @Override
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		boolean editable = false;
//		try {
//			if (columnIndex == 0) {
//				editable = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			return editable;
//		}
//	}



}
