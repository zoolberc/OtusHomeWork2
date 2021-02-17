import config.VariableConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class FillDataOtusTest {
    protected static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(FillDataOtusTest.class);
    protected String browser;
    private VariableConfig config = ConfigFactory.create(VariableConfig.class);
    Properties appProps = new Properties();


    @Before
    public void SetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void test1() throws InterruptedException {
        driver.get(config.urlOtus());
        authorizations();
        entranceToLK();
        fillData();
    }

    @Test
    public void test2() throws InterruptedException {
        driver.get(config.urlOtus());
        authorizations();
        entranceToLK();
        checkData();
        deleteAdditionalContacts();
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
           // driver.quit();
            logger.info("Browser driver closed");
        }
    }

    public void authorizations() {
        String login = config.emailOtus();
        String password = config.passwordOtus();
        driver.findElement(By.cssSelector(".header2__auth")).click();
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).clear();
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).sendKeys(login);
        driver.findElement(By.cssSelector(".js-psw-input")).clear();
        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password + Keys.ENTER);
    }

    private void entranceToLK(){
        String iconLocator = ".header2-menu__item_dropdown_no-border";
        WebElement icon = driver.findElement(By.cssSelector(iconLocator));
        Actions action = new Actions(driver);
        action.moveToElement(icon).build().perform();
        driver.findElement(By.cssSelector(".header2-menu__dropdown-text")).click();
    }

    public void fillData() throws InterruptedException {

        String actPage = driver.findElement(By.cssSelector("h3.text")).getText();
        assertEquals(actPage,"Персональные данные");
        driver.findElement(By.cssSelector("#id_fname")).clear();
        driver.findElement(By.cssSelector("#id_fname")).sendKeys(config.firstName());
        driver.findElement(By.cssSelector("#id_fname_latin")).clear();
        driver.findElement(By.cssSelector("#id_fname_latin")).sendKeys(config.firstNameLatin());
        driver.findElement(By.cssSelector("#id_lname")).clear();
        driver.findElement(By.cssSelector("#id_lname")).sendKeys(config.lastName());
        driver.findElement(By.cssSelector("#id_lname_latin")).clear();
        driver.findElement(By.cssSelector("#id_lname_latin")).sendKeys(config.lastNameLatin());
        driver.findElement(By.cssSelector("#id_blog_name")).clear();
        driver.findElement(By.cssSelector("#id_blog_name")).sendKeys(config.blogName());
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).clear();
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).sendKeys(config.DOB());

        driver.findElement(By.xpath("//*[@data-ajax-slave = '/lk/biography/cv/lookup/cities/by_country/']")).click(); // выбор страны
        driver.findElement(By.xpath("//*[@title = 'Россия']")).click(); // выбор страны

        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@name ='city']/../..")).click(); // выбор города
        driver.findElement(By.xpath("//*[@title = 'Москва']")).click(); // выбор города

        driver.findElement(By.xpath("//*[@data-title = 'Уровень знания английского языка']/following::div[1]")).click(); // уровень английского
        driver.findElement(By.xpath("//*[@title = 'Средний (Intermediate)']")).click(); // выбор уровня

        Thread.sleep(500);
        // Добавление 1-го доп контакта
        driver.findElement(By.xpath("//*[@class ='placeholder']//..")).click();
        driver.findElement(By.xpath("//*[@title = 'VK']")).click(); // выбор способа связи ВК
        driver.findElement(By.id("id_contact-0-value")).clear();
        driver.findElement(By.id("id_contact-0-value")).sendKeys("https://vk.com/id=12345");

        // Добавление 2-го доп контакта
        driver.findElement(By.xpath("//*[contains(@class,'js-lk-cv-custom-select-add')]")).click();
        driver.findElements(By.xpath("//*[@class ='placeholder']//..")).get(0).click();
        driver.findElements(By.xpath("//*[@title = 'Skype']")).get(1).click();
        driver.findElement(By.id("id_contact-1-value")).clear();
        driver.findElement(By.id("id_contact-1-value")).sendKeys("AlievTural1234");

         driver.findElement(By.cssSelector("button.button_md-4:nth-child(2)")).click(); // сохранение данных
         driver.findElement(By.xpath("//*[@title = 'Редактировать резюме']")).isDisplayed(); // проверка что данные сораннены
    }

    public void checkData(){
        String actPage = driver.findElement(By.cssSelector("h3.text")).getText();
        assertEquals(actPage,"Персональные данные");
        assertEquals(driver.findElement(By.cssSelector("#id_fname")).getAttribute("value"),config.firstName());
        assertEquals(driver.findElement(By.cssSelector("#id_fname_latin")).getAttribute("value"),config.firstNameLatin());
        assertEquals(driver.findElement(By.cssSelector("#id_lname")).getAttribute("value"),config.lastName());
        assertEquals(driver.findElement(By.cssSelector("#id_lname_latin")).getAttribute("value"),config.lastNameLatin());
        assertEquals(driver.findElement(By.cssSelector("#id_blog_name")).getAttribute("value"),config.blogName());
        assertEquals(driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).getAttribute("value"),config.DOB());
        assertEquals(driver.findElement(By.xpath("//*[@data-ajax-slave = '/lk/biography/cv/lookup/cities/by_country/']")).getText(), "Россия");
        assertEquals(driver.findElement(By.xpath("//*[@name ='city']/../..")).getText(),"Москва");
        assertEquals("AlievTural1234", driver.findElement(By.id("id_contact-0-value")).getAttribute("value"));
        assertEquals( driver.findElement(By.id("id_contact-1-value")).getAttribute("value"),"https://vk.com/id=12345");
    }

    public void deleteAdditionalContacts() throws InterruptedException {
        Thread.sleep(5000);
          driver.findElements(By.xpath("//*[text() = 'Удалить']")).get(1).click();
//        driver.findElement(By.cssSelector("button.button_md-4:nth-child(2)")).click(); // сохранение данных
//        driver.findElement(By.xpath("//*[@title = 'Редактировать резюме']")).isDisplayed(); // проверка что данные сораннены
    }


}
