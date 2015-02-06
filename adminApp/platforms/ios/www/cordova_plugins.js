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
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.dudu.gexin": "0.2.14-dev",
    "org.apache.cordova.inappbrowser": "0.5.4"
}
// BOTTOM OF METADATA
});