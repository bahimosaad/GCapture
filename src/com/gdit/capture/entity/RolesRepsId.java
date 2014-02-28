package com.gdit.capture.entity;
// Generated 05/01/2011 04:28:00   by Hibernate Tools 3.2.0.beta7



/**
 * RolesRepsId generated by hbm2java
 */
public class RolesRepsId  implements java.io.Serializable {

    // Fields    

     private long roleId;
     private short repId;

     // Constructors

    /** default constructor */
    public RolesRepsId() {
    }

    /** full constructor */
    public RolesRepsId(long roleId, short repId) {
       this.roleId = roleId;
       this.repId = repId;
    }
   
    // Property accessors
    public long getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
    public short getRepId() {
        return this.repId;
    }
    
    public void setRepId(short repId) {
        this.repId = repId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RolesRepsId) ) return false;
		 RolesRepsId castOther = ( RolesRepsId ) other; 
         
		 return (this.getRoleId()==castOther.getRoleId())
 && (this.getRepId()==castOther.getRepId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getRoleId();
         result = 37 * result + this.getRepId();
         return result;
   }   


}


