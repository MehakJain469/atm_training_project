package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DAOConnection;
import entity.Statement;
import util.Formatter;

@WebServlet("/bankstatement")
public class BankStatement extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------- INSIDE GET METHOD OF BANK STATEMENT SERVLET ----------------");
		
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
			
			String query = "Select * from statement where AccountNumber=?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, accountNumber);
			System.out.println("QUERY FOR STATEMENTS: "+pstmt.toString());
			
			ResultSet result = pstmt.executeQuery();
			Formatter formatter = new Formatter();
			System.out.println("Result set for STATEMENTS: "+result.toString());
			List<Statement> transactions = formatter.formatDBData(result);
//			System.out.println("------------ TRANSACTION DETAILS -----------------\n"+transactions);
			
			String data = "<html><h1>BANK TRANSACTIONS:</h1><br><hr><p>"+transactions+" </p><form action=\"Bank.jsp\">\r\n"
					+ "	<button type=\"submit\" class=\"btn btn4\">GO BACK</button>\r\n"
					+ "</form></html>";
			response.getWriter().write(data);
		}
		catch(Exception e) {
			System.out.println("------- EXCEPTION IN BANK STATEMENT SERVLET GET METHOD----------------\n"+e.getMessage());
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}