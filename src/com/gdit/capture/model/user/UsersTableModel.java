/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model.user;

/**
 *
 * @author bahy
 */
import com.gdit.capture.entity.Groups;
import com.gdit.capture.entity.Roles;
import com.gdit.capture.entity.Users;
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
public class UsersTableModel extends DefaultTableModel {

    private List<Users> users = new ArrayList<Users>();
    String columnNames[];
    private ResourceBundle bundle;
    JTable currentTable = null;

    public UsersTableModel(JTable currentTable, List<Users> users, ResourceBundle bundle) {
        this.bundle = bundle;
        this.users = users;
        this.currentTable = currentTable;
        columnNames = new String[]{bundle.getString("username"),bundle.getString("groups"), bundle.getString("edit"),
        bundle.getString("delete")};

    }

    @SuppressWarnings("finally")
    public List<Users> getDataModel() {
        try {
            if (users == null) {
                users = new ArrayList<Users>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    public void addRow(Users user) {
        try {
            users.add(user);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeRow(int rowIndex) {
        try {
            users.remove(rowIndex);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        if (users != null) {
            return users.size();
            
        } else {
            return 0;
            
        }
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
            Users currentBean = users.get(rowIndex);
            if (columnIndex == 0) {
                value = currentBean.getUserName();
            } else if (columnIndex == 1) {
                value = bundle.getString("groups");
            }else if(columnIndex ==2){
                value = bundle.getString("edit");
            }
            else if (columnIndex == 3) {
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
            Users currentBean = users.get(rowIndex);
            if (columnIndex == 0) {
                currentBean.setUserName(aValue.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
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
