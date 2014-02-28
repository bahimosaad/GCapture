package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59 � by Hibernate Tools 3.2.0.beta7

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Users generated by hbm2java
 */
public class Users implements java.io.Serializable {

    // Fields
    private int id;
    private String userName;
    private String password;
    private boolean active;
    private Set captures = new HashSet(0);
    private Set usersGroupses  ; 
    // Constructors
    /** default constructor */
    public Users() {
    }

    /** minimal constructor */
    public Users(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /** full constructor */
    public Users(Integer id, String userName, String password, boolean active, Set captures, Set usersGroupses) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.captures = captures;
        this.usersGroupses = usersGroupses;
    }

    // Property accessors
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set getCaptures() {
        return this.captures;
    }

    public void setCaptures(Set captures) {
        this.captures = captures;
    }

    public Set getUsersGroupses() {
        return this.usersGroupses;
    }

    public void setUsersGroupses(Set usersGroupses) {
        this.usersGroupses = usersGroupses;
    }

    @Override
    public String toString() {
        return   userName ;
    }

    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users other = (Users) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
}
