/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.entity;

import java.io.Serializable;

/**
 *
 * @author ehab
 */
public class DocumentData implements Serializable, Comparable<DocumentData> {
    private Long id;
    private Long fieldId;
    private Long docId;
    private String fieldValue;

    public DocumentData() {
    }

    public DocumentData(Long id, Long fieldId, Long docId, String fieldValue) {
        this.id = id;
        this.fieldId = fieldId;
        this.docId = docId;
        this.fieldValue = fieldValue;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return this.getId()+" : "+this.getFieldValue();
    }

    @Override
    public int compareTo(DocumentData o) {
        return new Long(this.getId()).compareTo(o.getId());
    }
    
}
