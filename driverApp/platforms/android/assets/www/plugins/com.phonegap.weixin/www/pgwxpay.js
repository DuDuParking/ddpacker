cordova.define("com.phonegap.weixin.Pgwxpay", function(require, exports, module) { 
var exec = require("cordova/exec");

function Pgwxpay() {
	console.log('module.exports = new Pgwxpay();');
}

Pgwxpay.prototype.iswx = function(successCallback, errorCallback) {
    if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
        console.log("pgbaidumap.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("pgbaidumap.scan failure: success callback parameter must be a function");
        return
    }

    exec(successCallback, errorCallback, 'Pgwxpay', 'iswx', []);
};
Pgwxpay.prototype.wxpay2 = function(paydata, successCallback, errorCallback) {
    if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
        console.log("pgbaidumap.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("pgbaidumap.scan failure: success callback parameter must be a function");
        return
    }

    exec(successCallback, errorCallback, 'Pgwxpay', 'wxpay2', [paydata]);
};
Pgwxpay.prototype.wxpay = function(out_trade_no,url,bodtxt,total_fee,successCallback, errorCallback) {
    if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
        console.log("pgbaidumap.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("pgbaidumap.scan failure: success callback parameter must be a function");
        return
    }

    exec(successCallback, errorCallback, 'Pgwxpay', 'wxpay', [{"out_trade_no":out_trade_no, "url": url,"bodtxt":bodtxt, "total_fee": total_fee.toString()}]);
};
Pgwxpay.prototype.getcode = function(successCallback, errorCallback) {
    if (errorCallback == null) { errorCallback = function() {}}

    if (typeof errorCallback != "function")  {
        console.log("pgbaidumap.scan failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("pgbaidumap.scan failure: success callback parameter must be a function");
        return
    }

    exec(successCallback, errorCallback, 'Pgwxpay', 'getcode', []);
};

module.exports = new Pgwxpay();

});
