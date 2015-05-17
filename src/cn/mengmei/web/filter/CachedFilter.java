package cn.mengmei.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CachedFilter implements Filter {
	
	private Map<String,byte[]> cache = new HashMap<String,byte[]>();

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//接到请求先从缓存查有没有
		String uri = request.getRequestURI();
		System.out.println(uri);
		byte[] value = this.cache.get(uri);
		
		//如果有，直接从缓存取数据打给浏览器,程序return。
		if(value!=null){
			response.getOutputStream().write(value);
			return;
		}
				
		//如果没有，重写response,把资源读到自己的缓存流中， 给资源放行
		MyBufferResponse1 myResponse = new MyBufferResponse1(response);
		chain.doFilter(request, myResponse);
		
		//将缓存流中的数据以uri为key，存到缓存中，再打给浏览器。
		byte[] buffer = myResponse.getBuffer();
		this.cache.put(uri, buffer);
		response.getOutputStream().write(buffer);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

}


class MyBufferResponse1 extends HttpServletResponseWrapper{

	private HttpServletResponse response;
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();
	private PrintWriter pw;
	
	public MyBufferResponse1(HttpServletResponse response) {
		super(response);
		this.response = response;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new MyServletOutputStream(bout);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		pw = new PrintWriter(new OutputStreamWriter(bout, this.response.getCharacterEncoding()));
		return pw;
	}

	public byte[] getBuffer() {
		try{
			if(pw!=null){
				pw.close();
			}
			if(bout!=null){
				bout.flush();
				return bout.toByteArray();
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}

class MyServletOutputStream1 extends ServletOutputStream{
	
	private ByteArrayOutputStream bout;

	public MyServletOutputStream1(ByteArrayOutputStream bout){
		this.bout = bout;
	}

	@Override
	public void write(int b) throws IOException {
		this.bout.write(b);
	}
	
}