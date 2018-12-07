package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Holds all the transactions for a user.
 * @author Oliver Harris
 *
 */
public class Transactions {

	private String username;
	private ArrayList<Payment> payments;
	private static String DATE_FORMAT = "yyyy.MM.dd.HH.mm.ss";



	/**
	 * Constructor for transactions.
	 * @param username The username this transaction object is for.
	 * @param payments The payments this user has done.
	 */
	public Transactions(String username, ArrayList<Payment> payments) {
		this.username = username;
		this.payments = payments;

	}

	/**
	 * Gets the username.
	 * @return username.
	 */
	public String getUsername() {
		return username;
	}

	

	/**
	 * Get the list of payments this user has.
	 * @return ArrayList of payments.
	 */
	public ArrayList<Payment> getPayments() {
		return payments;
	}

	/**
	 * Add a payment.
	 * @param payment The payment object.
	 */
	public void addPayment(Payment payment) {
		payments.add(payment);
		String dateTimeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT "
					+ "INTO transactions (username,paid,dateTime) VALUES (?,?,?)");
	            statement.setString(1, username);
	            statement.setFloat(2, payment.getAmount());
	            statement.setString(3, dateTimeStamp);
	            statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Generate the transactions object for a username.
	 * @param username  The username in the database.
	 * @return The transactions the user has.
	 */
	public static Transactions getTransactions(String username){
		ArrayList<Payment> payments = new ArrayList<Payment>();
		try {
			Connection connection = DBHelper.getConnection(); 
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM "
					+ "transactions WHERE username=?");
			statement.setString(1,username);
			ResultSet results = statement.executeQuery(); 
			while(results.next()) {
				payments.add(new Payment(results.getInt("transactionId"),
						results.getString("username"),
						results.getFloat("paid"),
						results.getString("dateTime")));

			}
			return new Transactions(username,payments);
		} catch (SQLException e) {
			e.printStackTrace();
	}

		return null;
	}
	
}
