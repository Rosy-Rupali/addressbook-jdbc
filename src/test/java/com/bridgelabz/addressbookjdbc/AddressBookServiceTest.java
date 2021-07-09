package com.bridgelabz.addressbookjdbc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import exception.AddressBookJDBCException;
import model.ContactDetails;
import service.AddressBookService;

public class AddressBookServiceTest {

	@Test
	public void givenAddressBookInDB_WhenDataRetrieved_ShouldMatchBookCount() throws AddressBookJDBCException {

		AddressBookService addressBookService = new AddressBookService();
		List<ContactDetails> contactDetails = addressBookService.readAddressBookData();
		Assert.assertEquals(6, contactDetails.size());
	}

	@Test
	public void givenAddressBookInDB_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB()
			throws AddressBookJDBCException {
		List<ContactDetails> contactDetails;
		AddressBookService addressBookService = new AddressBookService();
		contactDetails = addressBookService.readAddressBookData();
		addressBookService.updateContactDetails("MP", 22100, "Aman");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("Aman");
		Assert.assertTrue(result);
	}

}
