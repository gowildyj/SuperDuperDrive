	package com.udacity.jwdnd.course1.cloudstorage;

	import io.github.bonigarcia.wdm.WebDriverManager;
	import org.junit.jupiter.api.*;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.web.server.LocalServerPort;

	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	class CloudStorageApplicationTests {

		protected WebDriver driver;
		@LocalServerPort
		protected int port;

		@BeforeAll
		static void beforeAll() {
			WebDriverManager.chromedriver().setup();
		}

		@BeforeEach
		public void beforeEach() {
			this.driver = new ChromeDriver();
		}

		@AfterEach
		public void afterEach() {
			if (this.driver != null) {
				driver.quit();
			}
		}

		@Test
		public void getLoginPage() {
			driver.get("http://localhost:" + this.port + "/login");
			Assertions.assertEquals("Login", driver.getTitle());
		}

		protected HomeTest signUpAndLogin() {
			driver.get("http://localhost:" + this.port + "/signup");
			SignupTest signupTest = new SignupTest(driver);
			signupTest.setFirstName("Jane");
			signupTest.setLastName("Porter");
			signupTest.setUserName("jane");
			signupTest.setPassword("123");
			signupTest.signUp();
			driver.get("http://localhost:" + this.port + "/login");
			LoginTest loginTest = new LoginTest(driver);
			loginTest.setUserName("jane");
			loginTest.setPassword("123");
			loginTest.login();

			return new HomeTest(driver);
		}
	}
