/*
 * LaunchNavigator Plugin for Phonegap
 *
 * Copyright (c) 2014 Dave Alden  (http://github.com/dpa99c)
 * Copyright (c) 2014 Working Edge Ltd. (http://www.workingedge.co.uk)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package uk.co.workingedge.phonegap.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


public class LaunchNavigator extends CordovaPlugin {

	private static final String LOG_TAG = "LaunchNavigator";

	@Override
	public boolean execute(String action, JSONArray args,
		CallbackContext callbackContext) throws JSONException {
		boolean result;

		Log.d(LOG_TAG, action);

		if ("navigate".equals(action)){
			result = this.navigate(args, callbackContext);
		}else {
			Log.e(LOG_TAG, "Invalid action");
			result = false;
		}
		
		if(result == true){
			callbackContext.success();
		}
		return result;
	}

	private boolean navigate(JSONArray args, CallbackContext callbackContext){
		boolean result;
		try {
			String slat = "", slon = "", dlat = "", dlon = "";

			String type = args.getString(0);

			JSONArray pos = args.optJSONArray(1);
			JSONArray pos2 = args.optJSONArray(2);

			if (null != pos && null != pos2) {
				dlat = pos.getString(0);
				dlon = pos.getString(1);

				slat = pos2.getString(0);
				slon = pos2.getString(1);

				result = this.doNavigate(type, slat, slon, dlat, dlon, callbackContext);
			} else if (null != pos) {
				dlat = pos.getString(0);
				dlon = pos.getString(1);

				result = this.doViewMap(type, dlat, dlon, callbackContext);
			} else {
				result = false;
			}

		}catch( JSONException e ) {
			Log.e(LOG_TAG, "Exception occurred: ".concat(e.getMessage()));
        	result = false;
		}
        return result;
    }


	private boolean doViewMap(String type, String dlat, String dlon, CallbackContext callbackContext){
		boolean result;
		try {
			if (type.equals("baidu")) {
				String url = String.format("bdapp://map/marker?location=%s,%s&title=%s&content=%s&coord_type=gcj02&src=duduche|parking"
						, dlat, dlon, dlat + "," + dlon, dlat + "," + dlon);
				Log.d(LOG_TAG, url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent("android.intent.action.VIEW", uri);
				intent.setPackage("com.baidu.BaiduMap");
				this.cordova.getActivity().startActivity(intent);
				result = true;
			} else if (type.equals("amap")) {
				String url = String.format("androidamap://viewMap?sourceApplication=duduche&lat=%s&lon=%s&poiname=%s&dev=0"
						, dlat, dlon, dlat + "," + dlon);
				Log.d(LOG_TAG, url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent("android.intent.action.VIEW", uri);
				intent.setPackage("com.autonavi.minimap");
				this.cordova.getActivity().startActivity(intent);

				result = true;
			} else {
				String msg = "Invalid type.";
				callbackContext.error(msg);
				result = false;
			}
		}catch( Exception e ) {
			String msg = e.getMessage();
			Log.e(LOG_TAG, "Exception occurred: ".concat(msg));
			callbackContext.error(msg);
			result = false;
		}

		return result;
	}


	private boolean doNavigate(String type, String slat, String slon, String dlat, String dlon, CallbackContext callbackContext){
		boolean result;
		try {
			if (type.equals("baidu")) {
				String url = String.format("bdapp://map/direction?origin=%s,%s&destination=%s,%s&coord_type=gcj02&mode=driving&src=duduche|parking"
						, slat, slon, dlat, dlon);
				Log.d(LOG_TAG, url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent("android.intent.action.VIEW", uri);
				intent.setPackage("com.baidu.BaiduMap");
				this.cordova.getActivity().startActivity(intent);
				result = true;
			} else if (type.equals("amap")) {
				String url = String.format("androidamap://route?sourceApplication=duduche&slat=%s&slon=%s&sname=%s&dlat=%s&dlon=%s&dname=%s&dev=0&m=0&t=2"
						, slat, slon, slat + "," + slon, dlat, dlon, dlat + "," + dlon);
				Log.d(LOG_TAG, url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent("android.intent.action.VIEW", uri);
				intent.setPackage("com.autonavi.minimap");
				this.cordova.getActivity().startActivity(intent);

				result = true;
			} else {
				String msg = "Invalid type.";
				callbackContext.error(msg);
				result = false;
			}
		}catch( Exception e ) {
			String msg = e.getMessage();
			Log.e(LOG_TAG, "Exception occurred: ".concat(msg));
			callbackContext.error(msg);
        	result = false;
		}

        return result;
	}

}