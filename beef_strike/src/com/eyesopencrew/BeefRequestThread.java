package com.eyesopencrew;

/**
 * @author Beny Green - gacksecurity.blogspot.com
 *
 * EyesOpenCrew
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BeefRequestThread extends Thread {
    private static String beefserverurl;
    private static String details;
    private static String hooks;

    public BeefRequestThread(String beefUrl) {
        BeefRequestThread.setBeefserverurl(beefUrl);
    }

    /**
     *
     * @param beefUrl
     * @return
     */

    @Override
    public void run() {

            try {
 
                        URL url = new URL(BeefRequestThread.getBeefserverurl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed, Maybe the RestfulAPI key is wrong ! : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
                        //String t = br.readLine();
			while ((output = br.readLine()) != null) {

                                BeefRequestThread.setHooks(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
         //this.setHooks(hooks);
    }

    public static String BeefPostRequest(String beefUrl){
     String result = null;
     return result ;}

    /**
     * 
     * @return
     */
    public static String getHooks() {
        return hooks;
    }

    /**
     *
     * @param hooks
     */
    public static void setHooks(String hooks) {
        BeefRequestThread.hooks = hooks;
    }

    /**
     *
     * @return
     */
    public static String getDetails() {
        return details;
    }

    /**
     *
     * @param details
     */
    public static void setDetails(String details) {
        BeefRequestThread.details = details;
    }

    public static String getBeefserverurl() {
        return beefserverurl;
    }

    public static void setBeefserverurl(String beefserverurl) {
        BeefRequestThread.beefserverurl = beefserverurl;
    }

}
