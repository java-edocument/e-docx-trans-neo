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
@Table(name = "Master_File")
public class UnitMasterFile implements Serializable, GEntity {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @Column(name = "sTransNox")
   private String sTransNox;
   @Column(name = "nEntryNox")
   private Integer nEntryNox;
   @Column(name = "nFileNoxx")
   private Integer nFileNoxx;
   @Column(name = "nClientNo")
   private Integer nClientNo;
   @Column(name = "sFileName")
   private String sFileName;
   public UnitMasterFile() {
      this.sFileName = "";
           
      //set vector for fields/columns
      laColumns = new LinkedList();
      laColumns.add("sTransNox");
      laColumns.add("nEntryNox");
      laColumns.add("nFileNoxx");
      laColumns.add("nClientNo");
      laColumns.add("sFileName");
   }

   public String getTransNox() {
      return sTransNox;
   }

   public void setTransNox(String sTransNox) {
      this.sTransNox = sTransNox;
   }

   public Integer getEntryNox() {
      return nEntryNox;
   }

   public void setEntryNox(Integer nEntryNox) {
      this.nEntryNox = nEntryNox;
   }
   
   public Integer getFileNoxx() {
      return nFileNoxx;
   }

   public void setFileNoxx(Integer nFileNoxx) {
      this.nFileNoxx = nFileNoxx;
   }
   
   public Integer getClientNo() {
      return nClientNo;
   }

   public void setClientNo(Integer nClientNo) {
      this.nClientNo = nClientNo;
   }

   public String getFileName() {
      return sFileName;
   }

   public void setFileName(String sFileName) {
      this.sFileName = sFileName;
   }
   
   @Override
   public int hashCode() {
      int hash = 0;
      hash += (sTransNox != null ? sTransNox.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      // TODO: Warning - this method won't work in the case the id fields are not set
      if (!(object instanceof UnitMasterFile)) {
         return false;
      }
      UnitMasterFile other = (UnitMasterFile) object;
      if ((this.sTransNox == null && other.sTransNox != null) || (this.sTransNox != null && !this.sTransNox.equals(other.sTransNox))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "org.rmj.edocx.trans.pojo.UnitEDocxDetail[sTransNox=" + sTransNox + ", nEntryNox=" + nEntryNox + "]";
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
            return this.sTransNox;
         case 2:
            return this.nEntryNox;
         case 3:
            return this.nFileNoxx;
         case 4:
            return this.nClientNo;
         case 5:
            return this.sFileName;
         default:
            return null;
      }
   }

   public String getTable() {
      return "EDocSys_Detail";
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
             this.sTransNox = (String) foValue;
             break;
         case 2: 
             this.nEntryNox = (Integer) foValue;
             break;
         case 3: 
             this.nFileNoxx = (Integer) foValue;
             break;
         case 4: 
             this.nClientNo = (Integer) foValue;
             break;
         case 5: 
             this.sFileName = (String)foValue;
             break;
      }
   }
   
   @Override
   public int getColumnCount() {
      return laColumns.size();
   }

    public void list(){
        Stream.of(laColumns).forEach(System.out::println);        
    }

   //Member Variables here
   LinkedList laColumns = null;

    <T> int indexOf(T elementToMove) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}