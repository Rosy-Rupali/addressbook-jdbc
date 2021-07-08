package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ContactDetails;
import exception.AddressBookJDBCException;

public class AddressBookDBService {

	/**
	 * created getConnection() method to make connection with mySQL database
	 * 
	 * @return connection
	 * @throws AddressBookJDBCException
	 */
	private Connection getConnection() throws AddressBookJDBCException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String user = "root";
		String password = "Database123@";
		Connection connection;
		System.out.println("Connecting to database: " + jdbcURL);
		try {
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.println("Connection is SuccessFull!!! " + connection);
			return connection;
		} catch (SQLException e) {
			throw new AddressBookJDBCException("Unable to establish the connection");
		}
	}

	/**
	 * This method is used to retrieve the contact details from address book
	 * 
	 * @return addressBookList
	 * @throws AddressBookJDBCException
	 */
	public List<ContactDetails> readData() throws AddressBookJDBCException {
		String query = "select * from addressbook;";
		List<ContactDetails> addressBookList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String first_name = resultSet.getString("first_name");
				String last_name = resultSet.getString("last_name");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zip = resultSet.getInt("zip");
				long phone_number = resultSet.getLong("phone_number");
				String addressbook_name = resultSet.getString("addressbook_name");
				String addressbook_type = resultSet.getString("addressbook_type");
				addressBookList.add(new ContactDetails(first_name, last_name, address, city, state, zip, phone_number,
						state, addressbook_name, addressbook_type));
			}
		} catch (SQLException e) {
			throw new AddressBookJDBCException("Unable to get data from address book");
		}
		return addressBookList;
	}

}
