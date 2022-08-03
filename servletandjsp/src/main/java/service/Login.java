package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DAOConnection;

@WebServlet("/login")
public class Login extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		System.out.println("--------- INSIDE LOGIN SERVLET ---------------");
		
		String accountNumber = request.getParameter("accountNumber");
		String password = request.getParameter("password");
		try {
			DAOConnection dao = new DAOConnection();
			Connection connection = dao.buildConnection();
			
			String query = "Select * from user where AccountNumber=? and password=?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, accountNumber);
			pstmt.setString(2, password);
			
			ResultSet result = pstmt.executeQuery();
		
			if(result.next() == false) {
//				System.out.println("INSIDE IF OF RESULT.NEXT()");
				request.getRequestDispatcher("Index.jsp").forward(request,response);
			}else {
				Cookie cookie = new Cookie("accountNumber",accountNumber);
				response.addCookie(cookie);
				request.getRequestDispatcher("Bank.jsp").forward(request, response);
			}
		}
		catch(Exception e) {
			System.out.println("----------- EXCEPTION INSIDE LOGIN SERVLET -------------\n");
			e.printStackTrace();
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
	}

}