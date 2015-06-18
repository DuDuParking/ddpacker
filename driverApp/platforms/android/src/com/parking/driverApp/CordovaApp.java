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

package com.parking.driverApp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import org.apache.cordova.*;

import com.flurry.android.FlurryAgent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class CordovaApp extends CordovaActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();

        if (BuildConfig.DEBUG) {
            appView.clearCache(true);
            launchUrl = launchUrl.replace("index", "t.index");
        }

        loadUrl(launchUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FlurryAgent.setCaptureUncaughtExceptions(true);
        FlurryAgent.onStartSession(this, "WG6F24XTXTVTP2J4ZDFN");

        // Umeng Analytics
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.onResume(this);

        // Umeng Update Service
        UmengUpdateAgent.update(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);

        MobclickAgent.onPause(this);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_quit_app), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
