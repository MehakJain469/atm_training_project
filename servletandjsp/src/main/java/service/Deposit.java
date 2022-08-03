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

@WebServlet("/deposit")
public class Deposit extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("```````````````` INSIDE DEPOSIT OP POST METHOD `````````````````");
		
		Cookie ck[]=request.getCookies();
		String accountNumber = "";
		for(int i=0;i<ck.length;i++){
			if(ck[i].getName().equals("accountNumber")) {
				accountNumber = ck[i].getValue();
				break;
			}
		}
		int amount = Integer.parseInt(request.getParameter("amount"));
		System.out.println("\nAMOUNT PASSED IN DEPOIST IS: "+amount+" and ACCOUNT NUMBER IS: "+accountNumber);
		
		try {
			DAOConnection dao = new DAOConnection();
			Connection connection = dao.buildConnection();
			String query = "Update user set Balance=(Balance+?) where AccountNumber=?";
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, accountNumber);
			pstmt.executeUpdate();
			
			String balanceQuery = "Select Balance from user where AccountNumber=?";
			PreparedStatement balancePstmt = connection.prepareStatement(balanceQuery);
			balancePstmt.setString(1, accountNumber);
			
			ResultSet result = balancePstmt.executeQuery();
			result.next();
			int dbAmount = result.getInt("Balance");
			
			String transactionQuery = "Insert into statement(AccountNumber,InitialAmount,ClosingAmount)"
					+ " values(?,?,?)";
			PreparedStatement transactionPstmt = connection.prepareStatement(transactionQuery);
			transactionPstmt.setString(1, accountNumber);
			transactionPstmt.setInt(2, dbAmount-amount);
			transactionPstmt.setInt(3, dbAmount);
			transactionPstmt.executeUpdate();
			
			dao.closeConnection();
			
			Cookie cookie = new Cookie("balance",String.valueOf(dbAmount+amount));
			response.addCookie(cookie);
			//refresh the page instead of forwarding and keep cookies stored in the browser
			request.getRequestDispatcher("Bank.jsp").forward(request, response);
		}
		catch(Exception e) {
			System.out.println("--------- ERROR IN DEPOST -----------------"+e.getMessage());
			//throw exception
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
	}

}