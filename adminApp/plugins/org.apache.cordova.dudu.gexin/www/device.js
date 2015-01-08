/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onCordovaInfoReady');
// Tell cordova channel to wait on the CordovaInfoReady event
channel.waitForInitialization('onCordovaInfoReady');

/**
 * This represents the mobile device, and provides properties for inspecting the model, version, UUID of the
 * phone, etc.
 * @constructor
 */
function Device() {
    this.onPushID = null;
    this.onMsgData = null;
    
    var me = this;

    channel.onCordovaReady.subscribe(function() {
        me.setCallback(function(info) {
            //ignoring info.cordova returning from native, we should use value from cordova.version defined in cordova.js
            //TODO: CB-5105 native implementations should not return info.cordova
                console.log('Received Callback: ' + info.evt + "," + info.data);
                
                if(info.evt == "pushid"){
                    if(me.onPushID != null){
                       me.onPushID(info.data);
                    }
                }
                else if(info.evt == "msg"){
                    if(me.onMsgData != null){
                       me.onMsgData(info.data);
                    }
                }
        });
        channel.onCordovaInfoReady.fire();
    });
}

/**
 * Get device info
 *
 * @param {Function} successCallback The function to call when the heading data is available
 * @param {Function} errorCallback The function to call when there is an error getting the heading data. (OPTIONAL)
 */
Device.prototype.getPushID = function(successCallback, errorCallback) {
    argscheck.checkArgs('fF', 'Device.getPushID', arguments);
    exec(successCallback, errorCallback, "Device", "getPushID", []);
};
Device.prototype.setCallback = function(successCallback, errorCallback) {
    argscheck.checkArgs('fF', 'Device.setCallback', arguments);
    exec(successCallback, errorCallback, "Device", "setCallback", []);
};

module.exports = new Device();
