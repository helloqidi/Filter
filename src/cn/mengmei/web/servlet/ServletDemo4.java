package cn.mengmei.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo4 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*String message = "我爱你，中国🇨🇳abcdefghigklmn我爱你，中国🇨🇳abcdefghigklmn我爱你，中国🇨🇳abcdefghigklmn我爱你，中国🇨🇳abcdefghigklmn";
		
		response.getWriter().write(message);*/
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
