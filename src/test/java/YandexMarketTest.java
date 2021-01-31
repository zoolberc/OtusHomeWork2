import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
    }


    @Test
    public void yandexMarketPhoneComparisonTest() {
        driver.get("https://market.yandex.ru/");
        driver.findElement(By.cssSelector("div._381y5orjSo:nth-child(1) > div:nth-child(5) > div:nth-child(1) > a:nth-child(1)")).click();
        if (driver.findElement(By.xpath("//*[contains(text(), 'Понятно')]")).isDisplayed()){
            driver.findElement(By.xpath("//*[contains(text(), 'Понятно')]")).click();
        }
        driver.findElement(By.cssSelector("div._1YdrMWBuYy:nth-child(1) > div:nth-child(2) > ul:nth-child(1) > li:nth-child(1) > div:nth-child(1) > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("div._3_phr-spJh:nth-child(3) > div:nth-child(1) > div:nth-child(1) > fieldset:nth-child(1) > ul:nth-child(2) > li:nth-child(10) > div:nth-child(1) > a:nth-child(1) > label:nth-child(1) > div:nth-child(2) > span:nth-child(1)")).click();
        if (driver.findElement(By.xpath("//*[contains(text(), 'Понятно')]")).isDisplayed()){
            driver.findElement(By.xpath("//*[contains(text(), 'Понятно')]")).click();
        }
        driver.findElement(By.cssSelector("div._3_phr-spJh:nth-child(3) > div:nth-child(1) > div:nth-child(1) > fieldset:nth-child(1) > ul:nth-child(2) > li:nth-child(11) > div:nth-child(1) > a:nth-child(1) > label:nth-child(1) > div:nth-child(2) > span:nth-child(1)")).click();
        driver.findElement(By.cssSelector("button._2zH77vazcW:nth-child(3)")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'Samsung')]/ancestor::article/descendant::div[@data-tid = 'd460c8a7']/div[2]/div")));
        driver.findElement(By.xpath("//*[contains(text(), 'Samsung')]/ancestor::article/descendant::div[@data-tid = 'd460c8a7']/div[2]/div")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'добавлен к сравнению')]")).isDisplayed();
        driver.findElement(By.xpath("//*[contains(text(), 'Xiaomi')]/ancestor::article/descendant::div[@data-tid = 'd460c8a7']/div[2]/div")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'добавлен к сравнению')]")).isDisplayed();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//*[contains(text(),'Сравнить')]")).click();
        int countPhone = driver.findElements(By.xpath("//*[@data-tid = '412661c']")).size();
        assertEquals(countPhone, 2);
    }

    @After
    public void setDown() {
        if (driver != null) {
            //driver.quit();
            logger.info("Browser driver closed");
        }
    }
}

