package com.wissen.sms.receiver;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Base64;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        //Obtenemos el SMS
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++) {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }

        //Mostramos el SMS
        Toast toast = Toast.makeText(context, "Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
        toast.show();
        
       //Creamos un cliente HTTP y un GET request
        HttpClient client = new DefaultHttpClient();
        
        //Le pasamos la data
        HttpGet req = new HttpGet("http://juanlajara.com/taiper/cat/index2.php?phoneNumber="+smsMessage[0].getOriginatingAddress()+"&body="+smsMessage[0].getMessageBody());
        //req.setParams(params); Adicionar Parametros si necesario
        try {
			HttpResponse res = client.execute(req);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
