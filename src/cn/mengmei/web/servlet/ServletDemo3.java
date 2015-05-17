package cn.mengmei.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo3 extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String message = "AAAAA级景区";
		
		ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = new GZIPOutputStream(byteArrOut);
		
		gzipOut.write(message.getBytes("UTF-8"));
		gzipOut.finish();
		
		byte[] gzipByte = byteArrOut.toByteArray();
		
		response.setHeader("Content-Encoding", "gzip");
		response.setContentLength(gzipByte.length);
		response.setContentType("UTF-8");
		
		response.getOutputStream().write(gzipByte);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
