/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

/**
 *
 * @author bahy
 */
import com.gdit.capture.entity.Computers;
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
public class ComputersTableModel extends DefaultTableModel{

    private List<Computers> computers = new ArrayList<Computers>();
    String columnNames[] ;
    private ResourceBundle bundle ;
    JTable currentTable = null;

    public ComputersTableModel(JTable currentTable,List<Computers> computers,ResourceBundle bundle){
        this.bundle = bundle;
        this.computers = computers;
        this.currentTable = currentTable;
        columnNames =  new String[]{bundle.getString("computer.ip"),bundle.getString("computer.name"),bundle.getString("edit"),bundle.getString("delete")};

    }

    @SuppressWarnings("finally")
	public List<Computers> getDataModel() {
		try {
			if (computers == null) {
				computers= new ArrayList<Computers>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return computers;
		}
	}

    public void addRow(Computers computer) {
		try {
			computers.add(computer);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
    public void removeRow(int rowIndex) {
		try {
			computers.remove(rowIndex);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public int getRowCount() {
        if(computers!=null)
		return computers.size();
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
			Computers currentBean = computers.get(rowIndex);
			if (columnIndex == 0) {
				value = currentBean.getIp();
			} else if (columnIndex == 1) {
				value = currentBean.getName();
			} 
            else if(columnIndex==2){
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
			Computers currentBean = computers.get(rowIndex);
			if (columnIndex == 0) {
				currentBean.setName(aValue.toString());
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public List<Computers> getComputers() {
        return computers;
    }

    public void setComputers(List<Computers> computers) {
        this.computers = computers;
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
