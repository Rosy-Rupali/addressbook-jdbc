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
		Assert.assertEquals(7, contactDetails.size());
	}

}
