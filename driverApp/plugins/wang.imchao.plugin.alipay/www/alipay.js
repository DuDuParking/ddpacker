/*global cordova, module*/

module.exports = {
    pay: function (name, body, price, orderId, notifyUrl, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "AliPay", "pay", [name, body, price, orderId, notifyUrl]);
    }
};
