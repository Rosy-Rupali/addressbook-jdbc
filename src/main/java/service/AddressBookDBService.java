package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ContactDetails;
import exception.AddressBookJDBCException;

public class AddressBookDBService {

	List<ContactDetails> addressBookDataObj = null;

	/**
	 * created getConnection() method to make connection with mySQL database
	 * 
	 * @return connection
	 * @throws AddressBookJDBCException
	 */
	public Connection getConnection() throws AddressBookJDBCException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?&useSSL=false";
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
				String email_id = resultSet.getString("email_id");
				String addressbook_name = resultSet.getString("addressbook_name");
				String addressbook_type = resultSet.getString("addressbook_type");
				addressBookList.add(new ContactDetails(first_name, last_name, address, city, state, zip, phone_number,
						email_id, addressbook_name, addressbook_type));
			}
		} catch (SQLException e) {
			throw new AddressBookJDBCException("Unable to get data from address book");
		}
		return addressBookList;
	}

	/**
	 * created updateAddressBookDataUsingPreparedStatement method to update data in database by using sql query
     * added try and catch block to throw sql exception
	 * @param state
	 * @param zip
	 * @param name
	 * @return size of addressBookDataObj
	 * @throws AddressBookJDBCException
	 */
	public int updateAddressBookDataUsingPreparedStatement(String state, int zip, String name)
			throws AddressBookJDBCException {
		String query = "update addressbook set state = ? , zip = ? where first_name = ?";
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, state);
			preparedStatement.setInt(2, zip);
			preparedStatement.setString(3, name);
			int result = preparedStatement.executeUpdate();
			addressBookDataObj = getAddressBookDataFromDB(name);
			if (result > 0 && addressBookDataObj != null) {
				((ContactDetails) addressBookDataObj).setState(state);
				((ContactDetails) addressBookDataObj).setZip(zip);
			}
		} catch (SQLException e) {
			throw new AddressBookJDBCException("Error in updation");
		}
		return addressBookDataObj.size();
	}

	/**
	 * This method is used to retrieve data from database and check whether the values are updated or not 
	 * if the name is matched with first name
	 * @param name 
	 * @return addressBookList having data according to the addressbok table of addressbook_service
	 * @throws AddressBookJDBCException
	 */
	public List<ContactDetails> getAddressBookDataFromDB(String name) throws AddressBookJDBCException {
		String query = "select * from addressbook where first_name=?;";
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
				String email_id = resultSet.getString("email_id");
				String addressbook_name = resultSet.getString("addressbook_name");
				String addressbook_type = resultSet.getString("addressbook_type");
				addressBookList.add(new ContactDetails(first_name, last_name, address, city, state, zip, phone_number,
						email_id, addressbook_name, addressbook_type));
			}
		} catch (SQLException e) {
			throw new AddressBookJDBCException("Unable to get data.Please check table for updation");
		}
		return addressBookList;
	}

}
