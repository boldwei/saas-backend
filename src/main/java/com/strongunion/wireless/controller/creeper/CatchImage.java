package com.strongunion.wireless.controller.creeper;

import java.io.BufferedInputStream;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;  
import java.net.MalformedURLException;
import java.net.URL;  
import java.net.URLConnection;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;

public class CatchImage {
	//地址
	private static final String URL= "http://user.qzone.qq.com/502716162/infocenter?ptsig=*TwUlhtDymqIN4-*6XXonLjh6p5*eupDSrCtRjQ-F-4_";
	//编码
	private static final String ECODING= "UTF-8";
	//获取img标志正则
	private static final String IMGURL_REG= "<img.*src=(.*?)[^>]*?>";
	//获取src路径的正则
	private static final String IMASRC_REG= "http:\"?(.*?)(\"|>|\\s+)";
	/*
	 * 获取url地址
	 */
	private String getHTML(String url) throws IOException{
		URL uri = new URL(url);
		URLConnection connection = uri.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		InputStream in = connection.getInputStream();
		byte[] buf = new byte[1024];
		int length = 0;
		StringBuffer sb = new StringBuffer();
		while((length =in.read(buf, 0, buf.length))>0){
			sb.append(new String(buf,ECODING));
		}
		in.close();
		return sb.toString();
	}
	/*
	 * 获取ImageUrl地址
	 */
	private List<String> getImageUrl(String HTML){
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
		List<String> listImgUrl = new ArrayList<String>();
		while(matcher.find()){
			listImgUrl.add(matcher.group());
		}
		return listImgUrl;
	} 
	/*
	 * 获取ImageSrc的地址
	 */
	private List<String> getImageSrc(List<String> listImageUrl){
		List<String> listImgSrc = new ArrayList<String>();
		for (String image : listImageUrl) {
			Matcher matcher = Pattern.compile(IMASRC_REG).matcher(image);
			while(matcher.find()){
				listImgSrc.add(matcher.group().substring(0, matcher.group().length()-1));
			}
		}
		return listImgSrc;
	}
	/*
	 * 下载图片
	 */
	private void Download(List<String> listImgSrc){
		try{
			for (String url : listImgSrc) {
				String imageName = url.substring(url.lastIndexOf("/")+1, url.length());
				URL uri = new URL(url);
				InputStream in = uri.openStream();
				FileOutputStream fo = new FileOutputStream(new File(imageName));
				byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("下载开始"+url);
				while((length = in.read(buf, 0, buf.length))!=-1){
					fo.write(buf, 0, length);
				}
				in.close();
				fo.close();
				System.out.println(imageName+"下载完成");
			}
		}catch(Exception e){
			System.out.println("下载失败");
		}
	}
	/**
	 * 下载图片
	 * @param httpUrl
	 */
	public void getHtmlPicture(List<String> listImgSrc) {

		//定义URL对象uri
		URL uri;
		//定义输入字节缓冲流对象in
		BufferedInputStream in;
		//定义文件输出流对象file
		FileOutputStream file;

		try {
			for (String url : listImgSrc) {
				//输出“取网络图片”几个破字
				System.out.println("取网络图片");
				//获取图片名
				String imageName = url.substring(url.lastIndexOf("/")).replace("/", "");
				//定义图片存储路径
				String filePath = "E:\\pictures";
				//初始化url对象
				uri = new URL(url);
				//初始化in对象，也就是获得url字节流
				in = new BufferedInputStream(uri.openStream());
				file = new FileOutputStream(new File(filePath +"\\"+ imageName));
				//byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("下载开始"+url);
				while((length = in.read())!=-1){
					file.write(length);
				}
				in.close();
				file.close();
				System.out.println(imageName+"下载完成");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		CatchImage catchImage = new CatchImage();
		String HTML = catchImage.getHTML(URL);
		List<String> imgUrl = catchImage.getImageUrl(HTML);
		List<String> imgSrc = catchImage.getImageSrc(imgUrl);
		catchImage.getHtmlPicture(imgSrc);
	}
}
