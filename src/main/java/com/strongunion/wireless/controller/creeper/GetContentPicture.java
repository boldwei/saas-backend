package com.strongunion.wireless.controller.creeper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片抓取存储类GetContentPicture
 * @author BD
 *
 */
public class GetContentPicture {

	/**
	 * 下载图片
	 * @param httpUrl
	 */
	public void getHtmlPicture(String httpUrl, String filePath) {

		//定义URL对象url
		URL url;
		//定义输入字节缓冲流对象in
		BufferedInputStream in;
		//定义文件输出流对象file
		FileOutputStream file;

		try {
			//输出“取网络图片”几个破字
			System.out.println("取网络图片");
			//获取图片名
			String fileName = httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", "");
			//定义图片存储路径
			//String filePath = "E:\\spider_internet\\pictures";
			//初始化url对象
			url = new URL(httpUrl);
			//初始化in对象，也就是获得url字节流
			in = new BufferedInputStream(url.openStream());
			file = new FileOutputStream(new File(filePath +"\\"+ fileName));
			int t;
			while ((t = in.read()) != -1) {
				file.write(t);
			}
			file.close();
			in.close();
			//输出几个破字
			System.out.println("图片获取成功");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前网页的code
	 * @param httpUrl
	 * @return
	 * @throws IOException
	 */
	public String getHtmlCode(String httpUrl) throws IOException {
		//定义字符串content
		String content = "";
		//生成传入的URL的对象
		URL url = new URL(httpUrl);
		//获得当前url的字节流（缓冲）
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String input;
		//当当前行存在数据时
		while ((input = reader.readLine()) != null) {
			//将读取数据赋给content
			content += input;
		}
		//关闭缓冲区
		reader.close();
		//返回content
		return content;
	}

	/**
	 * 图片扒取方法
	 * @param url
	 * @throws IOException
	 */
	public void get(String url,String filePath) throws IOException {

		//定义两个获取网页图片的正则表达式
		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";

		//获得content
		String content = this.getHtmlCode(url);
		//讲编译的正则表达式对象赋给pattern
		Pattern pattern = Pattern.compile(searchImgReg);
		//对字符串content执行正则表达式
		Matcher matcher = pattern.matcher(content);
		//获得匹配字符串的时候
		while (matcher.find()) {
			//输出第三个括号里的东西
			System.out.println(matcher.group(3));
			//执行getHtmlPicture方法，也就是下载图片
			this.getHtmlPicture(url + "/" +matcher.group(3),filePath);
		}

		//同上，只不过换个正则表达式
		pattern = Pattern.compile(searchImgReg2);
		matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			this.getHtmlPicture(matcher.group(3),filePath);

		}

	}


}
