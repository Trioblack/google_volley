package com.wordpress.felipenipo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONParser {

	private static Gson mGson;
	
	// Static constructor
	static {
		mGson = new Gson();
	}
	
	public static JSONObject toJSON(Object obj) {
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(mGson.toJson(obj));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public static JSONArray toJSONArray(Object obj) {
		JSONArray jsonArray = null;
		
		try {
			jsonArray = new JSONArray(mGson.toJson(obj));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public static <T> T toObject(JSONObject jsonObject, Class<T> classOfT) {
		return mGson.fromJson(jsonObject.toString(), classOfT);
	}
	
	public static <T> T toObjectArray(JSONArray jsonArray, Class<T> classOfT) {
		return mGson.fromJson(jsonArray.toString(), classOfT);
	}
}
