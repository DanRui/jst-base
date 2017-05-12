package com.jst.common.xml;



import java.util.List;
import java.util.ArrayList;


public class XMLBuilder {

  private String xmlVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private String retCode="";
  private String retMsg="";
  private String docXml="";
  private String headXml="";
  private String bodyXml="";

  private List recordList=new ArrayList();


  public XMLBuilder() {
  }


  public void setHead(String retCode,String retMsg){
    this.retCode=retCode;
    this.retMsg=retMsg;
  }

  /**
   * 添加Record,不包括标记REC
   * @param recXml String
   */
  public void addRecord(String recXml){
    recordList.add(recXml);
  }

  /**
   * 设置RecordList,不包括标记REC
   * @param recXmlList List
   */
  public void setRecordList(List recXmlList){
  this.recordList=recXmlList;
}


  public String buildXml(){



    docXml=xmlVersion;
    docXml+="<MSG>";
    docXml+=buildHeadXml();
    docXml+=buildBodyXml();
    docXml+="</MSG>";

    //docXml=  CharacterConvertor.getChineseString(docXml);
    return docXml;


  }

  private String buildHeadXml(){
    headXml="<HEAD>";
    headXml=headXml+"<RET_CODE>"+retCode+"</RET_CODE><RET_MSG>"+retMsg+"</RET_MSG>";
    headXml+="</HEAD>";
    return headXml;
  }

  private String buildBodyXml(){

   bodyXml="<BODY>";

   if(recordList!=null && recordList.size() >0){
     for (int index = 0; index < recordList.size(); index++) {
       String tempRec;

       tempRec = "<REC>";
       tempRec += (String) recordList.get(index);
       tempRec += "</REC>";

       bodyXml += tempRec;
     }
   }
   bodyXml+="</BODY>";

   return bodyXml;
  }


  public static void main(String[] argv){

    XMLBuilder xmlBuilder=new XMLBuilder();
    xmlBuilder.setHead("T","成功");
    List recList=new ArrayList();
    recList.add("<CODE>01</CODE><NAME>大型汽车</NAME>");
    recList.add("<CODE>02</CODE><NAME>小型汽车</NAME>");
    recList.add("<CODE>06</CODE><NAME>外籍汽车</NAME>");
    xmlBuilder.setRecordList(recList);
    String xml= xmlBuilder.buildXml();

    System.out.println(xml) ;

  }

}
