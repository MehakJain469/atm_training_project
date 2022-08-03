package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DAOConnection;

@WebServlet("/register")
public class Registration extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------- INSIDE REGISTRATION SERVLET ---------------");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
		String accountNumber = request.getParameter("accountNumber");
		String password = request.getParameter("password");
		int balance = Integer.parseInt(request.getParameter("balance"));
		
		System.out.println("DATA FETCHED");
		try {
			DAOConnection dao = new DAOConnection();
			Connection connection = dao.buildConnection();

			String query = "Insert into user(FirstName,LastName,PhoneNumber,AccountNumber,Password,Balance) values(?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setInt(3, phoneNumber);
			pstmt.setString(4, accountNumber);
			pstmt.setString(5, password);
			pstmt.setInt(6, balance);
			pstmt.executeUpdate();
			System.out.println("USER REGISTERED QUERY SUCCESS");
			
			Cookie cookie = new Cookie("accountNumber",accountNumber);
			response.addCookie(cookie);
			request.getRequestDispatcher("Bank.jsp").forward(request, response);
		}
		catch(Exception e) {
			System.out.println("----------- EXCEPTION INSIDE REGISTRATION SERVLET -------------\n"+e.getMessage());
			e.printStackTrace();
		}
	}
}