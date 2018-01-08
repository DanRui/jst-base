package com.jst.common.system.aspect.impl;

import com.jst.common.system.annotation.Cache;
import com.jst.common.utils.ServiceUtil;
import com.jst.type.DataType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

public class MessageAspect
        implements Serializable
{
    public Object doServiceAround(ProceedingJoinPoint pjp)
            throws Throwable
    {
        Object xb = null;
        if (!AopUtils.isAopProxy(pjp.getTarget())) {
            xb = pjp.getTarget();
        } else {
            xb = getProxyTargetObject(pjp.getTarget());
        }
        pjp.getSignature().getName();

        Object o = null;
        try
        {
            o = pjp.proceed();
        }
        catch (Exception localException) {}
        try
        {
            if (xb.getClass().getMethod(pjp.getSignature().getName(), new Class[0]).isAnnotationPresent(Cache.class))
            {
                Cache c = (Cache)xb.getClass().getMethod(pjp.getSignature().getName(), new Class[0]).getAnnotation(Cache.class);
                String type = c.type();
                boolean load = c.load();
                ServiceUtil.invokeInterface("", "sayHello", new Object[] { "aa", Integer.valueOf(123) }, DataType.JSON);
            }
        }
        catch (Exception localException1) {}
        return o;
    }

    private static Object getProxyTargetObject(Object proxy)
            throws Exception
    {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy)h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
    }
}
