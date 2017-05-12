package com.jst.common.jsp;

import javax.servlet.http.HttpSession;


/**
 * 
 */

import java.io.PrintWriter;

public class JspCommon {

  public JspCommon() {
  }
  public static void main(String[] args) {
    
  }
  
  public static boolean checkLogin(HttpSession session){
	  
		return true;

	}
  

  public static String setSelectFlag(String inputValue,String arctualValue){

    String selectFlag="";

    if(inputValue== null || arctualValue==null)
      return selectFlag;

    if(inputValue.equals(arctualValue) ){

      selectFlag=" selected ";

    }

    return selectFlag;

  }
  
  public static String setSelectedFlag(String inputValue,String arctualValue){

	    String selectFlag="";

	    if(inputValue== null || arctualValue==null)
	      return selectFlag;

	    if(inputValue.equals(arctualValue) ){

	      selectFlag=" selected ";

	    }

	    return selectFlag;

	  }
  
  
  public static String setCheckedFlag(String inputValue,String arctualValue){

	    String checkedFlag="";

	    if(inputValue== null || arctualValue==null)
	      return checkedFlag;

	    if(inputValue.equals(arctualValue) ){

	    	checkedFlag=" checked ";

	    }

	    return checkedFlag;

	  }

  public static String showText( String text){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="alert('" + text + "');\n";
    returnVal+="</script>\n";

    return returnVal;
  }

  public static String  goHistoryPage(String pageNum){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="history.go(" + pageNum + ");\n";
    returnVal+="</script>\n";

    return returnVal;
  }

  public static void  goHistoryPage(int historyPage ,PrintWriter out){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="history.go(" + historyPage + ");\n";
    returnVal+="</script>\n";

    out.println(returnVal) ;


  }

  public static String goURL(String url){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="window.location='" +url+ "';\n";
    returnVal+="</script>\n";

    return returnVal;
  }

  public static String topGoURL(String url){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="top.window.location='" +url+ "';\n";
    returnVal+="</script>\n";

    return returnVal;
  }

  public static String  defJavaVar(String name,String value){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="var  "+ name + ";\n";
    returnVal+=name + " ='" + value + "';\n";
    returnVal+="</script>";

    return returnVal;

  }

  public static void shwoText(String text,PrintWriter out){
    out.println( showText(text)) ;
  }

  public static void closeWin(PrintWriter out){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="window.close();\n";
    returnVal+="</script>\n";

    out.println(returnVal) ;

  }

  public static String closeWin(){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="window.close();\n";
    returnVal+="</script>\n";

    return returnVal;

  }
  
  public static String closeTopWin(){

	    String returnVal="";

	    returnVal+="<script language=javaScript>\n";
	    returnVal+="top.window.close();\n";
	    returnVal+="</script>\n";

	    return returnVal;

	  }


  public static void reloadOpener(PrintWriter out){
    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="top.opener.window.location.reload();\n";
    returnVal+="</script>\n";

    out.println(returnVal) ;
  }

  public static String reloadOpener(){
    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="top.opener.window.location.reload();\n";
    returnVal+="</script>\n";

    return returnVal ;
  }


  public static void reloadMe(PrintWriter out){
    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+="window.location.reload();\n";
    returnVal+="</script>\n";

    out.println(returnVal) ;
  }


  /**
   * windowName include frame name
   * @param windowName String
   * @param out PrintWriter
   */

  public static void reloadWindow(String windowName,PrintWriter out){
   String returnVal="";

   returnVal+="<script language=javaScript>\n";
   returnVal=returnVal+windowName+".window.location.reload();\n";
   returnVal+="</script>\n";

   out.println(returnVal) ;
 }


  public static long getStartRow(int pageNo ,int pageSize){
    long startRow=0;

    startRow=(pageNo-1)*pageSize+1;
    return startRow;
  }
  public static long getEndRow(int pageNo ,int pageSize){
    long endRow=0;
    endRow=pageNo*pageSize;
    return endRow;
  }

 
  
  public static String getSpacesRows(int rowsCounter, int colsCounter,
			String para1, String para2) {

		String rowsHtml = "";

		for (int rIndex = 0; rIndex < rowsCounter; rIndex++) {//��ʾ�ո���			
			rowsHtml += "<tr>";
			for (int cIndex = 0; cIndex < colsCounter; cIndex++) {
				rowsHtml += "<td>&nbsp;</td>";
			}
			rowsHtml += "</tr>";
		}
		return rowsHtml;
	}
  
  public static void executeCommand(String command, PrintWriter out) {

		String returnVal = "";

		returnVal += "<script language=javaScript>\n";
		returnVal += "document.execCommand('" + command + "');\n";
		returnVal += "</script>\n";

		out.println(returnVal);

	}



  public static String asHTML(String text) {
    if (text == null)
      return "";
    StringBuffer results = null;
    char[] orig = null;
    int beg = 0, len = text.length();
    for (int i = 0; i < len; ++i) {
      char c = text.charAt(i);
      switch (c) {
        case 0:
        case '&':
        case '<':
        case '>':
        case '"':
          if (results == null) {
            orig = text.toCharArray();
            results = new StringBuffer(len + 10);
          }
          if (i > beg)
            results.append(orig, beg, i - beg);
          beg = i + 1;
          switch (c) {
            default: // case 0:
              continue;
            case '&':
              results.append("&");
              break;
            case '<':
              results.append("<");
              break;
            case '>':
              results.append(">");
              break;
            case '"':
              results.append("\"");
                             break;

              }
                  break;
          }
      }
      if (results == null)
        return text;
      results.append(orig, beg, len - beg);
      return results.toString();
    }

  public static void  runScript(String scriptStr ,PrintWriter out){

    String returnVal="";

    returnVal+="<script language=javaScript>\n";
    returnVal+=scriptStr+"\n";
    returnVal+="</script>\n";

    out.println(returnVal) ;

  }

}
