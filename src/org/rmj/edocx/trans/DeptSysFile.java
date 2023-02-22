package org.rmj.edocx.trans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.edocx.trans.pojo.UnitDeptSysFile;
import org.rmj.edocx.trans.pojo.UnitSysFileMaster;
import org.rmj.edocx.trans.pojo.UnitSysFile;

/**
 *
 * @author jef
 */
public class DeptSysFile {
   public Object newTransaction() {
      UnitDeptSysFile loOcc = new  UnitDeptSysFile();
      Connection loCon = poGRider.getConnection();

      if(psBranchCD.equals("")) {
         psBranchCD = poGRider.getBranchCode();
      }
      return loOcc;
   }

   public Object loadTransaction(String string) {
      UnitDeptSysFile loOcc = new UnitDeptSysFile();
      Connection loCon = poGRider.getConnection();

      //Load the master record
      loOcc.setMaster(loadMaster(loCon, string));
      
      //Load detail if no error was encountered during loadMaster();
      if(getErrMsg().isEmpty() || getMessage().isEmpty())
          loOcc.setSysFile(loadSysFile(loCon, string));
                    
        //Close connection if this is not a parent class...
//      if(!pbWithParnt)
//        MiscUtil.close(loCon);      
        return loOcc;
   }
   
   public void setFileCd(String foFileCd) {
      this.psFileCode = foFileCd;
   }

    public Object saveUpdate(Object foEntity, String fsTransNox) {
        UnitDeptSysFile loNewEnt = null;
        UnitDeptSysFile loResult = null;
      
        Connection loCon = poGRider.getConnection();

        // Check for the value of foEntity
        if (!(foEntity instanceof UnitDeptSysFile)) {
            setErrMsg("Invalid Entity Passed as Parameter");
            return foEntity;
        }
      
        // Typecast the Entity to this object
        loNewEnt = (UnitDeptSysFile) foEntity;
      
        //TODO: Test the user rights in these area...
        System.out.println(!pbWithParnt);
        if(!pbWithParnt)
            poGRider.beginTrans();
      
//        //Save the master transaction 
//        UnitSysFileMaster loSysFileMaster = saveMaster(loCon, loNewEnt, fsTransNox);
//        UnitSysFile loSysFile;
//        if (loSysFileMaster != null){
//            //save detail if master was saved successfully...
////            saveDetail(loCon, loNewEnt, fsTransNox);
            saveSysFile(loCon, loNewEnt, fsTransNox);
//        }
        
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
        
        loResult = (UnitDeptSysFile) loadTransaction(psDeptIDxx);
        
        return loResult;
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
   
   public String getSQ_Master() {
      return (MiscUtil.makeSelect(new UnitSysFileMaster()));
   }
   
// add methods here
   public void setGRider(GRider foGRider) {
      this.poGRider = foGRider;
      this.psUserIDxx = foGRider.getUserID();
      if(psBranchCD.isEmpty())
         psBranchCD = poGRider.getBranchCode();
   }
   
   private UnitSysFileMaster loadMaster(Connection con, String string){
      UnitSysFileMaster loOcc = new UnitSysFileMaster(); 

      //retrieve the record
//      StringBuilder lsSQL = new StringBuilder();
//      lsSQL.append(getSQ_Master());
//      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(string));

      String lsSQL = "SELECT" +
                        "  sDeptIDxx" +
                        ", sDeptName" +
                      " FROM Department" +
                      " WHERE sDeptIDxx =  " + SQLUtil.toSQL(string);

//        String lsSQL = "SELECT" +
//                " a.sDeptIDxx" +
//                ", a.sFileCode" +
//                ", b.sDeptName" +
//                ", c.sBarrcode" +
//                ", c.sBriefDsc" +
//                ", c.nNoPagesx" +
//                ", c.nNoCopies" +
//                ", c.sB2BPagex" +
//                " FROM EDocSys_Department_File a" +
//                " LEFT JOIN Department b" +
//                " ON a.sDeptIDxx = b.sDeptIDxx" +
//                " LEFT JOIN EDocSys_File c" +
//                " ON a.sFileCode = c.sFileCode" +
//                " WHERE a.sDeptIDxx = "+ SQLUtil.toSQL(string) +
//                " GROUP BY sDeptIDxx";
                            
      Statement loStmt = null;
      ResultSet loRS = null;
      try {
         loStmt = con.createStatement();
         loRS = loStmt.executeQuery(lsSQL.toString());

         if(!loRS.next())
             setMessage("No transaction Found!");
         else{
            //load each column to the entity
            for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
                loOcc.setValue(lnCol, loRS.getObject(lnCol));
//                psDeptIDxx=loRS.getObject("sDeptIDxx").toString();
//                loOcc.setValue("sTransNox", loRS.getObject("sTransNox"));
                loOcc.setValue("sDeptIDxx",loRS.getObject("sDeptIDxx"));
//                loOcc.setV(loRS.getObject("sDeptName").toString());
            }
         }
      } catch (SQLException ex) {
         Logger.getLogger(DeptSysFile.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
      }
      
      return loOcc;
   }
   
   private ArrayList<UnitSysFile> loadSysFile(Connection con, String string){
      ArrayList <UnitSysFile> sysfile = new ArrayList<UnitSysFile>();

      //retrieve the record
//      StringBuilder lsSQL = new StringBuilder();
//      lsSQL.append(getSQ_Detail());
//      lsSQL.append(" WHERE sTransNox = " + SQLUtil.toSQL(string));
      
//         String lsSQL = "SELECT" +
//                        "  a.sTransNox" +
//                        ", a.nEntryNox" +
//                        ", a.nPageNmbr" +
//                        ", a.sFileCode" +
//                        ", a.sFileName" +
//                        ", a.sSourceNo" +
//                        ", c.sCompnyNm" +
//                        ", b.dPurchase" +
//                      " FROM EDocSys_Detail a" +
//                        ", MC_AR_Master b" +
//                        ", Client_Master c" +
//                      " WHERE a.sSourceNo = b.sAcctNmbr" +
//                        " AND b.sClientID = c.sClientID" +
//                        " AND a.sTransNox =  " + SQLUtil.toSQL(string) +
//                      " ORDER BY a.nEntryNox, a.sSourceNo, a.nPageNmbr";
       
        String lsSQL = "SELECT " +
                            " a.sDeptIDxx" +
                            ", a.sFileCode" +
                            ", b.sBarrcode" +
                            ", b.sBriefDsc" +
                            ", b.nNoCopies" +
                            ", b.nNoPagesx" +
                            ", b.sB2BPagex" +
                            " FROM EDocSys_Department_File a" +
                            " LEFT JOIN EDocSys_File b" +
                            " ON a.sFileCode = b.sFileCode" +
                            " WHERE a.sDeptIDxx = "+ SQLUtil.toSQL(string) +
                            " ORDER BY a.nEntryNox DESC" ;

      Statement loStmt = null;
      ResultSet loRS = null;
      String lsAcctNmbr="";
      try {
         loStmt = con.createStatement();
         loRS = loStmt.executeQuery(lsSQL.toString());

         if(!loRS.next())
             setMessage("No transaction Found!");
         else{
//             Integer lnEntryNo=0;
             do{
               
                UnitSysFile loOcc = new UnitSysFile(); 
                loOcc.setValue("sFileCode", loRS.getObject("sFileCode"));
//                if(!lsAcctNmbr.equals(loRS.getString("sSourceNo"))){
//                    lnEntryNo=lnEntryNo+1;
//                }
//                
                
                loOcc.setValue("sBarrcode", loRS.getObject("sBarrcode"));
                loOcc.setValue("sBriefDsc", loRS.getObject("sBriefDsc"));
                loOcc.setValue("nNoPagesx", loRS.getObject("nNoPagesx"));
                loOcc.setValue("nNoCopies", loRS.getObject("nNoCopies"));
                loOcc.setValue("sB2BPagex", loRS.getObject("sB2BPagex"));
                //load each column to the entity
//                    for(int lnCol=1; lnCol<=loRS.getMetaData().getColumnCount(); lnCol++){
//                        loOcc.setValue(lnCol, loRS.getObject(lnCol));
//                    }
//                lsAcctNmbr=loRS.getString("sSourceNo");
                sysfile.add(loOcc);
             }while(loRS.next());
         }
      } catch (SQLException ex) {
         Logger.getLogger(DeptSysFile.class.getName()).log(Level.SEVERE, null, ex);
         setErrMsg(ex.getMessage());
      }
      finally{
         MiscUtil.close(loRS);
         MiscUtil.close(loStmt);
      }
      
      return sysfile;
   }
   
   private UnitSysFileMaster saveMaster(Connection con, UnitDeptSysFile obj, String str){
      String lsSQL = "";
      UnitSysFileMaster loOcc = obj.getMaster();
      UnitSysFileMaster loOldEnt = new UnitSysFileMaster();
   
      loOcc.setDeptIDxx(psDeptIDxx);
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
   
   private ArrayList<UnitSysFile> saveSysFile(Connection con, UnitDeptSysFile obj, String str){
      String lsSQL = "";
      ArrayList<UnitSysFile> loOcc = obj.getSysFile();
      ArrayList<UnitSysFile> loNewEnt = new ArrayList<UnitSysFile>();
      String lsTransNox = obj.getMaster().getDeptIDxx();
      int lnCtr=0;
      
      for(UnitSysFile e: loOcc){
               e.setTransNox(lsTransNox);
//          ", sFileCode = " + SQLUtil.toSQL(obj.getSysFile().get(lnCtr).getFileCode()) +
          if(!e.getFileCode().isEmpty()){ 
              if (str.equals("")) {
//                  lsSQL = MiscUtil.makeSQL((GEntity)loOcc.get(lnCtr-1));

                    lsSQL = "INSERT INTO EDocSys_Department_File SET" +
                                "  sDeptIDxx = " + SQLUtil.toSQL(psDeptIDxx) +
                                ", nEntryNox = " + SQLUtil.toSQL((int)lnCtr + 1) +
                                ", sFileCode = " + SQLUtil.toSQL(e.getFileCode()) +
                                ", cRecdStat = '1'" +
                                ", sModified = " + SQLUtil.toSQL(psUserIDxx) +
                                ", dModified =  " + SQLUtil.toSQL(poGRider.getServerDate());
              }
              else{
//                  UnitEDocxDetail f = (UnitEDocxDetail) loadDetail(con, str, e.getFileName(),"MCAR");
                  //Generate the UPDATE statement

//                  lsSQL = MiscUtil.makeSQL((GEntity)e, (GEntity)f,
//                          "sSourceNo = " + SQLUtil.toSQL(e.getFileName()) +
//                          " AND sSourceCd = " + SQLUtil.toSQL("MCAR"));                   
              }
              lnCtr++;
              
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
//           for(UnitSysFile e: loNewEnt){
//               lsSQL = lsSQL + ", " + SQLUtil.toSQL(e.ge());
//           }
//           
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
                Logger.getLogger(DeptSysFile.class.getName()).log(Level.SEVERE, null, ex);
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
   private String psUserIDxx = "";
   private String psFileCode = "";
   private String psWarnMsg = "";
   private String psErrMsgx = "";
   private GRider poGRider = null;
}
