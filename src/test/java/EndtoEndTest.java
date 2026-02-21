import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert; // This is the new import for assertions
import org.testng.annotations.*;

public class LoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=SafeBrowsingPasswordCheck");

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void fullPurchaseTest() throws InterruptedException {
        // 1. Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 2. Add Backpack to Cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        // 3. Checkout Flow
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Sarada");
        driver.findElement(By.id("last-name")).sendKeys("Amarakoon");
        driver.findElement(By.id("postal-code")).sendKeys("11500");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();

        // --- NEW STEP: THE ASSERTION ---
        // This finds the "Thank you for your order!" text on the screen
        String actualMessage = driver.findElement(By.className("complete-header")).getText();
        String expectedMessage = "Thank you for your order!";

        // This compares the two. If they match, the test passes green.
        Assert.assertEquals(actualMessage, expectedMessage);

        System.out.println("Assertion Passed: Success message found!");
        // -------------------------------

        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        // driver.quit();
    }
}