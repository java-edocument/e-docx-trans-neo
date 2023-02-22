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
import org.rmj.appdriver.iface.GEntity;

/**
 *
 * @author jef
 */
@Entity
@Table(name = "EDocSys_Detail")
public class UnitSysFile implements Serializable, GEntity {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @Column(name = "sTransNox")
   private String sTransNox;
   @Column(name = "nEntryNox")
   private Integer nEntryNox;
   @Column(name = "sFileCode")
   private String sFileCode;
   @Column(name = "sBarrcode")
   private String sBarrcode;
   @Column(name = "sBriefDsc")
   private String sBriefDsc;
   @Column(name = "nNoPagesx")
   private Integer nNoPagesx;
   @Column(name = "nNoCopies")
   private Integer nNoCopies;
   @Column(name = "sB2BPagex")
   private String sB2BPagex;

   public UnitSysFile() {
      this.sFileCode = "";
      this.sBarrcode = "";
      this.sBriefDsc = "";
      this.nNoPagesx = 0;
      this.nNoCopies = 0;
      this.sB2BPagex = "0";
           
      //set vector for fields/columns
      laColumns = new LinkedList();
      laColumns.add("sTransNox");
      laColumns.add("nEntryNox");
      laColumns.add("sFileCode");
      laColumns.add("sBarrcode");
      laColumns.add("sBriefDsc");
      laColumns.add("nNoPagesx");
      laColumns.add("nNoCopies");
      laColumns.add("sB2BPagex");
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
   
   public String getFileCode() {
      return sFileCode;
   }

   public void setFileCode(String sFileCode) {
      this.sFileCode = sFileCode;
   }
   
   public String getBarCode() {
      return sBarrcode;
   }

   public void setBarCode(String sBarrcode) {
      this.sBarrcode = sBarrcode;
   }
   
   public String getBriefDsc() {
      return sBriefDsc;
   }

   public void setBriefDsc(String sBriefDsc) {
      this.sBriefDsc = sBriefDsc;
   }
   
   public Integer getNoPages() {
      return nNoPagesx;
   }

   public void setNoPages(Integer nNoPagesx) {
      this.nNoPagesx = nNoPagesx;
   }
   
   public Integer getNoCopies() {
      return nNoCopies;
   }

   public void setNoCopies(Integer nNoCopies) {
      this.nNoCopies = nNoCopies;
   }
   
   public String getB2BPagex() {
      return sB2BPagex;
   }

   public void setB2BPagex(String sB2BPagex) {
      this.sB2BPagex = sB2BPagex;
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
      if (!(object instanceof UnitSysFile)) {
         return false;
      }
      UnitSysFile other = (UnitSysFile) object;
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
            return this.sFileCode;
         case 4:
            return this.sBarrcode;
         case 5:
            return this.sBriefDsc;
         case 6:
            return this.nNoPagesx;
         case 7:
            return this.nNoCopies;
         case 8:
            return this.sB2BPagex;
         default:
            return null;
      }
   }

   public String getTable() {
      return "EDocSys_File";
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
             this.sFileCode = (String)foValue;
             break;
         case 4: 
             this.sBarrcode = (String)foValue;
             break;
         case 5: 
             this.sBriefDsc = (String)foValue;
             break;
         case 6: 
             this.nNoPagesx = (Integer)foValue;
             break;
         case 7: 
             this.nNoCopies = (Integer)foValue;
             break;
         case 8: 
             this.sB2BPagex = (String)foValue;
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
}
