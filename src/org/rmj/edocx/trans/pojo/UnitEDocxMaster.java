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
@Table(name = "EDocSys_Master")
public class UnitEDocxMaster implements Serializable, GEntity {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @Column(name = "sTransNox")
   private String sTransNox;
   @Column(name = "dTransact")
   @Temporal(TemporalType.DATE)
   private Date dTransact;
   @Column(name = "sBranchCd")
   private String sBranchCd;
   @Column(name = "sDeptIDxx")
   private String sDeptIDxx;
   @Column(name = "sEmployID")
   private String sEmployID;
   @Column(name = "sModuleCd")
   private String sModuleCd;
   @Column(name = "sFileCode")
   private String sFileCode;
   @Column(name = "nNoPagesx")
   private Integer nNoPagesx;
   @Column(name = "nNoCopies")
   private Integer nNoCopies;
   @Column(name = "sB2BPages")
   private String sB2BPages;
   @Column(name = "sRemarksx")
   private String sRemarksx;
   @Column(name = "nEntryNox")
   private Integer nEntryNox;
   @Column(name = "cTranStat")
   private String cTranStat;
   @Column(name = "sModified")
   private String sModified;
   @Basic(optional = false)
   @Column(name = "dModified")
   @Temporal(TemporalType.TIMESTAMP)
   private Date dModified;

   public UnitEDocxMaster() {
      this.dTransact = new Date();
      this.sBranchCd = "";
      this.sDeptIDxx = "";
      this.sEmployID = "";
      this.sModuleCd = "";
      this.sFileCode = "";
      this.nNoPagesx = 1;
      this.nNoCopies = 1;
      this.sB2BPages = "";
      this.sRemarksx = "";
      this.cTranStat = "0";
      
      //set vector for fields/columns
      laColumns = new LinkedList();
      laColumns.add("sTransNox");
      laColumns.add("dTransact");
      laColumns.add("sBranchCd");
      laColumns.add("sDeptIDxx");
      laColumns.add("sEmployID");
      laColumns.add("sModuleCd");
      laColumns.add("sFileCode");
      laColumns.add("nNoPagesx");
      laColumns.add("nNoCopies");
      laColumns.add("sB2BPages");
      laColumns.add("sRemarksx");
      laColumns.add("nEntryNox");      
      laColumns.add("cTranStat");
      laColumns.add("sModified");
      laColumns.add("dModified");
   }

   public String getTransNox() {
      return sTransNox;
   }

   public void setTransNox(String sTransNox) {
      this.sTransNox = sTransNox;
   }

   public void setTransact(Date dTransact) {
      this.dTransact = dTransact;
   }

   public Date getTransact() {
      return dTransact;
   }

   public String getBranchCd() {
      return sBranchCd;
   }

   public void setBranchCd(String sBranchCd) {
      this.sBranchCd = sBranchCd;
   }
   
   public String getDeptIDxx() {
      return sDeptIDxx;
   }
   
   public void setDeptIDxx(String sDeptIDxx) {
      this.sDeptIDxx = sDeptIDxx;
   }
   
   public String getEmployID() {
      return sEmployID;
   }
   
   public void setEmployID(String sEmployID) {
      this.sEmployID = sEmployID;
   }
   
   public String getModuleCd() {
      return sModuleCd;
   }
   
   public void setModuleCd(String sModuleCd) {
      this.sModuleCd = sModuleCd;
   }
   
   public String getFileCode() {
      return sFileCode;
   }
   
   public void setFileCode(String sFileCode) {
      this.sFileCode = sFileCode;
   }
   
   public Integer getNoPagesx() {
      return nNoPagesx;
   }
   
   public void setNoPagesx(Integer nNoPagesx) {
      this.sFileCode = sFileCode;
   }
   
   public Integer getNoCopies() {
      return nNoCopies;
   }
   
   public void setNoCopies(Integer nNoCopies) {
      this.nNoCopies = nNoCopies;
   }
   
   public String getB2BPages() {
      return sB2BPages;
   }
   
   public void setB2BPages(String sB2BPages) {
      this.sB2BPages = sB2BPages;
   }
   
   public String getRemarksx() {
      return sRemarksx;
   }
   
   public void setRemarksx(String sRemarksx) {
      this.sRemarksx = sRemarksx;
   }

   public Integer getEntryNox() {
      return nEntryNox;
   }

   public void setEntryNox(Integer nEntryNox) {
      this.nEntryNox = nEntryNox;
   }
   
   public String getTranStat() {
      return cTranStat;
   }

   public void setTranStat(String cTranStat) {
      this.cTranStat = cTranStat;
   }

   public String getModifiedBy() {
      return sModified;
   }

   public void setModifiedBy(String sModified) {
      this.sModified = sModified;
   }

   public Date getDateModified() {
      return dModified;
   }

   public void setDateModified(Date dModified) {
      this.dModified = dModified;
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
      if (!(object instanceof UnitEDocxMaster)) {
         return false;
      }
      UnitEDocxMaster other = (UnitEDocxMaster) object;
      if ((this.sTransNox == null && other.sTransNox != null) || (this.sTransNox != null && !this.sTransNox.equals(other.sTransNox))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "org.rmj.edocc.pojo.trans.UnitEDocxMaster[sTransNox=" + sTransNox + "]";
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
            return this.dTransact;
         case 3:
            return this.sBranchCd;
         case 4:
            return this.sDeptIDxx;
         case 5:
            return this.sEmployID;
         case 6:
            return this.sModuleCd;
         case 7:
            return this.sFileCode;
         case 8:
            return this.nNoPagesx;
         case 9:
            return this.nNoCopies;
         case 10:
            return this.sB2BPages;
         case 11:
            return this.sRemarksx;
         case 12:
            return this.nEntryNox;             
         case 13:
            return this.cTranStat;
         case 14:
            return this.sModified;
         case 15:
            return this.dModified;
         default:
            return null;
      }
   }

   public String getTable() {
      return "EDocSys_Master";
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
            this.dTransact = (Date) foValue;
            break;
         case 3:
            this.sBranchCd = (String) foValue;
            break;
         case 4:
            this.sDeptIDxx = (String) foValue;
            break;
         case 5:
            this.sEmployID = (String) foValue;
            break;
         case 6:
            this.sModuleCd = (String) foValue;
            break;
         case 7:
            this.sFileCode = (String) foValue;
            break;
         case 8:
            this.nNoPagesx = (int) foValue;
            break;   
         case 9:
            this.nNoCopies = (int) foValue;
            break;
         case 10:
            this.sB2BPages = (String) foValue;
            break;              
         case 11:
            this.sRemarksx = (String) foValue;
            break;
         case 12: 
            this.nEntryNox = (Integer)foValue;
            break; 
         case 13:
            this.cTranStat = (String) foValue;
            break;
         case 14:
            this.sModified = (String) foValue;
            break;
         case 15:
            this.dModified = (Date) foValue;
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
