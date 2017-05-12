/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jst.common.xml;


import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import java.io.*;
import javax.xml.*;
import java.net.URL;
import org.jdom.xpath.XPath;
import java.util.*;




import org.xml.sax.InputSource;

public class JDomUtil {

  private SAXBuilder sb = new SAXBuilder();
  private   Document doc=null;
  private Element root=null;

  public JDomUtil() {
  }


  public JDomUtil(SAXBuilder sb) {
    this.sb=sb;
  }

  public JDomUtil(Document doc) {
    this.doc=doc;
    this.root=doc.getRootElement() ;
  }


  public Document build(File xmlFile) throws Exception{
    this.doc=sb.build(xmlFile);

    return this.doc ;
  }

//error here
  public Document build(String xmlString) throws Exception{

    try{
      InputSource inputSource = new InputSource(new StringReader(xmlString));
      this.doc = sb.build(inputSource);
      this.root =doc.getRootElement() ;
    }catch(Exception e){
      System.out.println("build exp:"+e.getMessage()) ;
      e.printStackTrace() ;
    }

    return this.doc ;
  }

  public Document build(URL xmlUrl) throws Exception{
     this.doc=sb.build(xmlUrl);

     return this.doc ;
   }




  public Document getDocument(){
    return this.doc ;
  }
  public Element getRoot(){
    return this.root ;
  }

  public String getElementData(String elementName) throws Exception{

      String eleData=null;
      Element theElement= (Element)XPath.selectSingleNode(this.root , elementName);
      if(theElement!=null)
        eleData=theElement.getTextTrim();
    return eleData;

  }




  public Element getElement(String elementName) throws Exception{
      return (Element)XPath.selectSingleNode(this.root , elementName);
  }

   public Element getElement(Object context,String elementName) throws Exception{
      return (Element)XPath.selectSingleNode(context , elementName);
  }

  public List getElementList(String elementName) throws Exception{
      return XPath.selectNodes(this.root, elementName);
      
  }

    public List getElementList(Object context,String elementName) throws Exception{
      return XPath.selectNodes(context, elementName);
      

  }


  public static void main(String[] argvs){
    test2();
  }

  public static void test(){

   String xmlString="";
   xmlString = xmlString + "<RecordList>" ;
   for(int index=1;index<10;index++){
     xmlString = xmlString + "<Record>";
     xmlString = xmlString + "<Item>aaa"+index+"</Item><Item>bbb"+index+"</Item><Item1>ccc"+index+"</Item1>";
     xmlString = xmlString + "</Record>";
   }
   xmlString = xmlString + "</RecordList>";

   try{
     JDomUtil jdomUtil = new JDomUtil();
     jdomUtil.build(xmlString);
     Element root=jdomUtil.getRoot() ;

     List list=root.getChildren() ;
     System.out.print("counter:"+list.size()) ;

     String item1=jdomUtil.getElementData("Item1");

     System.out.println("item1:"+item1) ;




     /*
 NodeList nodeList=root.getChildNodes() ;//doc.getElementsByTagName("RecordList");
 int rowCounter=nodeList.getLength() ;

 Node tempRow=null;

 for(int rowIndex=0;rowIndex<rowCounter;rowIndex++){
   tempRow=nodeList.item(rowIndex) ;
   NodeList columnList=tempRow.getChildNodes() ;
      int columnCounter=columnList.getLength() ;
      for(int columnIndex=0;columnIndex<columnCounter;columnIndex++){
        Node tempColumn=columnList.item(columnIndex) ;
        System.out.print(tempColumn.getFirstChild().getNodeValue()) ;
      }
 }

    */
   }catch(Exception  e){
     e.printStackTrace() ;
     System.out.println("exp:"+e.getMessage() ) ;
   }

  }


  public static void test2(){

   String xmlString="";
   xmlString = xmlString + "<RecordList>" ;
   for(int index=1;index<10;index++){
     xmlString = xmlString + "<Record>";
     xmlString = xmlString + "<Item>aaa"+index+"</Item><Item>bbb"+index+"</Item><Item1>ccc"+index+"</Item1>";
     xmlString = xmlString + "</Record>";
   }
   xmlString = xmlString + "</RecordList>";

   try{
     JDomUtil jdomUtil = new JDomUtil();
     jdomUtil.build(xmlString);
    // Element root=jdomUtil.getRoot() ;

     List list=jdomUtil.getElementList("//RecordList//Record");
     System.out.print("counter:"+list.size()) ;

     for(int index=0;index<list.size() ;index++){
       Element recordElemnt=(Element)list.get(index);
       List itemList=jdomUtil.getElementList(recordElemnt,"//Item");
        for(int index2=0;index2<itemList.size();index2++){
           Element itemElement=(Element)itemList.get(index2);
           String item = itemElement.getTextTrim();
           System.out.println("item:" + item);
        }
     }


   }catch(Exception  e){
     e.printStackTrace() ;
     System.out.println("exp:"+e.getMessage() ) ;
   }

  }


}
