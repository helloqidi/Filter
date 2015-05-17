package cn.mengmei.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/*脏话过滤器*/
public class DirtyCharacterFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request = new MyDirtyCharacterRequest(request);
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

}

/*
Request包装类：
*/
class MyDirtyCharacterRequest extends HttpServletRequestWrapper{
	
	private List<String> dirty = Arrays.asList("操蛋","傻B","傻b","畜生");
	private HttpServletRequest request;
	
	public MyDirtyCharacterRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		try{
			
			String value = this.request.getParameter(name);
			
			if(value==null){
				return null;
			}
						
			for(String s : dirty){
				if(value.contains(s)){
					value = value.replace(s, "***");
				}
			}
			return value;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
