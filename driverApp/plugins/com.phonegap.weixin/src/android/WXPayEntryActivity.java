package com.qsk.app.wxapi;
 

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

import com.phonegap.weixin.Keyswx;
import com.phonegap.weixin.Pgwxpay;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    public CordovaWebView webView;					// WebView object
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//       setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Keyswx.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		 


		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			
		 
			Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
			SharedPreferences mySharedPreferences= getSharedPreferences("wxcode",0); 
		    SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("code", ""+resp.errCode); 
			editor.commit(); 
			this.finish();
			
 
			 
			
 
		}
	}
}