package com.lanjie.lib.utils;

import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * 解析XML工具类
 */
public class XmlUtils {

	//采用XmlPullParser来解析XML文件    
    public static List<Object> getStudents(InputStream inStream) throws Throwable   
    {    
        List<Object> students = null;    
        Object mStudent = null;    
          
        //========创建XmlPullParser,有两种方式=======  
        //方式一:使用工厂类XmlPullParserFactory  
        /*XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();  
        XmlPullParser parser = pullFactory.newPullParser(); */ 
        //方式二:使用Android提供的实用工具类android.util.Xml  
        XmlPullParser parser = Xml.newPullParser();    
          
        //解析文件输入流    
        parser.setInput(inStream, "UTF-8");    
        //产生第一个事件    
        int eventType = parser.getEventType();    
        //只要不是文档结束事件，就一直循环    
        while(eventType!=XmlPullParser.END_DOCUMENT)    
        {    
            switch (eventType)     
            {    
                //触发开始文档事件    
                case XmlPullParser.START_DOCUMENT:    
                    students = new ArrayList<Object>();    
                    break;    
                //触发开始元素事件    
                case XmlPullParser.START_TAG:    
                    //获取解析器当前指向的元素的名称    
                    String name = parser.getName();    
                    if("student".equals(name))    
                    {    
                        //通过解析器获取id的元素值，并设置student的id    
                        mStudent = new Object();    
                        //mStudent.setId(parser.getAttributeValue(0));    
                    }    
                    if(mStudent!=null)    
                    {    
                        if("name".equals(name))    
                        {    
                            //获取解析器当前指向元素的下一个文本节点的值    
                           // mStudent.setName(parser.nextText());    
                        }    
                        if("age".equals(name))    
                        {    
                            //获取解析器当前指向元素的下一个文本节点的值    
                           // mStudent.setAge(new Short(parser.nextText()));    
                        }  
                        if("sex".equals(name))  
                        {  
                            //获取解析器当前指向元素的下一个文本节点的值    
                           // mStudent.setSex(parser.nextText());  
                        }  
                    }    
                    break;    
                //触发结束元素事件    
                case XmlPullParser.END_TAG:    
                    //    
                    if("student".equals(parser.getName()))    
                    {    
                        students.add(mStudent);    
                        mStudent = null;    
                    }    
                    break;    
                default:    
                    break;    
            }    
            eventType = parser.next();    
        }    
        return students;    
    }    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 解析XML转换成对象
	 * 
	 * @param is 输入流
	 * @param clazz 对象Class
	 * @param fields 字段集合一一对应节点集合
	 * @param elements 节点集合一一对应字段集合
	 * @param itemElement 每一项的节点标签
	 * @return
	 */
	public static List<Object> parse(InputStream is, Class<?> clazz,
			List<String> fields, List<String> elements, String itemElement) {
		List<Object> list = new ArrayList<Object>();
		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(is, "UTF-8");
			int event = xmlPullParser.getEventType();

			Object obj = null;

			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					if (itemElement.equals(xmlPullParser.getName())) {
						obj = clazz.newInstance();
					}
					if (obj != null
							&& elements.contains(xmlPullParser.getName())) {
						setFieldValue(obj, fields.get(elements
								.indexOf(xmlPullParser.getName())),
								xmlPullParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (itemElement.equals(xmlPullParser.getName())) {
						list.add(obj);
						obj = null;
					}
					break;
				}
				event = xmlPullParser.next();
			}
		} catch (Exception e) {
			throw new RuntimeException("解析XML异常：" + e.getMessage());
		}
		return list;
	}

	/**
	 * 设置字段值
	 * 
	 * @param propertyName 字段名
	 * @param obj 实例对象
	 * @param value 新的字段值
	 * @return
	 */
	public static void setFieldValue(Object obj, String propertyName,
			Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

}