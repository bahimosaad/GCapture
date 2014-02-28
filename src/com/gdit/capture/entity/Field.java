/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.entity;

import java.io.Serializable; 
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ehab
 */
public class Field implements Serializable, Comparable<Field>{
    private Long id;
    private String name;
    private String alias;
    private String type;
    private Set<Category> associatedCategories;
    private String dateFormat;
    private Set<String> listData = new HashSet<String>();

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the alials
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alials the alials to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the associatedDocs
     */
    public Set<Category> getAssociatedCategories() {
        if (associatedCategories == null){
            associatedCategories = new HashSet<Category>();
        }
        return associatedCategories;
    }

    /**
     * @param associatedDocs the associatedDocs to set
     */
    public void setAssociatedCategories(Set<Category> associatedCategories) {
        this.associatedCategories = associatedCategories;
    }

    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the listData
     */
    public Set<String> getListData() {
        if (listData == null){
            listData = new HashSet<String>();
        }
        return listData;
    }

    /**
     * @param listData the listData to set
     */
    public void setListData(Set<String> listData) {
        this.listData = listData;
    }

    @Override
    public int compareTo(Field o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
    

}
