package cl.gnoma;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    Properties prop;
    final static String LOGIN = "login";
    final static String WAIT = "wait";
    final static String ADD_POTENCIAL = "addPotencial";
    final static String SELECT = "ng-select";
    final static String INPUT = "input";

    public String generaRut() {
        int randomNum = 999999 + (int)(Math.random() * 99999999);
        Integer rand = new Integer(randomNum);
        String rut = rand.toString();
        String[] arr = rut.split("");
        List<Integer> digits = new ArrayList<Integer>();

        for(String digit: arr) {
            digits.add(Integer.parseInt(digit));
        }

        Collections.reverse(digits);
        Integer[] array = new Integer[digits.size()];
        for (int i=0; i < array.length; i++)
        {
            array[i] = digits.get(i).intValue();
        }

        int rutSumado = 0;

        int a = 2;
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] * a;
            rutSumado += Integer.parseInt(String.valueOf(array[i]));
            if (a == 7) {
                a = 1;
            }
            a++;
        }

        int resto = rutSumado % 11;
        String Digito = String.valueOf(11 - resto);

        if (Digito.equals("11")) {
            Digito = "0";
        }

        if (Digito.equals("10")) {
            Digito = "K";
        }

        return rut+"-"+Digito;
    }

    public void loadProperties(String file) {
        this.prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // write your code here
        Main self = new Main();
        String propertiesFile = args[1];
        String browser = args[0];
        self.loadProperties(propertiesFile);

        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", self.prop.getProperty("chromeDriver"));
        System.setProperty("webdriver.firefox.driver", self.prop.getProperty("firefoxDriver"));

        String sequence = self.prop.getProperty("sequence");

        //WebDriver driver = new FirefoxDriver();
        WebDriver driver = browser.equalsIgnoreCase("chrome") ?new ChromeDriver() :new FirefoxDriver();
        driver.get(self.prop.getProperty("url"));

        for(String paso : sequence.split(","))
            switch (paso) {
                case LOGIN:
                    self.login(self, driver);
                    break;
                case ADD_POTENCIAL:
                    self.addPotencial(self, driver);
                    break;
                case WAIT:
                default:
                    self.sleep();
                    break;
            }

        driver.quit();
    }

    public void login(Main self, WebDriver driver) {
        System.out.printf("\n--> login:\n");
        try {
            Thread.sleep(5000);  // Let the user actually see something!
            WebElement userBox = driver.findElement(By.name("email"));
            userBox.sendKeys(self.prop.getProperty("user"));

            WebElement passBox = driver.findElement(By.name("password"));
            passBox.sendKeys(self.prop.getProperty("pass"));

            Thread.sleep(5000);  // Let the user actually see something!

            WebElement loginForm = driver.findElements(By.tagName("form")).get(0);
            loginForm.submit();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep() {
        System.out.printf("\n--> sleep:\n");
        try {
            Thread.sleep(10000);  // Let the user actually see something!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addPotencial(Main self, WebDriver driver) {
        System.out.printf("\n--> addPotencial:\n");
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        //jse.executeScript("$('#gbqfba').click();");

        try {
            WebElement potenciales = driver.findElement(By.id("tabPotenciales"));
            potenciales.click();

            Thread.sleep(5000);  // Let the user actually see something!

            WebElement boton = driver.findElement(By.xpath("//*[text()='Agregar potencial']"));
            boton.click();

            String datos = self.prop.getProperty("cliPotencial");

            Thread.sleep(5000);  // Let the user actually see something!

            for(String dato: datos.split(";")) {
                String[] dat = dato.split(":");
                String key = dat[0];
                String val = dat[1];
                System.out.printf("key:'%s' val:'%s'\t", key, val);

                //jse.executeScript("$('input[name="+key+"]').val('"+val+"');");

                WebElement elem = driver.findElement(By.name(key));
                elem.click();

                String tag = elem.getTagName();
                System.out.printf("tag:'%s'\n", tag);

                switch (tag) {
                    case SELECT:
                        WebElement elem2 = driver.findElement(By.xpath("//*/li/span[contains(text(),'"+val+"')]"));
                        elem2.click();
                        break;
                    case INPUT:
                        if(val.equals("{RUT}"))
                            val = self.generaRut();
                        elem.sendKeys(val);
                        break;
                    default:
                        break;
                }

                Thread.sleep(2000);  // Let the user actually see something!
            }

            WebElement submit = driver.findElements(By.xpath("//*/button[@type='submit']")).get(0);
            submit.click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
