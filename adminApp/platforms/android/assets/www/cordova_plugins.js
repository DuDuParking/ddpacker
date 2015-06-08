cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/org.apache.cordova.dudu.gexin/www/device.js",
        "id": "org.apache.cordova.dudu.gexin.device",
        "clobbers": [
            "device"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.inappbrowser/www/inappbrowser.js",
        "id": "org.apache.cordova.inappbrowser.inappbrowser",
        "clobbers": [
            "window.open"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.network-information/www/network.js",
        "id": "org.apache.cordova.network-information.network",
        "clobbers": [
            "navigator.connection",
            "navigator.network.connection"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.network-information/www/Connection.js",
        "id": "org.apache.cordova.network-information.Connection",
        "clobbers": [
            "Connection"
        ]
    },
    {
        "file": "plugins/com.phonegap.plugins.flurry/www/flurry.js",
        "id": "com.phonegap.plugins.flurry.Flurry",
        "clobbers": [
            "Flurry"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.dudu.gexin": "0.2.14-dev",
    "org.apache.cordova.inappbrowser": "0.5.4",
    "org.apache.cordova.network-information": "0.2.14",
    "org.apache.cordova.console": "0.2.14-dev",
    "com.phonegap.plugins.flurry": "1.0.0"
}
// BOTTOM OF METADATA
});