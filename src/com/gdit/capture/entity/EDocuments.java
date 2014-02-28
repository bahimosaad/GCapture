/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

/**
 *
 * @author admin
 */
public class EDocuments {
    
    private long docId;
    private long batchId;
    private   long dId;
    private String yer;
    private long lineNo;
    private String jrnlDate;
    private String jrnlType;
    private String fileNo;
    private String boxNo;
    private String corridorNo;
    private String columnNo;
    private String barcode;

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(String columnNo) {
        this.columnNo = columnNo;
    }

    public String getCorridorNo() {
        return corridorNo;
    }

    public void setCorridorNo(String corridorNo) {
        this.corridorNo = corridorNo;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

     

    public String getJrnlDate() {
        return jrnlDate;
    }

    public void setJrnlDate(String jrnlDate) {
        this.jrnlDate = jrnlDate;
    }

    public String getJrnlType() {
        return jrnlType;
    }

    public void setJrnlType(String jrnlType) {
        this.jrnlType = jrnlType;
    }

    public long getLineNo() {
        return lineNo;
    }

    public void setLineNo(long lineNo) {
        this.lineNo = lineNo;
    }

    public String getYer() {
        return yer;
    }

    public void setYer(String yer) {
        this.yer = yer;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public long getdId() {
        return dId;
    }

    public void setdId(long dId) {
        this.dId = dId;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EDocuments other = (EDocuments) obj;
        if (this.docId != other.docId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (this.docId ^ (this.docId >>> 32));
        return hash;
    }
 
}
