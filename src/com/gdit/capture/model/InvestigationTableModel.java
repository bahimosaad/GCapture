/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

import com.gdit.capture.entity.Investigation;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bahy
 */
public class InvestigationTableModel extends DefaultTableModel{

    private List<Investigation> investigations = new ArrayList<Investigation>();
    String columnNames[] ;
    private ResourceBundle bundle ;
    JTable currentTable = null;

    public InvestigationTableModel(JTable currentTable,List<Investigation> investigations,ResourceBundle bundle){
        this.bundle = bundle;
        this.investigations = investigations;
        this.currentTable = currentTable;
        columnNames =  new String[]{bundle.getString("investigation.name"),bundle.getString("edit"),bundle.getString("delete")};

    }

    @SuppressWarnings("finally")
	public List<Investigation> getDataModel() {
		try {
			if (investigations == null) {
				investigations= new ArrayList<Investigation>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return investigations;
		}
	}

    public void addRow(Investigation investigation) {
		try {
			investigations.add(investigation);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
    public void removeRow(int rowIndex) {
		try {
			investigations.remove(rowIndex);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public int getRowCount() {
        if(investigations!=null)
		return investigations.size();
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
			Investigation currentBean = investigations.get(rowIndex);
			if (columnIndex == 0) {
				value = currentBean.getName();
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
			Investigation currentBean = investigations.get(rowIndex);
			if (columnIndex == 0) {
				currentBean.setName(aValue.toString());
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public List<Investigation> getInvestigations() {
        return investigations;
    }

    public void setInvestigations(List<Investigation> investigations) {
        this.investigations = investigations;
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
