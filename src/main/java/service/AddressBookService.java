package service;

import java.util.List;

import exception.AddressBookJDBCException;
import model.*;

public class AddressBookService {
	public AddressBookDBService addressBookDBService;
	private List<ContactDetails> contactList;

	public AddressBookService() {
		this.addressBookDBService = new AddressBookDBService();
	}

	/**
	 * created readAddressBookData method to read data from database
	 * 
	 * @return this.addressBookDBService.readData()
	 * @throws AddressBookJDBCException
	 */
	public List<ContactDetails> readAddressBookData() throws AddressBookJDBCException {
		return this.addressBookDBService.readData();
	}

	/**
	 * This method update the contact if the first_name matches in the addressbook table
	 * @param name
	 * @param state
	 * @param zip
	 * @throws AddressBookJDBCException
	 */
	public void updateContactDetails(String state, int zip, String name) throws AddressBookJDBCException {
		int result = new AddressBookDBService().updateAddressBookDataUsingPreparedStatement(state, zip, name);
		if (result == 0)
			return;
		ContactDetails addressBookData = this.getContactDetails(name);
		if (addressBookData != null) {
			addressBookData.setZip(zip);
			addressBookData.setState(state);
		}
	}

	/**
	 * getContactDetails method to get the data of person from contact details list y using stream api
	 * @param name : first_name
	 * @return first_name if it matches with the name present in the database
	 */
	private ContactDetails getContactDetails(String name) {
		return this.contactList.stream()
				.filter(addressBookDataListObject -> addressBookDataListObject.getFirst_name().equals(name)).findFirst()
				.orElse(null);
	}

	/**
	 * checkAddressBookInSyncWithDB method check update details whether synced or not with database
	 * @param name : first name of contact details
	 * @return synced contact list
	 * @throws AddressBookJDBCException
	 */
	public boolean checkAddressBookInSyncWithDB(String name) throws AddressBookJDBCException {
		List<ContactDetails> contactList = new AddressBookDBService().getAddressBookDataFromDB(name);
		return contactList.get(0).equals(getContactDetails(name));
	}
}
