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
import jakarta.servlet.http.HttpSession;

import dao.DAOConnection;

@WebServlet("/balance")
public class CheckBalance extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------ INSIDE GET METHOD OF CHECK BALANCE SERVLET --------------------");
		
		Cookie ck[]=request.getCookies();
		String accountNumber = "";
		for(int i=0;i<ck.length;i++){
			if(ck[i].getName().equals("accountNumber")) {
				accountNumber = ck[i].getValue();
				break;
			}
		}
		try {
			DAOConnection dao = new DAOConnection();
			Connection connection = dao.buildConnection();
			
			String query = "Select Balance from user where AccountNumber=?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, accountNumber);
			
			ResultSet result = pstmt.executeQuery();

			result.next();
			int balance = result.getInt("Balance");
			String data = "<html><h1>BALANCE IS:"+balance+" </h1><form action=\"Bank.jsp\">\r\n"
					+ "	<button type=\"submit\" class=\"btn btn4\">GO BACK</button>\r\n"
					+ "</form></html>";
			response.getWriter().write(data);
		}
		catch(Exception e) {
			System.out.println("----------- EXCEPTION INSIDE GET METHOD OF CHECK BALANCE -----------------");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}