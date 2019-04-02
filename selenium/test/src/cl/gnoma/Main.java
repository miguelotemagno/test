package cl.gnoma;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String user = args[0];
        String pass = args[1];
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", "/home/gnoma/proyectos/selenium/test/chromedriver");
        System.setProperty("webdriver.firefox.driver", "/home/gnoma/proyectos/selenium/test/geckodriver");

        WebDriver driver = new FirefoxDriver();
        //WebDriver driver = new ChromeDriver();
        driver.get("http://www.gnoma.cl:2095");

        try {
            Thread.sleep(5000);  // Let the user actually see something!
            WebElement userBox = driver.findElement(By.id("user"));
            userBox.sendKeys(user);

            WebElement passBox = driver.findElement(By.id("pass"));
            passBox.sendKeys(pass);

            Thread.sleep(5000);  // Let the user actually see something!

            WebElement loginForm = driver.findElement(By.id("login_form"));
            loginForm.submit();

            Thread.sleep(10000);  // Let the user actually see something!

            WebElement client = driver.findElement(By.id("hordePreview"));
            client.click();

            Thread.sleep(30000);  // Let the user actually see something!

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();

    }
}
