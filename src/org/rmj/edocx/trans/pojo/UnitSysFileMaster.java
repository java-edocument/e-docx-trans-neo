/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rmj.edocx.trans.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Stream;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.rmj.appdriver.iface.GEntity;

/**
 *
 * @author jef
 */
@Entity
@Table(name = "Department")
public class UnitSysFileMaster implements Serializable, GEntity {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @Column(name = "sDeptIDxx")
   private String sDeptIDxx;
   @Column(name = "sDeptName")
   private String sDeptName;
   
   public UnitSysFileMaster() {
      this.sDeptIDxx = "";
      this.sDeptName = "";
      
      //set vector for fields/columns
      laColumns = new LinkedList();
      laColumns.add("sDeptIDxx");
      laColumns.add("sDeptName");
   }

   public String getDeptIDxx() {
      return sDeptIDxx;
   }

   public void setDeptIDxx(String sDeptIDxx) {
      this.sDeptIDxx = sDeptIDxx;
   }

   public String getDeptName() {
      return sDeptName;
   }

   public void setDeptName(String sDeptName) {
      this.sDeptName = sDeptName;
   }
   
   
   @Override
   public int hashCode() {
      int hash = 0;
      hash += (sDeptIDxx != null ? sDeptIDxx.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      // TODO: Warning - this method won't work in the case the id fields are not set
      if (!(object instanceof UnitSysFileMaster)) {
         return false;
      }
      UnitSysFileMaster other = (UnitSysFileMaster) object;
      if ((this.sDeptIDxx == null && other.sDeptIDxx != null) || (this.sDeptIDxx != null && !this.sDeptIDxx.equals(other.sDeptIDxx))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "org.rmj.edocc.pojo.trans.UnitSysFileMaster[sTransNox=" + sDeptIDxx + "]";
   }

    public Object getValue(String fsColumn){
       int lnCol = getColumn(fsColumn);
       if(lnCol > 0){
          return getValue(lnCol);
       }
       else
         return null;
    }

    public Object getValue(int fnColumn) {
      switch(fnColumn){
         case 1:
            return this.sDeptIDxx;
         case 2:
            return this.sDeptName;
         default:
            return null;
      }
   }

   public String getTable() {
      return "Department";
   }

    public int getColumn(String fsCol) {
        return laColumns.indexOf(fsCol) + 1;
    }

    public String getColumn(int fnCol) {
       if(laColumns.size() < fnCol)
           return "";
       else
           return (String) laColumns.get(fnCol - 1);
    }

    public void setValue(String fsColumn, Object foValue){
       int lnCol = getColumn(fsColumn);
       if(lnCol > 0){
          setValue(lnCol, foValue);
       }
    }

   public void setValue(int fnColumn, Object foValue) {
      switch(fnColumn){
         case 1:
            this.sDeptIDxx = (String) foValue;
            break;
         case 2:
            this.sDeptName = (String) foValue;
            break;
      }
   }

   public int getColumnCount() {
      return laColumns.size();
   }
   
    public void list(){
        Stream.of(laColumns).forEach(System.out::println);        
    }
   
   //Member Variables here
   LinkedList laColumns = null;

}
