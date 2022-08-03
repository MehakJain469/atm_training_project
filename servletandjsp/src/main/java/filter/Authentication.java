package filter;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DAOConnection;

@WebFilter("/*")
public class Authentication extends HttpFilter implements Filter {
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		Cookie ck[] = httpRequest.getCookies();
		String accountNumber = null;
		for(int i=0;i<ck.length;i++){
			if(ck[i].getName().equals("accountNumber")) {
				accountNumber = ck[i].getValue();
				break;
			}
		}
		if(accountNumber == null) {
			System.out.println("INSIDE IF OF AUTHENTICATION");
			if(httpRequest.getParameter("accountNumber") == null || httpRequest.getParameter("password") == null) {
				httpRequest.getRequestDispatcher("Index.jsp").forward(httpRequest, httpResponse);
			}
			else
				chain.doFilter(httpRequest, httpResponse);
		}
		else {
			System.out.println("INSIDE ELSE OF AUTHENTICATION");
			chain.doFilter(httpRequest, httpResponse);			
		}
	}
}