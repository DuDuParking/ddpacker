package org.apache.cordova.dudu.gexin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

public class GexinSdkMsgReceiver extends BroadcastReceiver {
    
    public static String pushid = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.i("GexinSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {

		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			byte[] payload = bundle.getByteArray("payload");

			if (payload != null) {
				String data = new String(payload);
				Log.i("GexinSdkDemo", "Got Payload:" + data);
				if(Device.device != null){
          Device.device.onMsgData(data);
        }
			}
			break;
		case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                pushid = bundle.getString("clientid");
                if(Device.device != null){
                	Device.device.onPushID(pushid);
                }
			break;
		default:
			break;
		}
	}
}
