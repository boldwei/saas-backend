package com.strongunion.wireless.controller.ticket;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class VoteThread extends Thread{
	private BlockingQueue<IpInfo> ipInfoQueue;

	public VoteThread(BlockingQueue<IpInfo> ipInfoQueue) {
		this.ipInfoQueue = ipInfoQueue;
	}
	@Override
	public void run(){
		HttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 15000);
		HttpResponse response =null;
		HttpGet get = null;
		HttpEntity entity =null;
		HttpHost proxy = null;
		while(true){
			IpInfo ipInfo = null;
			try {
				ipInfo = ipInfoQueue.take();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			proxy = new HttpHost(ipInfo.getIpAddress(),ipInfo.getPort());
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			get = new HttpGet("http://event.21cn.com/api/v1/match/vote.do?jsoncallback=jQuery17206383393171709031_1463389391221&id=1088&t1=1463389423973.547&_=1463389419591");
			get.addHeader("Host", "event.21cn.com");
            get.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER");
            try {
				response = client.execute(get);
				entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	            // 对响应内容编码格式进行转化，统一成utf-8格式
	            String temp = new String(bytes, "gbk");
	            byte[] contentData = temp.getBytes("utf-8");
	            System.out.println(new String(contentData));
	            System.out.println("-----------------------------------");
			} catch (ClientProtocolException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}