package service;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/withdraw")
public class Withdraw extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------------- INSIDE WITHDRAW GET -----------------------");
		response.getWriter().append("INSIDE GET OF WITHDRAW");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int amount = Integer.parseInt(request.getParameter("amount"));
		
		Cookie ck[]=request.getCookies();
		String accountNumber = "";
		for(int i=0;i<ck.length;i++){
			if(ck[i].getName().equals("accountNumber")) {
				accountNumber = ck[i].getValue();
				break;
			}
		}

		if(amount < 0) {
			System.out.println("Invalid amount.");
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
		
		try {
			DAOConnection dao = new DAOConnection();
			Connection connection = dao.buildConnection();
			
			String query = "Select Balance from user where AccountNumber=?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, accountNumber);
			System.out.println("WITHDRAW QUERY IS: "+pstmt.toString());
			
			ResultSet result = pstmt.executeQuery();
			result.next();
			int dbAmount = result.getInt("Balance");
			System.out.println("ENTERED AMOUNT IS: "+amount+" BALANCE IS: "+dbAmount);
			
			if(amount > dbAmount) {
				System.out.println("Invalid amount entered.");
				//throw exception
				request.getRequestDispatcher("Error.jsp").forward(request, response);
			}
			else {
				String updateBalanceQuery = "Update user set Balance=? where AccountNumber=?";
				
				PreparedStatement updatePstmt = connection.prepareStatement(updateBalanceQuery);
				updatePstmt.setInt(1, dbAmount-amount);
				updatePstmt.setString(2, accountNumber);
				updatePstmt.executeUpdate();
				
				String transactionQuery = "Insert into statement(AccountNumber,InitialAmount,ClosingAmount)"
						+ " values(?,?,?)";
				PreparedStatement transactionPstmt = connection.prepareStatement(transactionQuery);
				transactionPstmt.setString(1, accountNumber);
				transactionPstmt.setInt(2, dbAmount);
				transactionPstmt.setInt(3, dbAmount-amount);
				transactionPstmt.executeUpdate();
				
				//refresh the page instead of forwarding & keep cookies store in the browser
				request.getRequestDispatcher("Bank.jsp").forward(request, response);
			}
			
			dao.closeConnection();
		}
		catch(Exception e) {
			System.out.println("----------- Exception in Withdraw ---------------\n");
			e.printStackTrace();
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
	}
}