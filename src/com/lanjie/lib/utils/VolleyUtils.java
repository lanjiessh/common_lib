package com.lanjie.lib.utils;

import java.util.Map;

import org.json.JSONObject;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyUtils {
	public static final String TAG = "VolleyPatterns";

	private static RequestQueue mRequestQueue;

	public static RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(App.getInstance());
		}

		return mRequestQueue;
	}

	public static void addToRequestQueue(String tag, Request req) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	public static void addToRequestQueue(Request req) {
		// set the default tag if tag is empty
		req.setTag(TAG);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	/**
	 *根据tag取消request
	 * 
	 */
	public static void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
	/**
	 *取消所有request
	 * 
	 */
	public static void cancelAllRequests() {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(App.getInstance());
		}
	}

	/**
	 *get方式StringRequest
	 * 
	 */
	public static void getStringRequest(String tag, String url,
			Response.Listener<String> onResponse,
			Response.ErrorListener onErrorResponse) {
		StringRequest req = new StringRequest(Method.GET, url, onResponse,
				onErrorResponse);
		// add the request object to the queue to be executed
		addToRequestQueue(tag, req);
	}

	/**
	 *post方式StringRequest
	 * 
	 */
	public static void postStringRequest(String tag, String url,
			final Map<String, String> paramMap,
			Response.Listener<String> onResponse,
			Response.ErrorListener onErrorResponse) {
		StringRequest req = new StringRequest(Method.POST, url, onResponse,
				onErrorResponse) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return paramMap;
			}
		};

		// add the request object to the queue to be executed
		addToRequestQueue(tag, req);
	}

	
	/**
	 *get方式JsonObjectRequest
	 * 
	 */
	public static void getJsonObjectRequest(String tag, String url,
			Response.Listener<JSONObject> onResponse,
			Response.ErrorListener onErrorResponse) {
		JsonObjectRequest req = new JsonObjectRequest(url, null, onResponse,
				onErrorResponse);
		// add the request object to the queue to be executed
		addToRequestQueue(tag, req);
	}
	

	/**
	 *post方式JsonObjectRequest
	 * 
	 */
	public static void postJsonObjectRequest(String tag, String url,
			Map<String, String> paramMap,
			Response.Listener<JSONObject> onResponse,
			Response.ErrorListener onErrorResponse) {
		JSONObject params = new JSONObject(paramMap);
		JsonObjectRequest req = new JsonObjectRequest(url, params, onResponse,
				onErrorResponse);
		// add the request object to the queue to be executed
		addToRequestQueue(tag, req);
	}
	
	
	/**
	 *post方式JsonObjectRequest
	 * 
	 */
	public static void postXMLRequest(String tag, String url,
			Map<String, String> paramMap,
			Response.Listener<JSONObject> onResponse,
			Response.ErrorListener onErrorResponse) {
		
		JSONObject params = new JSONObject(paramMap);
		JsonObjectRequest req = new JsonObjectRequest(url, params, onResponse,
				onErrorResponse);
		// add the request object to the queue to be executed
		addToRequestQueue(tag, req);
	}

}
