package com.wordpress.felipenipo;

import org.json.JSONArray;
import org.json.JSONObject;

public interface NetworkRequestCallback {

	public void onRequestResponse(JSONObject jsonObject);
	public void onArrayRequestResponse(JSONArray jsonArray);
	public void onRequestError(Exception error);
	
}

