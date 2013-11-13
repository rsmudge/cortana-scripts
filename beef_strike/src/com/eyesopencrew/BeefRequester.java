/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Administrateur
 */
public class BeefRequester {
    private static String hooks;
    private static String details;

    private static String beefConnect(String url, String login, String pass){
    String key = null;
    return key;}

    /**
     *
     * @param beefUrl
     * @return
     */
    public static String BeefGetRequest(String beefUrl) {

            try {

		     URL url = new URL(beefUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
                        //String t = br.readLine();
			while ((output = br.readLine()) != null) {

				//System.out.println(output);
                                BeefRequester.setHooks(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
          return hooks;
    }

    public static String BeefPostRequest(String beefUrl){
     String result = null; // Not implemented in fact, Curl is good.
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
        BeefRequester.hooks = hooks;
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
        BeefRequester.details = details;
    }

   }
