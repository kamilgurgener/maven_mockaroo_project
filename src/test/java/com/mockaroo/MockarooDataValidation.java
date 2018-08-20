package com.mockaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

public class MockarooDataValidation {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		driver.get("https://mockaroo.com/");

	}

	// @AfterClass
	// public void clenUp() {
	// driver.close();
	// }

	@Test
	public void DataValidation() throws InterruptedException, IOException {
		String actual = driver.getTitle();
		String expected = "Random Data Generator";
		Assert.assertTrue(actual.contains(expected));

		// ============================ #4 ================================

		String actual2 = driver.findElement(By.xpath("//div[@class='tagline']")).getText();
		String expected2 = "realistic data generator";
		Assert.assertTrue(actual2.contains(expected2));

		String actual3 = driver.findElement(By.xpath("//div[@class='brand']")).getText();
		String expected3 = "mockaroo";
		Assert.assertTrue(actual3.contains(expected3));

		// ============================ #5 ================================

		for (int i = 6; i >= 1; i--) {
			driver.findElement(By.xpath("(//a[@class='close remove-field remove_nested_fields'])[" + i + "]")).click();
		}

		// ============================ #6 ================================
		System.out.println(
				"Field name is displayed: " + driver.findElement(By.xpath("//div[text()='Field Name']")).isDisplayed());
		System.out.println("Type is displayed: " + driver.findElement(By.xpath("//div[text()='Type']")).isDisplayed());
		System.out.println(
				"Options name is displayed: " + driver.findElement(By.xpath("//div[text()='Options']")).isDisplayed());

		// ============================ #7 ================================
		
		System.out.println("Add another field button is enabled: "
				+ driver.findElement(By.xpath("//a[text()='Add another field']")).isEnabled());

		// ============================ #8 ================================
		
		String actual4 = driver.findElement(By.xpath("//input[@class='medium-number form-control']"))
				.getAttribute("value");
		String expected4 = "1000";
		assertEquals(actual4, expected4);

		// ============================ #9 ================================

		String actual5 = driver.findElement(By.xpath("//select[@id='schema_file_format']")).getAttribute("value");
		String expected5 = "csv";
		assertEquals(actual5, expected5);

		// ============================ #10 ================================

		String actual6 = driver.findElement(By.xpath("//*[@id=\"schema_line_ending\"]/option[1]")).getText();
		String expected6 = "Unix (LF)";
		assertEquals(actual6, expected6);

		// ============================ #11 ================================
		
		assertTrue(driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected());
		assertFalse(driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected());

		// ============================ #12 ================================
		
		driver.findElement(By.xpath("//a[@class = 'btn btn-default add-column-btn add_nested_fields']"))
		.sendKeys(Keys.ENTER + "city");

		// ============================ #13 ================================
		
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[7]/div[3]/input[3]")).click();
		Thread.sleep(1000);
		assertEquals(driver.findElement(By.xpath("//*[@id=\"type_dialog\"]/div/div/div[1]/h3")).getText(),
				"Choose a Type");
		
		// ============================ #14 ================================
		
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("city");
		driver.findElement(By.xpath("//*[@id=\"type_list\"]/div/div[3]")).click();
		
		// ============================ #15 ================================
		
		driver.findElement(By.xpath("//a[@class = 'btn btn-default add-column-btn add_nested_fields']"))
		.sendKeys(Keys.ENTER + "country");
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[8]/div[3]/input[3]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"type_search_field\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"type_search_field\"]")).sendKeys("country");
		driver.findElement(By.xpath("//*[@id=\"type_list\"]/div[1]/div[3]")).click();
		Thread.sleep(1000);
		
		
		// ============================ #16 ================================

		driver.findElement(By.xpath("//button[@id='download']")).click();
		
		// ============================ #17 ================================
		
		Thread.sleep(2000);
		BufferedReader bf = new BufferedReader(new FileReader("/Users/mac/Downloads/MOCK_DATA.csv"));
		List<String> data = new ArrayList<>();
		String temp = bf.readLine();
		while (temp != null) {
		    data.add(temp);
		    temp = bf.readLine();
		}
		 
		// ============================ #18 ================================
		
		assertEquals(data.get(0), "city,country");
		
		// ============================ #19 ================================
		
		data.remove(0);
		assertEquals(data.size(), 1000);
		
		// ============================ #20 ================================
		
		List<String> cities = new ArrayList<>();
		for (String each : cities) {
			cities.add(each.substring(0, each.indexOf(",")));
			
		// ============================ #21 ================================
			
			List<String> countries = new ArrayList<>();
			for (String str : data) {
			    countries.add(str.substring(str.indexOf(",") + 1));
			}
		// ============================ #22 ================================
			
			Collections.sort(cities);
			String cityShort = cities.get(0);
			String cityLong = cities.get(0);
			for (int i = 1; i < cities.size(); i++) {
			    if (cityShort.length() > cities.get(i).length()) {
				cityShort = cities.get(i);
			    }
			    if (cities.get(i).length() > cityLong.length()) {
				cityLong = cities.get(i);
				}
			}
			System.out.println("The city with shortest name in the list is: "+cityShort);
			System.out.println("The city with longest name in the list is: "+cityLong);

			//==============================  #23  ========================================

			SortedSet<String> sortedCountry = new TreeSet<>(countries);
			for (String str : sortedCountry) {
				System.out.println(str + " was listed " + Collections.frequency(countries, str)+" times");
			}
			
			//==============================  #24  ========================================
			
			Set<String> citiesSet = new HashSet<>(cities);	
			
			//==============================  #25  ========================================

			int uniqueCityCount = 0;
			for (int i = 0; i < cities.size(); i++) {
				if (i == cities.lastIndexOf(cities.get(i)))
					uniqueCityCount++;
			}
			assertEquals(uniqueCityCount, citiesSet.size());
			//==============================  #26   ========================================
			
			Set<String> countrySet = new HashSet<>(countries);
			
			//==============================  #27   ========================================

			int uniqueCountryCount = 0;
			for (int i = 0; i < countries.size(); i++) {
				if (i == countries.lastIndexOf(countries.get(i)))
					uniqueCountryCount++;
			}
			assertEquals(uniqueCountryCount, countrySet.size());
			
		}
		///Users/mac/Desktop/JAVAC/maven_mockaroo_project
	}
}
