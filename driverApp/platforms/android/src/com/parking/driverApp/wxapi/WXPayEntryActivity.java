package com.parking.driverApp.wxapi;
 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.phonegap.weixin.Keyswx;
import com.phonegap.weixin.Pgwxpay;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.cordova.CordovaWebView;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "com.parking.driverApp.wxapi.WXPayEntryActivity";
	
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
			
		 if(Pgwxpay.wxpay != null)
		 {
		 	Pgwxpay.wxpay.onResult(resp.errCode);
		 }
			
			this.finish();
			
 
			 
			
 
		}
	}
}