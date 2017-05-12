/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jst.common.webservice;

import org.apache.axis2.addressing.EndpointReference;


import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import javax.xml.namespace.QName;
import com.jst.common.xml.JDomUtil;
import org.jdom.xpath.XPath;
import org.jdom.Document;
import org.jdom.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public class WebServiceClient {

    private static final Log log = LogFactory.getLog(WebServiceClient.class);
    private String url = "http://localhost:8080/platform/services/PlatformService";
    private RPCServiceClient serviceClient = null;
    private Options options = null;

    public WebServiceClient() {
    }

    public WebServiceClient(String url) throws Exception {

        log.debug("实例化 WebServiceClient,url is"+url);

        try{
        
        this.url = url;

        serviceClient = new RPCServiceClient();
        options = serviceClient.getOptions();

        EndpointReference targetEPR = new EndpointReference(url);
        options.setTo(targetEPR);
        options.setTimeOutInMilliSeconds(1000 * 60);//60s
        options.setCallTransportCleanup(true);
        serviceClient.setOptions(options);
        log.error("实例化 WebServiceClient 结束");

        }catch(Exception e){
            log.error("实例化 WebServiceClient 失败");
            throw e;
        }

       /*
        ConfigurationContext context = serviceClient.getServiceContext().getConfigurationContext();
        context.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
        context.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, serviceClient);
        context.setProperty(HTTPConstants.AUTO_RELEASE_CONNECTION, true);
        */
       



    }

    /**
     * 调用WebService
     * @param methodName
     * @param paraArra
     * @return
     * @throws Exception
     */
    public String callService(String methodName, Object[] paraArra) throws Exception {

        log.debug("调用 callService 开始,methodName is"+url);
        try {
            //指定参数数组
            Object[] opAddEntryArgs = paraArra;//new Object[] {"001","test","test123"};

            //   指定方法返回值的数据类型的Class对象
            Class[] classes = new Class[]{String.class};

            //   指定要调用的方法及WSDL文件的命名空间
            QName opAddEntry = new QName("http://ws.apache.org/axis2", methodName);
            //   调用方法并输出该方法的返回值

           
            String result = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0].toString();
           

             log.debug("调用 callService 方法 结束,result:"+result);
           
            return result;
            //

        } catch (Exception e) {
            log.error("服务平台调用 失败",e);
            throw new Exception("服务平台调用失败：" + e.getMessage());
        }


    }

    public void close(){
         log.debug("调用 colse　开始");
         if(serviceClient!=null){
                try{
                serviceClient.cleanup();
                serviceClient.cleanupTransport();
                }catch(Exception e2){}
            }
         log.debug("调用 colse 结束");
    }

    public void test() throws Exception {

        //   使用RPC方式调用WebService

        //   指定调用WebService的URL

        //   指定方法的参数值
        Object[] opAddEntryArgs = new Object[]{"001", "test", "test123"};
        //   指定方法返回值的数据类型的Class对象
        Class[] classes = new Class[]{String.class};
        //   指定要调用的方法及WSDL文件的命名空间
        QName opAddEntry = new QName("http://ws.apache.org/axis2", "checkLogin");
        //   调用方法并输出该方法的返回值

        String result = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0].toString();

        log.debug("result:" + result);


        JDomUtil jDomUtil = new JDomUtil();
        Document xmlDoc = jDomUtil.build(result);
        Element retCodeEle = (Element) XPath.selectSingleNode(xmlDoc, "//MSG//HEAD//RET_CODE");
        Element retMsgEle = (Element) XPath.selectSingleNode(xmlDoc, "//MSG//HEAD//RET_MSG");

        log.debug("ret_code:" + retCodeEle.getValue());
        log.debug("ret_msg:" + retMsgEle.getValue());


    }
}
