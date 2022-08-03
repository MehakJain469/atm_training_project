package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import entity.Statement;

public class Formatter {
	
	public List<Statement> formatDBData(ResultSet result) throws SQLException {
		List<Statement> transactions = new ArrayList<>();
		
		while(result.next()) {
			Statement currentTransaction = new Statement(result.getString("AccountNumber"),
					result.getInt("InitialAmount"),
					result.getInt("ClosingAmount"),
					result.getDate("TransactionTime"));
			System.out.println("INSIDE WHILE OF FORMATTER: "+currentTransaction);
			transactions.add(currentTransaction);
		}
		
		return transactions;
	}
}