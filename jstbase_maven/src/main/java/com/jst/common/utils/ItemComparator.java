package com.jst.common.utils;



import java.util.Comparator;


/**
 *
 * <p>Title: ItemComparator</p>
 * <p>Description: ItemComparator,����Hashtable��������</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: �����н�ͨ��</p>
 * @author Ripple
 * @version 1.0
 */
public final class ItemComparator implements Comparator {

    public int compare(Object arg0, Object arg1) {
        ComObject obj1 = (ComObject) arg0;
        ComObject obj2 = (ComObject) arg1;
       int i=0;
       try{
         i = obj1.getSortId().intValue()-obj2.getSortId().intValue();
       }catch(Exception e){

       }

       return i;
    }
}
