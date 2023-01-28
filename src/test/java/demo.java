import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class demo {

    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException, MalformedURLException {
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath + "/browser_drivers/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", "enable-logging");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--log-level=1");

        driver = new RemoteWebDriver(
                new URL("http://127.0.0.1:9515"),
                chromeOptions
        );
        driver.get("https://www.ericsson.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        Thread.sleep(5000);
    }

    @Test
    public void TC_01() throws InterruptedException {
        try {
            driver.navigate().to("https://www.ericsson.com/en/events/imagine-possible/content/extended-reality-and-metaverse-track");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);

        driver.findElement(By.cssSelector("a#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

        driver.findElement(By.cssSelector("i.icon-video-play")).click();

        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.mwEmbedKalturaIframe")));

        Thread.sleep(5000);

        driver.findElement(By.cssSelector("a.icon-play")).click();

        String playTime = driver.findElement(By.cssSelector("div[data-plugin-name='currentTimeLabel']")).getText();

        System.out.println(playTime);

        Assert.assertTrue(playTime != null);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
