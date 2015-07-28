package com.lanjie.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/** 功能：工具类，将信息提交到服务器端并接收返回信息 版本 */
public class HttpUtils {

	private static HttpPost post; // 发送请求以及数据
	private static HttpGet get; // 发送请求以及数据
	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient client;

	public static synchronized HttpClient getHttpClient() {
		
		//单例加httpProtocalParams 完美解决http多线程冲突问题
		if (null == client) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) " + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			//HttpProtocolParams.setUserAgent(params, "android-async-http/1.4.5");
			
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 5000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 10000);
			
			//设置true后禁用Nagle算法,Nagle算法试图通过减少分片的数量来节省带宽。当应用程序希望降低网络延迟并提高性能时，它们可以关闭Nagle算法，这样数据将会更早地发送，但是增加了网络消耗
			HttpConnectionParams.setTcpNoDelay(params, false);//原来为true
			
			
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			client = new DefaultHttpClient(conMgr, params);
		}
		return client;
	}

	/**
	 * 提交数据并且得到返回的数据
	 * 
	 * @param keys
	 *            所传递的参数名称
	 * @param values
	 *            参数名所对应的 值
	 * @param path
	 *            提交的路径
	 * @return
	 */
	public static String doPost(String[] keys, String[] values, String path) {
		try {
			client = null;
			client = getHttpClient();
			post = new HttpPost(path);
			// 创建参数队列
			List<NameValuePair> list = new ArrayList<NameValuePair>();

			for (int i = 0; i < keys.length; i++) {
				list.add(new BasicNameValuePair(keys[i], values[i]));
			}

			// 创建发送对象 （发送内容 和发送编码）
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
			// 发送
			post.setEntity(entity);
			// 创建 HttpResponse 对象
			HttpResponse response = client.execute(post);
			// 得到返回的值
			HttpEntity HttEntity = response.getEntity();
			// 将返回的值转化成String类型
			String content = EntityUtils.toString(HttEntity, "UTF-8");

			return content;
		} catch (UnsupportedEncodingException e) {
			JLog.i("x", "US " + e.getMessage());
			return "-500";
		} catch (ClientProtocolException e) {
			JLog.i("x", "CP  " + e.getMessage());
			return "-500";
		} catch (IOException e) {
			JLog.i("x", "IO " + e.getMessage());
			return "-500";
		} catch (Exception e) {
			JLog.i("x", "getClient--error===" + e.getMessage());
			return "-500";
		}
	}

	/**
	 * 将inputStream转换成byte[]
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(InputStream inStream) {
		if (inStream == null) {
			return null;
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
		try {
			while ((len = inStream.read(buffer, 0, 1024)) != -1) {
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public static String doGet(String[] keys, String[] values, String path) {
		String params = "?";
		String result = null;
		for (int i = 0; i < keys.length; i++) {
			if (i == (keys.length - 1)) {
				params += keys[i] + "=" + values[i];
			} else {
				params += keys[i] + "=" + values[i] + "&";
			}
		}
		JLog.d("---request---", path + params);
		try {
			client = getHttpClient();
			get = new HttpGet(path + params);
			HttpResponse response = client.execute(get);
			HttpEntity HttEntity = response.getEntity(); // 得到返回的值
			result = EntityUtils.toString(HttEntity, "UTF-8"); // 将返回的值转化成String类型
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static InputStream getStreamFromURL(String imageURL) {
		InputStream in = null;
		try {
			in = new URL(imageURL).openStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	
	public static InputStream getImageStream(String path) throws Exception{     
	        URL url = new URL(path);     
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();     
	        conn.setConnectTimeout(5 * 1000);     
	        conn.setRequestMethod("GET");  
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){     
	            return conn.getInputStream();        
	        }     
	        return null;   
	    }
}
