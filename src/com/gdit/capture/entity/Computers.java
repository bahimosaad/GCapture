package com.gdit.capture.entity;
// Generated 27/10/2010 12:03:53   by Hibernate Tools 3.2.0.beta7


import java.util.HashSet;
import java.util.Set;

/**
 * Computers generated by hbm2java
 */
public class Computers  implements java.io.Serializable {

    // Fields    

     private int id;
     private String name;
     private String ip;
     private String mac;
     private Boolean active;
     private Disk disk ;
     private String scanPath;
     private String scanPre;
     private Set computersGroupses = new HashSet(0); 
     // Constructors

    /** default constructor */
    public Computers() {

    }

	/** minimal constructor */
    public Computers(int id, String name, String ip) {
        this.id = id;
        this.name = name;
        this.ip = ip;
    }
    /** full constructor */
    public Computers(int id, String name, String ip, Boolean active, Set computersGroupses) {
       this.id = id;
       this.name = name;
       this.ip = ip;
       this.active = active;
       this.computersGroupses = computersGroupses;
    }
   
    // Property accessors
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Set getComputersGroupses() {
        return this.computersGroupses;
    }
    
    public void setComputersGroupses(Set computersGroupses) {
        this.computersGroupses = computersGroupses;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Computers other = (Computers) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    public String getScanPre() {
        return scanPre;
    }

    public void setScanPre(String scanPre) {
        this.scanPre = scanPre;
    }

    
   

}

