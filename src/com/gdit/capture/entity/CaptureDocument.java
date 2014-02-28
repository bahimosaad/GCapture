/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

/**
 *
 * @author admin
 */
public class CaptureDocument {
    
    private   long id;
    private String yer;
    private long lineNo;
    private String jrnlDate;
    private String jrnlType;
    private String fileNo;
    private String boxNo;
    private String corridorNo;
    private String columnNo;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaptureDocument other = (CaptureDocument) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    
    
    
}
