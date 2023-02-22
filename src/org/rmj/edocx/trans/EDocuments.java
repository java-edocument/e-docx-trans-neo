package org.rmj.edocx.trans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rmj.appdriver.constants.TransactionStatus;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.iface.GEntity;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.edocx.trans.pojo.UnitEDocuments;
import org.rmj.edocx.trans.pojo.UnitEDocxMaster;
import org.rmj.edocx.trans.pojo.UnitEDocxDetail;
import org.rmj.edocx.trans.pojo.UnitMasterFile;

/**
 *
 * @author jef
 */
public class EDocuments {
   public Object newTransaction() {
      UnitEDocuments loOcc = new  UnitEDocuments();
      Connection loCon = poGRider.getConnection();

      if(psBranchCD.equals("")) {
         psBranchCD = poGRider.getBranchCode();
      }

      loOcc.getMaster().setTransNox(MiscUtil.getNextCode(loOcc.getMaster().getTable(), "sTransNox", true, loCon, psBranchCD));

      return loOcc;
   }

   public Object loadTransaction(String string) {
      UnitEDocuments loOcc = new UnitEDocuments();
      Connection loCon = poGRider.getConnection();

      //Load the master record
      loOcc.setMaster(loadMaster(loCon, string));
      
      //Load detail if no error was encountered during loadMaster();
      if(getErrMsg().isEmpty() || getMessage().isEmpty())
          loOcc.setDetail(loadDetail(loCon, string));
          loOcc.setMasterFile(loadMasterFile(loCon, string));
                    
        //Close connection if this is not a parent class...
      if(!pbWithParnt)
        MiscUtil.close(loCon);      
        return loOcc;
   }

    public Object saveUpdate(Object foEntity, String fsTransNox) {
        UnitEDocuments loNewEnt = null;
        UnitEDocuments loResult = null;
      
        Connection loCon = poGRider.getConnection();

        // Check for the value of foEntity
        if (!(foEntity instanceof UnitEDocuments)) {
            setErrMsg("Invalid Entity Passed as Parameter");
            return foEntity;
        }
      
        // Typecast the Entity to this object
        loNewEnt = (UnitEDocuments) foEntity;

        if(loNewEnt.getMaster().getTranStat()== null){
            setErrMsg("Invalid transaction date detected!");
            return foEntity;
        }
      
        //TODO: Test the user rights in these area...
        System.out.println(!pbWithParnt);
        if(!pbWithParnt)
            poGRider.beginTrans();
      
        //Save the master transaction 
        UnitEDocxMaster loEDocMaster = saveMaster(loCon, loNewEnt, fsTransNox);
        
        UnitEDocxDetail loEDocDetail;
        UnitMasterFile loMasterFile;
        if (loEDocMaster != null){
            //save detail if master was saved successfully...
//            saveDetail(loCon, loNewEnt, fsTransNox);
            saveMasterFile(loCon, loNewEnt, fsTransNox);
        }
        
        /*
        loResult = new UnitEDocuments();
        loResult.setMaster(loEDocMaster);      
        //save detail if no error was encountered during saveMaster();
        if(getErrMsg().isEmpty() || getMessage().isEmpty())
            loResult.setDetail(saveDetail(loCon, loNewEnt, fsTransNox));
        */
        
        if(!pbWithParnt){
            if(getErrMsg().isEmpty()){
                poGRider.commitTrans();
            } else
                poGRider.rollbackTrans();
        }
        
        loResult = (UnitEDocuments) loadTransaction(loEDocMaster.getTransNox());

        return loResult;
    }

   public boolean deleteTransaction(String fsTransNox) {
      UnitEDocuments  loOcc = (UnitEDocuments) loadTransaction(fsTransNox);
      boolean lbResult = false;

      if(loOcc == null){
         setMessage("No record found!");
         return lbResult;
      }

      //TODO: Test the user rights in these area...

      StringBuilder lsSQL = new StringBuilder();
      lsSQL.append("DELETE FROM " + loOcc.getMaster().getTable());
      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(fsTransNox));

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getMaster().getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record deleted");
      }
      else{
          lsSQL = new StringBuilder();
          lsSQL.append("DELETE FROM " + loOcc.getDetail().get(0).getTable());
          lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(fsTransNox));
          poGRider.executeQuery(lsSQL.toString(), loOcc.getDetail().get(0).getTable(), "", "");
         lbResult = true;
      }

      if(!pbWithParnt){
         if(getErrMsg().isEmpty()){
            poGRider.commitTrans();
         }
         else
            poGRider.rollbackTrans();
      }

      return lbResult;
   }

   public boolean closeTransaction(String string) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public boolean postTransaction(String string) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public boolean cancelTransaction(String fsTransNox) {
      UnitEDocuments  loOcc = (UnitEDocuments) loadTransaction(fsTransNox);
      boolean lbResult = false;

      if(loOcc == null){
         setMessage("No record found!");
         return lbResult;
      }

      //TODO: Test the user rights in these area...
      //GCrypt loCrypt = new GCrypt();
      StringBuilder lsSQL = new StringBuilder();
      lsSQL.append("UPDATE " + loOcc.getMaster().getTable() + " SET ");
      lsSQL.append("  cTranStat = " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED));
      lsSQL.append(", sModified = " + SQLUtil.toSQL(psUserIDxx));
      lsSQL.append(", dModified = " + SQLUtil.toSQL(poGRider.getServerDate()));
      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(fsTransNox));

      if(!pbWithParnt)
         poGRider.beginTrans();

      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getMaster().getTable(), "", "") == 0){
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record deleted");
      }
      else
         lbResult = true;

      if(!pbWithParnt){
         if(getErrMsg().isEmpty()){
            poGRider.commitTrans();
         }
         else
            poGRider.rollbackTrans();
      }

      return lbResult;
    }

   public String getMessage() {
      return psWarnMsg;
   }

   public void setMessage(String fsMessage) {
      this.psWarnMsg = fsMessage;
   }

   public String getErrMsg() {
      return psErrMsgx;
   }

   public void setErrMsg(String fsErrMsg) {
      this.psErrMsgx = fsErrMsg;
   }

   public void setBranch(String foBranchCD) {
      this.psBranchCD = foBranchCD;
   }
   
   public void setOrigin(String foOriginxx) {
      this.psOriginxx = foOriginxx  ;
   }
   
   public void setDepartment(String foDeptIDxx) {
      this.psDeptIDxx = foDeptIDxx;
   }
   
   public void setEdocsDepartment(String foDeptIDxx) {
      this.psDeptIDxx = foDeptIDxx;
   }
   
   public void setModule(String foModule) {
      this.psModuleCd = foModule;
   }
   
   public void setFileCd(String foFileCd) {
      this.psFileCode = foFileCd;
   }
   
   public void setNoPagesx(Integer foNoPagesx) {
      this.pnNoPagesx = foNoPagesx;
   }
   
   public void setNoCopies(Integer foNoCopies) {
      this.pnNoCopies = foNoCopies;
   }
   
   public void setB2BPages(String foB2BPages) {
      this.psB2BPages = foB2BPages;
   }
   
   public void setEmployee(String foEmployID) {
      this.psEmployID = foEmployID;
   }

   public void setWithParent(boolean fbWithParent) {
      this.pbWithParnt = fbWithParent;
   }

   public String getSQ_Master() {
      return (MiscUtil.makeSelect(new UnitEDocxMaster()));
   }

   public String getSQ_Detail() {
      return (MiscUtil.makeSelect(new UnitEDocxDetail()));
   }
   
// add methods here
   public void setGRider(GRider foGRider) {
      this.poGRider = foGRider;
      this.psUserIDxx = foGRider.getUserID();
      if(psBranchCD.isEmpty())
         psBranchCD = poGRider.getBranchCode();
   }

   public Date getTranDate() {
      return poTransact;
   }

   public void setTranDate(Date date) {
      this.poTransact = date;
   }
   
   private UnitEDocxMaster loadMaster(Connection con, String string){
      UnitEDocxMaster loOcc = new UnitEDocxMaster(); 

      //retrieve the record
//      StringBuilder lsSQL = new StringBuilder();
//      lsSQL.append(getSQ_Master());
//      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(string));

      String lsSQL = "SELECT" +
                        "  sTransNox" +
                        ", dTransact" +
                        ", sBranchCd" +
                        ", sDeptIDxx" +
                        ", sEmployID" +
                        ", sModuleCd" +
                        ", sRemarksx" +
                        ", nEntryNox" +
                        ", cTranStat" +
                        ", sModified" +
                        ", dModified" +
                      " FROM EDocSys_Master" +
                      " WHERE sTransNox =  " + SQLUtil.toSQL(string);
                            
      Statement loStmt = null;
      ResultSet loRS = null;

      try {
         loStmt = con.createStatement();
         loRS = loStmt.executeQuery(lsSQL.toString());

         if(!loRS.next())
             setMessage("No transaction Found!");
         else{
            //load each column to the entity
//            for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
//                loOcc.setValue(lnCol, loRS.getObject(lnCol));
                loOcc.setValue("sTransNox", loRS.getObject("sTransNox"));
                loOcc.setValue("dTransact", loRS.getObject("dTransact"));
                loOcc.setValue("sBranchCd", loRS.getObject("sBranchCd"));
                loOcc.setValue("sDeptIDxx", loRS.getObject("sDeptIDxx"));
                loOcc.setValue("nEntryNox", loRS.getObject("nEntryNox"));
                loOcc.setValue("sEmployID", loRS.getObject("sEmployID"));
                loOcc.setValue("sRemarksx", loRS.getObject("sRemarksx"));
//            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(EDocuments.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
      }
      
      return loOcc;
   }
   
   private ArrayList<UnitEDocxDetail> loadDetail(Connection con, String string){
      ArrayList <UnitEDocxDetail> detail = new ArrayList<UnitEDocxDetail>();

      //retrieve the record
//      StringBuilder lsSQL = new StringBuilder();
//      lsSQL.append(getSQ_Detail());
//      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(string));
      
         String lsSQL = "SELECT" +
                            "  a.sTransNox" +
                            ", a.nEntryNox" +
                            ", a.nPageNmbr" +
                            ", a.sFileCode" +
                            ", a.sFileName" +
                            ", a.sSourceNo" +
                            ", c.sCompnyNm" +
                            ", b.dPurchase" +
                            ", '' sEngineNo" +
                            ", '' sCRNoxxxx" +
                            ", a.sSourceCd" +
                            ", a.sSourceNo xSortName" +
                      " FROM EDocSys_Detail a" +
                            ", MC_AR_Master b" +
                            ", Client_Master c" +
                      " WHERE a.sSourceNo = b.sAcctNmbr" +
                            " AND b.sClientID = c.sClientID" +
                            " AND a.sTransNox =  " + SQLUtil.toSQL(string) +
                            " AND a.sSourceCd = 'MCAR'" +
                      " GROUP BY sTransNox, sSourceCd, sSourceNo" +
                      " UNION SELECT" +
                            "  a.sTransNox" +
                            ", a.nEntryNox" +
                            ", a.nPageNmbr" +
                            ", a.sFileCode" +
                            ", a.sFileName" +
                            ", a.sSourceNo" +
                            ", d.sCompnyNm" +
                            ", dRegister dPurchase" +
                            ", c.sEngineNo" +
                            ", b.sCRNoxxxx" +
                            ", a.sSourceCd" +
                            ", d.sCompnyNm xSortName" +
                      " FROM EDocSys_Detail a" +
                            ", MC_Serial_Registration b" +
                            ", MC_Serial c" +
                            ", Client_Master d" +
                      " WHERE a.sSourceNo = b.sSerialID" +
                            " AND b.sSerialID = c.sSerialID" +
                            " AND c.sClientID = d.sClientID" +
                            " AND a.sTransNox =  " + SQLUtil.toSQL(string) +
                            " AND a.sSourceCd = 'MCRg'" +
                      " GROUP BY sTransNox, sSourceCd, sSourceNo" +
                      " ORDER BY sSourceCd" +
                            ", xSortName" +
                            ", nEntryNox" +
                            ", sSourceNo" +
                            ", nPageNmbr";

      Statement loStmt = null;
      ResultSet loRS = null;
      System.out.println(lsSQL);
      try {
         loStmt = con.createStatement();
         loRS = loStmt.executeQuery(lsSQL.toString());

         if(!loRS.next()){
             setMessage("No transaction Found!");
         }else{
             do{
                UnitEDocxDetail loOcc = new UnitEDocxDetail();
                loOcc.setValue("sTransNox", loRS.getObject("sTransNox"));
                loOcc.setValue("nEntryNox", loRS.getObject("nEntryNox"));
                loOcc.setValue("sCompnyNm", loRS.getObject("sCompnyNm"));
                loOcc.setValue("sAcctNmbr", loRS.getObject("sSourceNo"));
                loOcc.setValue("dPurchase", loRS.getObject("dPurchase"));
                loOcc.setValue("sEngineNo", loRS.getObject("sEngineNo"));
                loOcc.setValue("sCRNoxxxx", loRS.getObject("sCRNoxxxx"));
                //load each column to the entity
//                    for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
//                        loOcc.setValue(lnCol, loRS.getObject(lnCol));
//                    }
                detail.add(loOcc);
             }while(loRS.next());
         }
      } catch (SQLException ex) {
         Logger.getLogger(EDocuments.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
      }
      
      return detail;
   }
   
   private ArrayList<UnitMasterFile> loadMasterFile(Connection con, String string){
      ArrayList <UnitMasterFile> masterfile = new ArrayList<UnitMasterFile>();

      //retrieve the record
//      StringBuilder lsSQL = new StringBuilder();
//      lsSQL.append(getSQ_Detail());
//      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(string));
      
//         String lsSQL = "SELECT" +
//                            "  sTransNox" +
//                            ", nEntryNox" +
//                            ", sFileName" +
//                            ", sSourceNo" +
//                            ", sSourceCd" +
//                            ", nPageNmbr" +
//                      " FROM EDocSys_Detail" +
//                      " WHERE sTransNox =  " + SQLUtil.toSQL(string) +
//                      " ORDER BY nEntryNox, nPageNmbr";

        String lsSQL = "SELECT" +
                            "  a.sTransNox" +
                            ", a.nEntryNox" +
                            ", a.nPageNmbr" +
                            ", a.sFileCode" +
                            ", a.sFileName" +
                            ", a.sSourceNo" +
                            ", c.sCompnyNm" +
                            ", b.dPurchase" +
                            ", '' sEngineNo" +
                            ", '' sCRNoxxxx" +
                            ", a.sSourceCd" +
                            ", a.sSourceNo xSortName" +
                      " FROM EDocSys_Detail a" +
                            ", MC_AR_Master b" +
                            ", Client_Master c" +
                      " WHERE a.sSourceNo = b.sAcctNmbr" +
                            " AND b.sClientID = c.sClientID" +
                            " AND a.sTransNox =  " + SQLUtil.toSQL(string) +
                            " AND a.sSourceCd = 'MCAR'" +
                      " UNION SELECT" +
                            "  a.sTransNox" +
                            ", a.nEntryNox" +
                            ", a.nPageNmbr" +
                            ", a.sFileCode" +
                            ", a.sFileName" +
                            ", a.sSourceNo" +
                            ", d.sCompnyNm" +
                            ", dRegister dPurchase" +
                            ", c.sEngineNo" +
                            ", b.sCRNoxxxx" +
                            ", a.sSourceCd" +
                            ", d.sCompnyNm xSortName" +
                      " FROM EDocSys_Detail a" +
                            ", MC_Serial_Registration b" +
                            ", MC_Serial c" +
                            ", Client_Master d" +
                      " WHERE a.sSourceNo = b.sSerialID" +
                            " AND b.sSerialID = c.sSerialID" +
                            " AND c.sClientID = d.sClientID" +
                            " AND a.sTransNox =  " + SQLUtil.toSQL(string) +
                            " AND a.sSourceCd = 'MCRg'" +
                      " ORDER BY sSourceCd" +
                            ", xSortName" +
                            ", nEntryNox" +
                            ", sSourceNo" +
                            ", nPageNmbr";

      Statement loStmt = null;
      ResultSet loRS = null;
      String lsSourceNo="";
      try {
         loStmt = con.createStatement();
         loRS = loStmt.executeQuery(lsSQL.toString());

         if(!loRS.next())
             setMessage("No transaction Found!");
         else{
             Integer lnEntryNo=0;
             do{
               
                UnitMasterFile loOcc = new UnitMasterFile(); 
                loOcc.setValue("sTransNox", loRS.getObject("sTransNox"));
                if(!lsSourceNo.equals(loRS.getString("sSourceNo"))){
                    lnEntryNo=lnEntryNo+1;
                }
                
                loOcc.setValue("nEntryNox", lnEntryNo);
                loOcc.setValue("sFileName", loRS.getObject("sFileName"));
                //load each column to the entity
//                    for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
//                        loOcc.setValue(lnCol, loRS.getObject(lnCol));
//                    }
                lsSourceNo=loRS.getString("sSourceNo");
                masterfile.add(loOcc);
             }while(loRS.next());
         }
      } catch (SQLException ex) {
         Logger.getLogger(EDocuments.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
      }
      
      return masterfile;
   }
   
   private UnitEDocxMaster saveMaster(Connection con, UnitEDocuments obj, String str){
      String lsSQL = "";
      UnitEDocxMaster loOcc = obj.getMaster();
      UnitEDocxMaster loOldEnt = new UnitEDocxMaster();
     
      loOcc.setBranchCd(psOriginxx);
      loOcc.setDeptIDxx(psDeptIDxx);
      loOcc.setEmployID(psEmployID);
      loOcc.setModuleCd(psModuleCd);
      loOcc.setFileCode(psFileCode);
      loOcc.setNoPagesx(pnNoPagesx);
      loOcc.setNoCopies(pnNoCopies);
      loOcc.setB2BPages(psB2BPages);
      loOcc.setModifiedBy(psUserIDxx);
      loOcc.setDateModified(poGRider.getServerDate());

      //Generate the SQL Statement
      if (str.equals("")) {
         loOcc.setValue(1, MiscUtil.getNextCode(loOcc.getTable(), "sTransNox", true, con, psBranchCD));
         lsSQL = "INSERT INTO EDocSys_Master SET" +
                    "  sTransNox = " + SQLUtil.toSQL(loOcc.getTransNox()) +
                    ", dTransact = " + SQLUtil.toSQL(loOcc.getTransact()) +
                    ", sBranchCd = " + SQLUtil.toSQL(loOcc.getBranchCd()) +
                    ", sDeptIDxx = " + SQLUtil.toSQL(loOcc.getDeptIDxx()) +
                    ", sEmployID = " + SQLUtil.toSQL(loOcc.getEmployID()) +
                    ", sRemarksx = " + SQLUtil.toSQL(loOcc.getRemarksx()) +
                    ", nEntryNox = " + (Integer) loOcc.getEntryNox() +
                    ", cTranStat = " + SQLUtil.toSQL(loOcc.getTranStat()) +
                    ", sModified = " + SQLUtil.toSQL(loOcc.getModifiedBy()) +
                    ", dModified = " + SQLUtil.toSQL(loOcc.getDateModified());                         
      } else {
          //Reload previous record
//          loOldEnt = (UnitEDocxMaster) loadTransaction(str);
          //Generate the UPDATE statement
//          lsSQL = MiscUtil.makeSQL((GEntity)loOcc, (GEntity)loOldEnt,
//                       "sTransNox = " + SQLUtil.toSQL(str));
         lsSQL = "UPDATE EDocSys_Master SET" +
                    "  dTransact = " + SQLUtil.toSQL(loOcc.getTransact()) +
                    ", sBranchCd = " + SQLUtil.toSQL(loOcc.getBranchCd()) +
                    ", sDeptIDxx = " + SQLUtil.toSQL(loOcc.getDeptIDxx()) +
                    ", sEmployID = " + SQLUtil.toSQL(loOcc.getEmployID()) +
                    ", sRemarksx = " + SQLUtil.toSQL(loOcc.getRemarksx()) +
                    ", nEntryNox = " + (Integer) loOcc.getEntryNox() +
                  " WHERE sTransNox = " + SQLUtil.toSQL(loOcc.getTransNox());
      }

      //No changes has been made
      if (lsSQL.equals("")) {
          setMessage("Record is not updated!");
          return loOcc;
      }
      
      System.out.println(loOcc.getTable());
      if(poGRider.executeQuery(lsSQL.toString(), loOcc.getTable(), "", "") == 0){
        System.out.println(lsSQL.toString());
         if(!poGRider.getErrMsg().isEmpty())
            setErrMsg(poGRider.getErrMsg());
         else
            setMessage("No record updated");
      }
      
      return loOcc; 
   }
   
   private ArrayList<UnitEDocxDetail> saveDetail(Connection con, UnitEDocuments obj, String str){
      String lsSQL = "";
      ArrayList<UnitEDocxDetail> loOcc = obj.getDetail();
      ArrayList<UnitEDocxDetail> loNewEnt = new ArrayList<UnitEDocxDetail>();
      String lsTransNox = obj.getMaster().getTransNox();
      int lnCtr=0;
      
      for(UnitEDocxDetail e: loOcc){
//          if(!e.getAcctNumber().isEmpty() && !e.getSourceCd().isEmpty()){              
          if(!e.getAcctNumber().isEmpty()){              
              lnCtr++;
              e.setTransNox(lsTransNox);
              e.setEntryNox(lnCtr);
                 
              if (str.equals("")) {
                  lsSQL = MiscUtil.makeSQL((GEntity)loOcc.get(lnCtr-1));
              }
              else{          
                  //Generate the UPDATE statement                  
              }
              
              if (!lsSQL.equals("")){
                  if(poGRider.executeQuery(lsSQL.toString(), e.getTable(), "", "") == 0){
                     if(!poGRider.getErrMsg().isEmpty())
                        setErrMsg(poGRider.getErrMsg());
                  }   
              }
              
              loNewEnt.add(e);
          }
      }
      
      if(loNewEnt.isEmpty()){
          setMessage("No detail found!");
      }
      else{
        if(!str.equals("")){
           //Do we have items to remove?
           lsSQL = "";
           for(UnitEDocxDetail e: loNewEnt){
               lsSQL = lsSQL + ", " + SQLUtil.toSQL(e.getAcctNumber());
           }
           
           String lsSQL1 = "SELECT sFileName " + 
                  " FROM " + loNewEnt.get(0).getTable() +
                  " WHERE sTransNox = " + SQLUtil.toSQL(str) +
                    " AND sAcctNmbr NOT IN(" + lsSQL.substring(1) + ")";
           
            Statement loStmt = null;
            ResultSet loRS = null;
            try {
                loStmt = con.createStatement();
                loRS = loStmt.executeQuery(lsSQL1.toString());

                //Delete items if found
                if(loRS.next()){
                    lsSQL1 = "DELETE FROM" + loNewEnt.get(0).getTable() +
                             " WHERE sTransNox = " + SQLUtil.toSQL(str) +
                               " AND sFileName NOT IN(" + lsSQL.substring(1) + ")";
                
                    poGRider.executeQuery(lsSQL.toString(), loNewEnt.get(0).getTable(), "", "");
                }
            } catch (SQLException ex) {
                Logger.getLogger(EDocuments.class.getName()).log(Level.SEVERE, null, ex);
                setErrMsg(ex.getMessage());
            }
            finally{
                MiscUtil.close(loRS);
                MiscUtil.close(loStmt);
            }
         }
     }
      
      return loNewEnt; 
   }   
     
   
   private ArrayList<UnitMasterFile> saveMasterFile(Connection con, UnitEDocuments obj, String str){
      String lsSQL = "";
      ArrayList<UnitMasterFile> loOcc = obj.getMasterFile();
      ArrayList<UnitMasterFile> loNewEnt = new ArrayList<UnitMasterFile>();
      String lsTransNox = obj.getMaster().getTransNox();
      int lnCtr=0;
      int lnEntryNox=0;
      String lsSourceNo="";
      
      for(UnitMasterFile e: loOcc){
          if(!e.getFileName().isEmpty()){              
              lnCtr++;
              e.setTransNox(lsTransNox);
                 
              if (str.equals("")) {
//                  lsSQL = MiscUtil.makeSQL((GEntity)loOcc.get(lnCtr-1));
                    if(lsSourceNo!= obj.getDetail().get(e.getEntryNox()-1).getSourceNo()){
                        lnEntryNox=1;
                    }else{
                        lnEntryNox=lnEntryNox+1;
                    }
                    lsSQL = "INSERT INTO EDocSys_Detail SET" +
                                "  sTransNox = " + SQLUtil.toSQL(lsTransNox) +
                                ", nEntryNox = " + (Integer) (1) + 
                                ", nPageNmbr = " + (Integer) lnEntryNox + 
                                ", sFileCode = " + SQLUtil.toSQL(obj.getMaster().getFileCode()) +
                                ", sFileName = " + SQLUtil.toSQL(e.getFileName()+".jpg") +                     
                                ", sSourceCd = " + SQLUtil.toSQL(obj.getDetail().get(e.getEntryNox()-1).getSourceCd()) +
                                ", sSourceNo = " + SQLUtil.toSQL(obj.getDetail().get(e.getEntryNox()-1).getSourceNo()) +
                                ", dModified = " + SQLUtil.toSQL(obj.getMaster().getDateModified());
                    
                    lsSourceNo = obj.getDetail().get(e.getEntryNox()-1).getSourceNo();
                    
                    if (obj.getDetail().get(e.getEntryNox()-1).getSourceCd()=="MCRg"){
                        String lsSQLRg = "UPDATE MC_Serial_Registration SET" +
                                            " cScannedx = '1'" +
                                        " WHERE sSerialID = " + SQLUtil.toSQL(obj.getDetail().get(e.getEntryNox()-1).getSourceNo());
                        poGRider.executeQuery(lsSQLRg, "", "", "");
                    }
              }
              else{
//                  UnitEDocxDetail f = (UnitEDocxDetail) loadDetail(con, str, e.getFileName(),"MCAR");
                  //Generate the UPDATE statement

//                  lsSQL = MiscUtil.makeSQL((GEntity)e, (GEntity)f,
//                          "sSourceNo = " + SQLUtil.toSQL(e.getFileName()) +
//                          " AND sSourceCd = " + SQLUtil.toSQL("MCAR"));                   
              }
              
              if (!lsSQL.equals("")){
                  if(poGRider.executeQuery(lsSQL.toString(), e.getTable(), "", "") == 0){
                     if(!poGRider.getErrMsg().isEmpty())
                        setErrMsg(poGRider.getErrMsg());
                  }   
              }
              
              loNewEnt.add(e);
          }
      }
      
      if(loNewEnt.isEmpty()){
          setMessage("No detail found!");
      }
      else{
        if(!str.equals("")){
           //Do we have items to remove?
           lsSQL = "";
           for(UnitMasterFile e: loNewEnt){
               lsSQL = lsSQL + ", " + SQLUtil.toSQL(e.getFileName());
           }
           
           String lsSQL1 = "SELECT sSourceNo " + 
                  " FROM " + loNewEnt.get(0).getTable() +
                  " WHERE sSourceCd = " + SQLUtil.toSQL("MCAR") +
                    " AND sSourceNo NOT IN(" + lsSQL.substring(1) + ")";
           
            Statement loStmt = null;
            ResultSet loRS = null;
            try {
                loStmt = con.createStatement();
                loRS = loStmt.executeQuery(lsSQL1.toString());

                //Delete items if found
                if(loRS.next()){
                    lsSQL1 = "DELETE FROM" + loNewEnt.get(0).getTable() +
                             " WHERE sSourceCd = " + SQLUtil.toSQL("MCAR") +
                               " AND sSourceNo NOT IN(" + lsSQL.substring(1) + ")";
                
                    poGRider.executeQuery(lsSQL.toString(), loNewEnt.get(0).getTable(), "", "");
                }
            } catch (SQLException ex) {
                Logger.getLogger(EDocuments.class.getName()).log(Level.SEVERE, null, ex);
                setErrMsg(ex.getMessage());
            }
            finally{
                MiscUtil.close(loRS);
                MiscUtil.close(loStmt);
            }
         }
     }
      
      return loNewEnt; 
   }   
   
   private boolean pbWithParnt = false;
   private String psBranchCD = "";
   private String psOriginxx = "";
   private String psDeptIDxx = "";
   private String psEmployID = "";
   private String psFileCode = "";
   private int pnNoPagesx = 1;
   private int pnNoCopies = 1;
   private String psB2BPages = "";
   private String psModuleCd = "";
   private String psUserIDxx = "";
   private String psWarnMsg = "";
   private String psErrMsgx = "";
   private GRider poGRider = null;
   private Date poTransact = null;
}
