package com.lanjie.lib.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonUtils {
	// 第一部分 javabean转JSON 或者jsonstr

	/**
	 * 将一个 Object 或者List对象转化为JSONObject或者JSONArray
	 * 
	 * @param ObjOrList
	 *            Object 或者List对象
	 * @return
	 */
	public static Object toJSON(Object ObjOrList) {
		Object obj = null;

		try {
			obj = JSON.toJSON(ObjOrList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
	 * 
	 * @param ObjOrList
	 *            Object 或者List对象 或者hashmap 但是如果是set 就会有问题
	 * @return
	 */
	public static String toJSONStr(Object ObjOrList) {
		String jsonstr = "";

		try {
			jsonstr = JSON.toJSONString(ObjOrList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonstr;
	}

	// 第二部分字符串转 obj list

	/**
	 * 字符串转obj
	 * 
	 * @param jsonstr
	 * @param clazz
	 * @return
	 */
	public static Object parseToObject(String jsonstr, Class<?> clazz) {
		Object parseObj = null;
		try {
			parseObj = JSON.parseObject(jsonstr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	/**
	 * 字符串转list
	 * 
	 * @param jsonstr
	 * @param clazz
	 * @return
	 */
	public static List<?> parseToList(String jsonstr, Class<?> clazz) {
		List<?> parseObj = null;
		try {
			parseObj = JSON.parseArray(jsonstr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	// 第三部分 字符串转JSONObj 或者JSONArray

	/**
	 * 字符串转jsonobj
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static JSONObject parseToJSONObejct(String jsonstr) {
		JSONObject parseObj = null;
		try {
			parseObj = JSON.parseObject(jsonstr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	/**
	 * 字符串转list
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static JSONArray parseToJSONArray(String jsonstr) {
		JSONArray parseObj = null;
		try {
			parseObj = JSON.parseArray(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	// 第四部分 com.alibaba包下 JSONObj 或者JSONArr 转 javabean或者 java array
	/**
	 *
	 * @param jsonObj
	 * @param obj
	 * @return
	 */
	public static Object parseToObject(JSONObject jsonObj, Class<?> obj) {
		Object parseObj = null;
		try {
			parseObj = JSON.parseObject(jsonObj + "", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	/**
	 *
	 * @param jsonArr
	 * @param obj
	 * @return
	 */
	public static List<?> parseToList(JSONArray jsonArr, Class<?> obj) {
		List list = null;

		try {
			list = JSON.parseArray(jsonArr + "", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 第五部分 将android系统下的JSONObj 或者JSONArr 转 javabean或者 javaarr
	// 第五部分看似没用不过想想特么的 用的人偶尔还是会用到系统的JSON对象 所以决定加下面这两个方法
	/**
	 *
	 * @param jsonObj
	 *            android系统下的JSONObj
	 * @param obj
	 * @return
	 */
	public static Object parseToObject(org.json.JSONObject jsonObj, Class<?> obj) {
		Object parseObj = null;
		try {
			parseObj = JSON.parseObject(jsonObj.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseObj;
	}

	/**
	 * @param jsonArr
	 *            android系统下的JSONArr
	 * @param obj
	 * @return
	 */
	public static List<?> parseToList(org.json.JSONArray jsonArr, Class<?> obj) {
		List list = null;

		try {
			list = JSON.parseArray(jsonArr.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
