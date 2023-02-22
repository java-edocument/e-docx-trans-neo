/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.edocx.trans.pojo;

import java.util.ArrayList;
import org.rmj.appdriver.constants.EditMode;

/**
 *
 * @author jef
 */
public class UnitEDocuments {
   UnitEDocxMaster master;
   ArrayList <UnitEDocxDetail> detail;
   ArrayList <UnitMasterFile> masterfile;
   
    enum Position
    {
        BEFORE, AFTER
    };
  
   public UnitEDocuments(){
      master = new UnitEDocxMaster();
      detail = new ArrayList<UnitEDocxDetail>();
      masterfile = new ArrayList<UnitMasterFile>();
   }

   public UnitEDocxMaster getMaster(){
      return master;
   }

   public ArrayList<UnitEDocxDetail> getDetail(){
      return detail;
   }
   
   public ArrayList<UnitMasterFile> getMasterFile(){
      return masterfile;
   }

   public void setMaster(Object master){
      this.master = (UnitEDocxMaster) master;
   }

   public void setDetail(ArrayList<UnitEDocxDetail> detail){
      if(!this.detail.isEmpty())
         this.detail = new ArrayList<UnitEDocxDetail>();
      this.detail.addAll(detail);
   }
   
   public void setMasterFile(ArrayList<UnitMasterFile> masterfile){
      if(!this.masterfile.isEmpty())
         this.masterfile = new ArrayList<UnitMasterFile>();
      this.masterfile.addAll(masterfile);
   }
   
   public int ItemCount(){
      if (detail.isEmpty()) return 0;
        
      return detail.size();
   }
   
   public int MasterFileItemCount(){
      if (masterfile.isEmpty()) return 0;
        
      return masterfile.size();
   }
   
   public void move(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            masterfile.add(toPosition, masterfile.get(fromPosition));
            masterfile.remove(fromPosition);
        } else {
            masterfile.add(toPosition, masterfile.get(fromPosition));
            masterfile.remove(fromPosition + 1);
        }
   }
   
   public void addDetail(){
       this.getDetail().add(new UnitEDocxDetail());
   }
   
   public void addMasterFile(){
       this.getMasterFile().add(new UnitMasterFile());
   }
}
