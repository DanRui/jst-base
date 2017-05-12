package com.jst.common.utils;

import java.lang.reflect.Type;



public class TypeConvertUtil {

	public static Object convert(String[] src, Type o) {

		if (src.length == 1) { // 单个值
			String typeName = "" + o;
			String[] typeNameSeperatedArray = typeName.split(" ");// 此外为啥这样，现在也想不起来了，郁闷吧!
			String temp = null;
			if (typeNameSeperatedArray.length > 1) { // 是基本类型，还是对象类弄型，大于是为非基本类型
				temp = typeNameSeperatedArray[1]
						.substring(typeNameSeperatedArray[1].lastIndexOf(".") + 1); // /此外为啥这样，现在也想不起来了，郁闷吧!
			} else if (typeNameSeperatedArray.length == 1) { // 基本类型
				temp = typeNameSeperatedArray[0];
			}
			if (temp.equalsIgnoreCase("boolean")) {
				return Boolean.valueOf(src[0]);
			} else if (temp.equalsIgnoreCase("char")) {
				return src[0].charAt(0);

			} else if (temp.equalsIgnoreCase("int")) {
				return Integer.parseInt(src[0]);

			} else if (temp.equalsIgnoreCase("short")) {
				return Short.valueOf(src[0]);

			} else if (temp.equalsIgnoreCase("long")) {
				return Long.valueOf(src[0]);

			} else if (temp.equalsIgnoreCase("float")) {
				return Float.valueOf(src[0]);
			} else if (temp.equalsIgnoreCase("byte")) {
				return Byte.valueOf(src[0]);
			} else if (temp.equalsIgnoreCase("double")) {
				return Double.valueOf(src[0]);
			} else if (temp.equalsIgnoreCase("Date")) {
				return DateUtil.getDate(src[0]);
			} else {
				return src[0];
			}
		} else { // 多个值 暂不支持我个值
			return null;

		}
	}
}
