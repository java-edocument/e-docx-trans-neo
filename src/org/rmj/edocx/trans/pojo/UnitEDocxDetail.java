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
public class UnitEDocxDetail implements Serializable, GEntity {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @Column(name = "sTransNox")
   private String sTransNox;
   @Column(name = "nEntryNox")
   private Integer nEntryNox;
   @Column(name = "sAcctNmbr")
   private String sAcctNmbr;
   @Column(name = "sCompnyNm")
   private String sCompnyNm;
   @Column(name = "sSourceNo")
   private String sSourceNo;
   @Column(name = "sSourceCd")
   private String sSourceCd;
   @Column(name = "sFileCode")
   private String sFileCode;
   @Column(name = "sEngineNo")
   private String sEngineNo;
   @Column(name = "sModelNme")
   private String sModelNme;
   @Column(name = "dPurchase")
   @Temporal(javax.persistence.TemporalType.DATE)
   private Date dPurchase;
   @Column(name = "sCRNoxxxx")
   private String sCRNoxxxx;
   @Column(name = "sClientID")
   private String sClientID;
   @Column(name = "sSerialID")
   private String sSerialID;
   
   public UnitEDocxDetail() {
      this.sAcctNmbr = "";
      this.sCompnyNm = "";
      this.sSourceNo = "";
      this.sSourceCd = "";
      this.sFileCode = "";
      this.sEngineNo = "";
      this.sModelNme = "";
      this.sCRNoxxxx = "";
      this.sClientID = "";
      this.sSerialID = "";
           
      //set vector for fields/columns
      laColumns = new LinkedList();
      laColumns.add("sTransNox");
      laColumns.add("nEntryNox");
      laColumns.add("sAcctNmbr");
      laColumns.add("sCompnyNm");
      laColumns.add("sSourceNo");
      laColumns.add("sSourceCd");
      laColumns.add("sFileCode");
      laColumns.add("sEngineNo");
      laColumns.add("sModelNme");
      laColumns.add("dPurchase");
      laColumns.add("sCRNoxxxx");
      laColumns.add("sClientID");
      laColumns.add("sSerialID");
   }

   public String getTransNox() {
      return sTransNox;
   }

   public void setTransNox(String sTransNox) {
      this.sTransNox = sTransNox;
   }

   public String getAcctNumber() {
      return sAcctNmbr;
   }

   public void setAcctNumber(String sAcctNmbr) {
      this.sAcctNmbr = sAcctNmbr;
   }

   public Integer getEntryNox() {
      return nEntryNox;
   }

   public void setEntryNox(Integer nEntryNox) {
      this.nEntryNox = nEntryNox;
   }

   public String getCompanyName() {
      return sCompnyNm;
   }

   public void setCompanyName(String sCompnyNm) {
      this.sCompnyNm = sCompnyNm;
   }
   
   public String getSourceNo() {
      return sSourceNo;
   }

   public void setSourceNo(String sSourceNo) {
      this.sSourceNo = sSourceNo;
   }
   
   public String getSourceCd() {
      return sSourceCd;
   }

   public void setSourceCd(String sSourceCd) {
      this.sSourceCd = sSourceCd;
   }
   
   public String getFileCode() {
      return sFileCode;
   }

   public void setFileCode(String sFileCode) {
      this.sFileCode = sFileCode;
   }
   
   public String getEngineNo() {
      return sEngineNo;
   }

   public void setEngineNo(String sEngineNo) {
      this.sEngineNo = sEngineNo;
   }
   
   public String getModelNme() {
      return sModelNme;
   }

   public void setModelNme(String sModelNme) {
      this.sModelNme = sModelNme;
   }
   
   public Date getDatePurchase() {
      return dPurchase;
   }

   public void setDatePurchase(Date dPurchase) {
      this.dPurchase = dPurchase;
   }
   
   public String getCRNoxxxx() {
      return sCRNoxxxx;
   }

   public void setCRNoxxx(String sCRNoxxxx) {
      this.sCRNoxxxx = sCRNoxxxx;
   }
   
   public String getClientID() {
      return sClientID;
   }

   public void setClientID(String sClientID) {
      this.sClientID = sClientID;
   }
   
   public String getSerialID() {
      return sSerialID;
   }

   public void setSerialID(String sSerialID) {
      this.sSerialID = sSerialID;
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
      if (!(object instanceof UnitEDocxDetail)) {
         return false;
      }
      UnitEDocxDetail other = (UnitEDocxDetail) object;
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
            return this.sAcctNmbr;
         case 4:
            return this.sCompnyNm;
         case 5:
            return this.sSourceNo;
         case 6:
            return this.sSourceCd;
         case 7:
            return this.sFileCode;
         case 8:
            return this.sEngineNo;
         case 9:
            return this.sModelNme;
         case 10:
            return this.dPurchase;
         case 11:
            return this.sCRNoxxxx;
         case 12:
            return this.sClientID;
         case 13:
            return this.sSerialID;
            
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
             this.sAcctNmbr = (String) foValue;
             break;
         case 4: 
             this.sCompnyNm = (String)foValue;
             break;
         case 5: 
             this.sSourceNo = (String) foValue;
             break;
         case 6: 
             this.sSourceCd = (String)foValue;
             break;
         case 7: 
             this.sFileCode = (String)foValue;
             break;
         case 8: 
             this.sEngineNo = (String)foValue;
             break;
         case 9: 
             this.sModelNme = (String)foValue;
             break;    
         case 10: 
             this.dPurchase = (Date)foValue;
             break;
         case 11: 
             this.sCRNoxxxx = (String)foValue;
             break;
         case 12: 
             this.sClientID = (String)foValue;
             break;
         case 13: 
             this.sSerialID = (String)foValue;
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
