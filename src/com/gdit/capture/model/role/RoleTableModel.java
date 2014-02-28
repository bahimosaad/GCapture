/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model.role;

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
public class RoleTableModel extends DefaultTableModel{

    private List<Roles> roles = new ArrayList<Roles>();
    String columnNames[] ;
    private ResourceBundle bundle ;
    JTable currentTable = null;
     
    public RoleTableModel(JTable currentTable,List<Roles> roles,ResourceBundle bundle){
        this.bundle = bundle;
        this.roles = roles;
        this.currentTable = currentTable;
        columnNames =  new String[]{bundle.getString("role.name"),
                                    bundle.getString("modules"),
                                    bundle.getString("categories"),
                                    bundle.getString("reps"),
                                    bundle.getString("edit"),
                                    bundle.getString("delete")};
    }

    @SuppressWarnings("finally")
	public List<Roles> getDataModel() {
		try {
			if (roles == null) {
				roles= new ArrayList<Roles>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return roles;
		}
	}

    public void addRow(Roles role) {
		try {
			roles.add(role);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
    public void removeRow(int rowIndex) {
		try {
			roles.remove(rowIndex);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public int getRowCount() {
        if(roles!=null)
		return roles.size();
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
			Roles currentBean = roles.get(rowIndex);
			if (columnIndex == 0) {
				value = currentBean.getRoleName();
			} else if (columnIndex == 1) {
				value = bundle.getString("modules");
			} else if (columnIndex == 2) {
				value = bundle.getString("categories");

			} else if (columnIndex == 3) {
				value = bundle.getString("reps");

			} else if (columnIndex == 4) {
				value = bundle.getString("edit");

			} else if (columnIndex == 5) {
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
			Roles currentBean = roles.get(rowIndex);
			if (columnIndex == 0) {
				currentBean.setRoleName(aValue.toString());
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
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
