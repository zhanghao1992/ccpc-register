package com.zhongqi.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @desc
 * @author GaoLi
 * @date 2015年4月30日 
 */
public class BaseController 
{
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	public String[] getIpAddress(HttpServletRequest request) {
		String ip[] = new String[2];
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				//根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		try {
			String hostip = InetAddress.getLocalHost().getHostAddress();//服务器IP
			ip[0] = hostip;
			ip[1] = ipAddress;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public String getRequest(HttpServletRequest req) throws IOException {
		BufferedReader br;
		String data = null;
		StringBuffer input = null;
		br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) req.getInputStream(), "UTF-8"));
		input = new StringBuffer();
		while ((data = br.readLine()) != null) {
			input.append(data);
		}
		br.close();
		return input.toString();
	}
	public void ajax(Object json,HttpServletResponse response)
	{
		try 
		{
			response.setCharacterEncoding("utf-8");
			//response.setContentType("application/json");
			response.setContentType("text/html");

//			response.setContentLength(json.toString().length());
			PrintWriter out = response.getWriter();
			out.print(json); 
			out.flush(); 
			out.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void response(String json, HttpServletResponse response)
	{
		try
		{
//			log.info(json);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
//			response.setContentLength(json.toString().length());
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void responseWithLog(String json, HttpServletResponse response)
	{
		try
		{
			log.info(json);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
//			response.setContentLength(json.toString().length());
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void response(String json, String desc, HttpServletResponse response)
	{
		try
		{
			log.info(json + "____" + desc);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
//			response.setContentLength(json.toString().length());
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public String toJsonFromArray(Object obj){
		return JSONArray.fromObject(obj).toString();
	}
	public String toJson(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
}
