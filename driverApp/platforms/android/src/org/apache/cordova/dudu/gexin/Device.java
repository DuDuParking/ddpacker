/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.dudu.gexin;

//import java.util.TimeZone;
import android.util.Log;

import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings;

//import com.igexin.slavesdk.MessageManager;
import com.igexin.sdk.PushManager;

public class Device extends CordovaPlugin {
    public static final String TAG = "Device";

//    public static String platform;                            // Device OS
//    public static String uuid;                                // Device UUID

//    private static final String ANDROID_PLATFORM = "Android";
//    private static final String AMAZON_PLATFORM = "amazon-fireos";
//    private static final String AMAZON_DEVICE = "Amazon";
    
    public static Device device = null;
    
    private CallbackContext callback = null;

    /**
     * Constructor.
     */
    public Device() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
//        Device.uuid = getUuid();
        
        device = this;
    }
    
    public void onPushID(String pushid){
    	Log.i(TAG, "onPushID");
        if(callback != null){
            try {
            JSONObject r = new JSONObject();
            r.put("evt", "pushid");
            r.put("data", pushid);
            PluginResult result = new PluginResult(PluginResult.Status.OK, r);
            result.setKeepCallback(true);
            callback.sendPluginResult(result);
            } catch (JSONException e) {
                Log.e(TAG, e.toString());
            }
        }
    }
    
    public void onMsgData(String data){
    	Log.i(TAG, "onMsgData");
        if(callback != null){
        try {
            JSONObject r = new JSONObject();
            r.put("evt", "msg");
            r.put("data", data);
            PluginResult result = new PluginResult(PluginResult.Status.OK, r);
            result.setKeepCallback(true);
            callback.sendPluginResult(result);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        }
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
//        if (action.equals("getDeviceInfo")) {
//            JSONObject r = new JSONObject();
//            r.put("uuid", Device.uuid);
//            r.put("version", this.getOSVersion());
//            r.put("platform", this.getPlatform());
//            r.put("model", this.getModel());
//            callbackContext.success(r);
//        }
				if (action.equals("getPushID")) {
                    JSONObject r = new JSONObject();
					r.put("id", GexinSdkMsgReceiver.pushid);
					callbackContext.success(r);
				}
                else if (action.equals("setCallback")) {
                	//Log.i(TAG, "setCallback");
                    callback = callbackContext;
				}
                else {
                    return false;
                }
        return true;
    }

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------

    /**
     * Get the OS name.
     * 
     * @return
     */
//    public String getPlatform() {
//        String platform;
//        if (isAmazonDevice()) {
//            platform = AMAZON_PLATFORM;
//        } else {
//            platform = ANDROID_PLATFORM;
//        }
//        return platform;
//    }

    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
//    public String getUuid() {
//        String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        return uuid;
//    }
//
//    public String getModel() {
//        String model = android.os.Build.MODEL;
//        return model;
//    }
//
//    public String getProductName() {
//        String productname = android.os.Build.PRODUCT;
//        return productname;
//    }

    /**
     * Get the OS version.
     *
     * @return
     */
//    public String getOSVersion() {
//        String osversion = android.os.Build.VERSION.RELEASE;
//        return osversion;
//    }
//
//    public String getSDKVersion() {
//        @SuppressWarnings("deprecation")
//        String sdkversion = android.os.Build.VERSION.SDK;
//        return sdkversion;
//    }
//
//    public String getTimeZoneID() {
//        TimeZone tz = TimeZone.getDefault();
//        return (tz.getID());
//    }

    /**
     * Function to check if the device is manufactured by Amazon
     * 
     * @return
     */
//    public boolean isAmazonDevice() {
//        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
//            return true;
//        }
//        return false;
//    }

}
