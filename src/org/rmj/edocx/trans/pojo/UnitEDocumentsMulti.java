/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.edocx.trans.pojo;

import java.util.ArrayList;

/**
 *
 * @author jef
 */
public class UnitEDocumentsMulti {
   UnitEDocxMaster master;
   ArrayList <UnitEDocxDetail> detail;
   ArrayList <UnitMasterFile> masterfile;
   ArrayList <UnitSysFile> sysfile;
   
    enum Position
    {
        BEFORE, AFTER
    };
  
   public UnitEDocumentsMulti(){
      master = new UnitEDocxMaster();
      detail = new ArrayList<UnitEDocxDetail>();
      masterfile = new ArrayList<UnitMasterFile>();
      sysfile = new ArrayList<UnitSysFile>();
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
   
   public ArrayList<UnitSysFile> getSysFile(){
      return sysfile;
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
   
   public void setSysFile(ArrayList<UnitSysFile> sysfile){
      if(!this.sysfile.isEmpty())
         this.sysfile = new ArrayList<UnitSysFile>();
      this.sysfile.addAll(sysfile);
   }
   
   public int ItemCount(){
      if (detail.isEmpty()) return 0;
        
      return detail.size();
   }
   
   public int MasterFileItemCount(){
      if (masterfile.isEmpty()) return 0;
        
      return masterfile.size();
   }
   
   public int SysFileItemCount(){
      if (sysfile.isEmpty()) return 0;
        
      return sysfile.size();
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
   
    public void addSysFile(){
       this.getSysFile().add(new UnitSysFile());
   }
}
