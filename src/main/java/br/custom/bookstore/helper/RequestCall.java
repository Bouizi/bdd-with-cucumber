package br.custom.bookstore.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import br.custom.bookstore.Correio;

public class RequestCall {
	
	public static String get(String url) throws ClientProtocolException, IOException {
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		StringBuilder xml = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			xml.append(line);
		}
		return xml.toString();
	}
	
	public static String postSoap(String url, String strSoapAction, String xmlBody) throws ClientProtocolException, IOException {
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpPost request = new HttpPost(url);
		request.addHeader("SOAPAction", strSoapAction);
		StringEntity entity = new StringEntity(xmlBody, "text/xml", HTTP.DEFAULT_CONTENT_CHARSET);
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		response.setHeader("Content-Type", "text/xml; charset=UTF-8");
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		StringBuilder xml = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			xml.append(line);
		}
		return xml.toString();
	}

}
