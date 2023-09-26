package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

	public static final String CREDENTIAL_URL = "https://www.google.com/";
	public static final String CREDENTIAL_USERNAME = "jane";
	public static final String CREDENTIAL_PASSWORD = "123";
	public static final String CREDENTIAL_URL2 = "http://www.naver.com/";
	public static final String CREDENTIAL_USERNAME2 = "tarzan";
	public static final String CREDENTIAL_PASSWORD2 = "abc";


	@Test
	public void testCredentialCreation() {
		HomeTest homeTest = signUpAndLogin();
		createAndVerifyCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, homeTest);
		homeTest.deleteCredential();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.logout();
	}

	private void createAndVerifyCredential(String url, String username, String password, HomeTest homeTest) {
		createCredential(url, username, password, homeTest);
		homeTest.navToCredentialsTab();
		Credential credential = homeTest.getFirstCredential();
		Assertions.assertEquals(url, credential.getUrl());
		Assertions.assertEquals(username, credential.getUserName());
		Assertions.assertNotEquals(password, credential.getPassword());
	}

	private void createCredential(String url, String username, String password, HomeTest homeTest) {
		homeTest.navToCredentialsTab();
		homeTest.addNewCredential();
		setCredentialFields(url, username, password, homeTest);
		homeTest.saveCredentialChanges();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.navToCredentialsTab();
	}

	private void setCredentialFields(String url, String username, String password, HomeTest homeTest) {
		homeTest.setCredentialUrl(url);
		homeTest.setCredentialUsername(username);
		homeTest.setCredentialPassword(password);
	}


	@Test
	public void testCredentialModification() {
		HomeTest homeTest = signUpAndLogin();
		createAndVerifyCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, homeTest);
		Credential originalCredential = homeTest.getFirstCredential();
		String firstEncryptedPassword = originalCredential.getPassword();
		homeTest.editCredential();
		String newUrl = CREDENTIAL_URL2;
		String newCredentialUsername = CREDENTIAL_USERNAME2;
		String newPassword = CREDENTIAL_PASSWORD2;
		setCredentialFields(newUrl, newCredentialUsername, newPassword, homeTest);
		homeTest.saveCredentialChanges();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.navToCredentialsTab();
		Credential modifiedCredential = homeTest.getFirstCredential();
		Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
		Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());
		String modifiedCredentialPassword = modifiedCredential.getPassword();
		Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
		Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
		homeTest.deleteCredential();
		resultTest.clickOk();
		homeTest.logout();
	}

	@Test
	public void testDeletion() {
		HomeTest homeTest = signUpAndLogin();
		createCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, homeTest);
		createCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD, homeTest);
		createCredential("http://www.daum.net/", "jane", "123", homeTest);
		Assertions.assertFalse(homeTest.noCredentials(driver));
		homeTest.deleteCredential();
		ResultTest resultTest = new ResultTest(driver);
		resultTest.clickOk();
		homeTest.navToCredentialsTab();
		homeTest.deleteCredential();
		resultTest.clickOk();
		homeTest.navToCredentialsTab();
		homeTest.deleteCredential();
		resultTest.clickOk();
		homeTest.navToCredentialsTab();
		Assertions.assertTrue(homeTest.noCredentials(driver));
		homeTest.logout();
	}
}
