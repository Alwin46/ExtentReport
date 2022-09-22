import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class extentReport {

    WebDriver driver;
    ExtentReports reports;
    ExtentSparkReporter sparkReporter;
    ExtentTest test;

    @BeforeSuite
    public void start()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        reports=new ExtentReports();
        sparkReporter=new ExtentSparkReporter("report.html");
        reports.attachReporter(sparkReporter);
    }

    @Test
    public void verifyGoogle()
    {
        test=reports.createTest("Verify_Google_Title");
        driver.navigate().to("http://www.google.com");
        test.info("driver is navigate to google");

        String title=driver.getTitle();
        test.info("getting title and verifying");

        Assert.assertEquals(title,"Google");
        test.info("title is verified");

       if (title.equals("Google"))
       {
           test.pass("The title is similar to google");
           System.out.println("this is similar");
       }
       else
       {
           test.fail("The title is not similar to google");
           System.out.println("not similar");
       }


       WebElement search=driver.findElement(By.name("q"));
       test.info("locate google search engine");
       search.sendKeys("game of thrones");
       test.info("search 'game of thrones'");


       List<WebElement> suggestions=driver.findElements(By.xpath("//*[@role=\"listbox\"]//li"));
       test.info("location the suggestions'");
       test.info("get the values iteratively and print that");
       for (WebElement webElement:suggestions)
       {
           String values=webElement.getText();
           System.out.println("suggestions :"+values);
       }

    }

    @AfterSuite
    public void TearDown()
    {
        reports.flush();
        driver.quit();
    }



}
