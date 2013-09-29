package com.example.parseJSON;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.log.ISLog;

import android.util.Log;

public class ParseJSON {
	private static String JSONURL = "http://10.0.2.2:4567/images";
	private static String serverJSON; // JSON file parsed from server

	public void parseServerJSON() {

		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(JSONURL);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				setServerJSON(EntityUtils.toString(entity));
				Log.i(ISLog.LOG_TAG, "JSON: " + getServerJSON());
			} else {
				Log.e(ParseJSON.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getServerJSON() {
		return serverJSON;
	}

	public static void setServerJSON(String serverJSON) {
		ParseJSON.serverJSON = serverJSON;
	}
}