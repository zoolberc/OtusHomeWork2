import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class YandexMarketTest {
    protected static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(YandexMarketTest.class);

    @Before
    public void setUp()  {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Browser driver open");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    @Test
    public void yandexMarketPhoneComparisonTest() throws InterruptedException {
        driver.get("https://market.yandex.ru/");

        WebElement buttonPonyatno = (new WebDriverWait(driver, 10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Понятно')]")));

        if (buttonPonyatno.isDisplayed()){
            buttonPonyatno.click();
        }

        driver.findElement(By.cssSelector("div._381y5orjSo:nth-child(1) > div:nth-child(4) > div:nth-child(1) > a:nth-child(1)")).click();

        driver.findElement(By.xpath("//*[text() = 'Смартфоны']")).click();

        driver.findElement(By.xpath("//*[contains(@name, 'Производитель Samsung')]/..")).click();
        driver.findElement(By.xpath("//*[contains(@name, 'Производитель Xiaomi')]/..")).click();

        driver.findElement(By.cssSelector("button._2zH77vazcW:nth-child(3)")).click();

        driver.findElement(By.xpath("//h1[contains(text(), 'Мобильные телефоны')]")).isDisplayed();

        driver.findElement(By.xpath("//*[contains(text(), 'Samsung')]/ancestor::article/descendant::div[@data-tid = 'd460c8a7']/div[2]/div")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'добавлен к сравнению')]")).isDisplayed();

        driver.findElement(By.xpath("//*[contains(text(), 'Xiaomi')]/ancestor::article/descendant::div[@data-tid = 'd460c8a7']/div[2]/div")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'добавлен к сравнению')]")).isDisplayed();

        driver.findElement(By.xpath("//h1[contains(text(), 'Мобильные телефоны')]")).isDisplayed();


        WebElement compareButton = driver.findElement(By.xpath("//*[contains(text(),'Сравнить')]/.."));
        driver.get(compareButton.getAttribute("href"));

        int countPhone = driver.findElements(By.xpath("//*[@data-tid = '412661c']")).size();
        assertEquals(countPhone, 2);
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser driver closed");
        }
    }
}

