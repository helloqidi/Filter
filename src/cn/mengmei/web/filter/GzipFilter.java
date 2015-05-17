package cn.mengmei.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

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

public class GzipFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		MyBufferResponse myResponse = new MyBufferResponse(response);
		
		chain.doFilter(request, myResponse);
		
		System.out.println("<<< "+myResponse.getBuffer().length);
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bout);
		gzip.write(myResponse.getBuffer());
		gzip.finish();
		
		System.out.println(">>> " + bout.toByteArray().length);
		
		response.setHeader("Content-Encoding", "gzip");
		response.setContentLength(bout.toByteArray().length);
		
		response.getOutputStream().write(bout.toByteArray());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}
}


class MyBufferResponse extends HttpServletResponseWrapper{

	private HttpServletResponse response;
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();
	private PrintWriter pw;
	
	public MyBufferResponse(HttpServletResponse response) {
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

class MyServletOutputStream extends ServletOutputStream{
	
	private ByteArrayOutputStream bout;

	public MyServletOutputStream(ByteArrayOutputStream bout){
		this.bout = bout;
	}

	@Override
	public void write(int b) throws IOException {
		this.bout.write(b);
	}
	
}