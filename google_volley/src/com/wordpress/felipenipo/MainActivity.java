package com.wordpress.felipenipo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wordpress.felipenipo.entities.Customer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	// Constants
	public static final String TAG = "MainActivity";
	private static final String SERVER_URL = "http://protobackend.herokuapp.com";
	private static final String CREATE_CUSTOMER_PATH = "/customers";
	private static final String GET_ALL_CUSTOMERS_PATH = "/customers";
	private static final String GET_CUSTOMER_PATH = "/customers/";
	private static final String FAKE_JSON = 
			  "	{\"customer\":" +
			  "	{\"name\":\"John Doe\"," +
			  "	\"email\":\"john.doe@example.com\"," +
			  "	\"password\":\"980293jfn98hinf298h3\"," +
			  "	\"device_model\":\"StarTAC\"," +
			  " \"gcm_id\":\"09jcm892j3p89hc2983h9982\"}}";
	
	// Members
	private NetworkQueue mNetworkQueue;
	private EditText mIdEditText;
	private Button mDoItButton;
	private Button mGetAllButton;
	private Button mGetItButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNetworkQueue = NetworkQueue.getInstance();
        
        Customer customer = null;
        JSONObject tryJsonObject = null;
        try {
        	tryJsonObject = new JSONObject(FAKE_JSON);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        final JSONObject jsonObject = tryJsonObject;
        
        mIdEditText = (EditText) findViewById(R.id.id_et);
        
        mGetAllButton = (Button) findViewById(R.id.get_all_btn);
        mGetAllButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doArrayRequest(
		        	SERVER_URL + GET_ALL_CUSTOMERS_PATH,
		        	TAG,
		        	new NetworkRequestCallback() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "GET ALL onRequestResponse!" + "\n" + jsonObject.toString());
						}
		        		@Override
						public void onArrayRequestResponse(JSONArray jsonArray) {
		        			Log.d(TAG, "GET ALL onArrayRequestResponse!" + "\n" + jsonArray.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "GET ALL onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
        
        mDoItButton = (Button) findViewById(R.id.do_it_btn);
        mDoItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doPost(
		        	SERVER_URL + CREATE_CUSTOMER_PATH,
		        	jsonObject /*JSONParser.toJSON(customer)*/,
		        	TAG,
		        	new NetworkRequestCallback() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "POST onRequestResponse!" + "\n" + jsonObject.toString());
						}
		        		@Override
						public void onArrayRequestResponse(JSONArray jsonArray) {
		        			Log.d(TAG, "POST onArrayRequestResponse!" + "\n" + jsonArray.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "POST onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
        
        mGetItButton = (Button) findViewById(R.id.get_it_btn);
        mGetItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doGet(
		        	SERVER_URL + GET_CUSTOMER_PATH + mIdEditText.getText().toString(),
		        	TAG,
		        	new NetworkRequestCallback() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "GET onRequestResponse!" + "\n" + jsonObject.toString());
						}
		        		@Override
						public void onArrayRequestResponse(JSONArray jsonArray) {
		        			Log.d(TAG, "GET onArrayRequestResponse!" + "\n" + jsonArray.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "GET onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mNetworkQueue.cancelRequestsByTag(TAG);
    }
}
