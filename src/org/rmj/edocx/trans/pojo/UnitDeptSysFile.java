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
public class UnitDeptSysFile {
   UnitSysFileMaster master;
   ArrayList <UnitSysFile> sysfile;
   
    enum Position
    {
        BEFORE, AFTER
    };
  
   public UnitDeptSysFile(){
      master = new UnitSysFileMaster();
      sysfile = new ArrayList<UnitSysFile>();
   }

   public UnitSysFileMaster getMaster(){
      return master;
   }
   
   public ArrayList<UnitSysFile> getSysFile(){
      return sysfile;
   }

   public void setMaster(Object master){
      this.master = (UnitSysFileMaster) master;
   }
   
   public void setSysFile(ArrayList<UnitSysFile> sysfile){
      if(!this.sysfile.isEmpty())
         this.sysfile = new ArrayList<UnitSysFile>();
      this.sysfile.addAll(sysfile);
   }
   
   public int SysFileItemCount(){
      if (sysfile.isEmpty()) return 0;
        
      return sysfile.size();
   }
}
