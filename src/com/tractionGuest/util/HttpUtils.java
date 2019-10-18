package com.tractionGuest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {

	private static HttpUtils httputils;

	public static HttpUtils getInstance() {
		if (httputils == null) {
			httputils = new HttpUtils();
		}
		return httputils;
	}

	public static String sendGETRequest(String endPoint) {
		try {
			URL url = new URL(endPoint);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("Content-Type", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			System.out.println(con.getResponseCode());
			if (con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// System.out.println(response.toString());
				in.close();

				// print result
				return response.toString();
			} else {
				System.out.println("error caught ----");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static String sendPOSTRequest(String endPoint, String params,
			String... clientAcceessToken) {
		StringBuilder result = new StringBuilder();
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(endPoint);

		post.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = null;

		try {
			if (clientAcceessToken.length > 0)
				/*
				 * post.setHeader("Access-Token", Action_Wrapper
				 * .returnPropertyValue(clientAcceessToken[0]))
				 */;

			post.setEntity(new StringEntity(params));
			httpResponse = client.execute(post);

			if (httpResponse != null) {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent()));

				String line = "";
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				return (result.toString());
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;

	}
}