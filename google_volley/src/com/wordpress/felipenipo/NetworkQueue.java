package com.wordpress.felipenipo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class NetworkQueue {

	private static NetworkQueue mInstance;
	private Context mApplicationContext;
	private RequestQueue mRequestQueue;
	
	// Prevent instantiation
	private NetworkQueue() {
		super();
	}
	
	public static NetworkQueue getInstance() {
		if(mInstance == null) {
			mInstance = new NetworkQueue();
		}
		return mInstance;
	}
	
	public void init(Application application) {
		if(mApplicationContext == null) {
			mApplicationContext = application.getApplicationContext();
			mRequestQueue = Volley.newRequestQueue(mApplicationContext);
		}
	}
	
	public void cancelRequestsByTag(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
	
	public void cancelAllRequests() {
		mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
	}
	
	public void doGet(String url, Object tag,
		NetworkRequestCallback networkRequestCallback) {
		doRequest(buildJSONRequest(Request.Method.GET, url, null, tag, networkRequestCallback));
	}
	
	public void doArrayRequest(String url, Object tag,
		NetworkRequestCallback networkRequestCallback) {
		// The method is supposed to be GET
		doRequest(buildJSONArrayRequest(url, tag, networkRequestCallback));
	}
	
	public void doPost(String url, JSONObject jsonObject, Object tag,
		NetworkRequestCallback networkRequestCallback) {
		doRequest(buildJSONRequest(Request.Method.POST, url, jsonObject, tag, networkRequestCallback));
	}

	public void doPut(String url, JSONObject jsonObject, Object tag,
		NetworkRequestCallback networkRequestCallback) {
		doRequest(buildJSONRequest(Request.Method.PUT, url, jsonObject, tag, networkRequestCallback));
	}

	public void doDelete(String url, Object tag,
		NetworkRequestCallback networkRequestCallback) {
		doRequest(buildJSONRequest(Request.Method.DELETE, url, null, tag, networkRequestCallback));
	}
	
	/*
	 * PRIVATE METHODS
	 */
	private JsonObjectRequest buildJSONRequest(int method, String url, JSONObject jsonObject, Object tag,
		final NetworkRequestCallback networkRequestCallback) {
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				networkRequestCallback.onRequestResponse(response);
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				networkRequestCallback.onRequestError(error);
			}
		};
		
		JsonObjectRequest request = new JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders();
			}
		};
		request.setTag(tag);
		return request;
	}
	
	private JsonArrayRequest buildJSONArrayRequest(String url, Object tag,
		final NetworkRequestCallback networkRequestCallback) {
		Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				networkRequestCallback.onArrayRequestResponse(response);
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				networkRequestCallback.onRequestError(error);
			}
		};
		
		// The method is supposed to be GET
		JsonArrayRequest request = new JsonArrayRequest(url, listener, errorListener) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders();
			}
		};
		request.setTag(tag);
		return request;
	}
	
	private Map<String, String> buildHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		return headers;
	}
	
	private <T> void doRequest(Request<T> request) {
		mRequestQueue.add(request);
	}
}
