package com.jst.common.utils;

import java.io.Serializable;


public class ComObject implements  Serializable{

  protected String id="";
  protected String name="";
  protected String remark="";
  protected String remark1="";
  protected Long sortId=new Long(10000);

  public ComObject(){    
  }

  public ComObject(String id){
    this.id=id;
  }
  public ComObject(String id,String name){
    this.id=id;
    this.name=name;
  }
  public ComObject(String id,String name,Long sortId){
      this.id=id;
      this.name=name;
      this.sortId=sortId;
    }

  public ComObject(String id,String name,String remark){
      this.id=id;
      this.name=name;
      this.remark=remark;
    }

public ComObject(String id,String name,String remark,String remark1,Long sortId){
      this.id=id;
      this.name=name;
      this.remark=remark;
      this.remark1=remark1;
      this.sortId=sortId;
    }
    public boolean equals(Object obj){
      if(obj instanceof ComObject){
        ComObject comObj=(ComObject)obj;
        if (this.id .equals(comObj.getId()) && this.name.equals(comObj.getName()) &&
            this.remark.equals(comObj.getRemark()) &&
            this.remark1.equals(comObj.getRemark1()) &&
            this.sortId.equals(comObj.getSortId()))
          return true;
        else
          return false;
      }
      else
        return false;
    }

  public String getId(){
    return this.id;
  }


  public void setId(String id){
    this.id =id;
  }

  public String getName(){
    return this.name ;
  }

  public void setName(String name){
    this.name =name;
  }

 public void setReamrk(String remark){
   this.remark=remark;
 }

 public String getRemark(){
   return this.remark;
 }

 public void setReamrk1(String remark1){
   this.remark1=remark1;
 }

 public String getRemark1(){
   return this.remark1;
 }


 public void setSortId(Long sortId){
   this.sortId=sortId;
 }

 public Long getSortId(){
   return this.sortId;
 }

  public static void main(String[] args) {
  }
}
