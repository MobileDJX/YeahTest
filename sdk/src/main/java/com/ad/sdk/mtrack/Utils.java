package com.ad.sdk.mtrack;

import android.annotation.SuppressLint;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@SuppressLint("DefaultLocale")
public class Utils {

    public static String convertToString(int a) {

        int c;
        char m;
        StringBuilder ans = new StringBuilder();
        // convert the String to int
        while (a > 0) {
            c = a % 10;
            a = a / 10;
            m = (char) ('0' + c);
            ans.append(m);
        }
        return ans.reverse().toString();
    }


    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

}