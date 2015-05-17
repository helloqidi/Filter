package cn.mengmei.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharacterEncodingFilter implements Filter {
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
				
		request = new MyCharacterEncodingRequest(request);
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

}

/*Request包装类：
1、实现与被增强对象相同的接口，或继承默认包装类。
2、定义一个变量记住被增强对象
3、定义一个构造器，接收被增强对象
4、覆盖需要增强的方法
5、对于不想增强的方法，直接调用被增强对象（目标对象）的方法
*/
class MyCharacterEncodingRequest extends HttpServletRequestWrapper{

	private HttpServletRequest request;
	
	public MyCharacterEncodingRequest(HttpServletRequest request){
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
			
			if(!this.request.getMethod().equalsIgnoreCase("get")){
				return value;
			}
			
			value = new String(value.getBytes("ISO8859-1"),this.request.getCharacterEncoding());
			return value;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
