package test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import testRunner.FunctionalTests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.apache.log4j.Logger.getLogger;

public class TestBase {
    protected WebDriver driver;
    protected static Properties props;
    protected final static Logger logger = getLogger(FunctionalTests.class);

    protected void initDriver() {
        String browser;
        browser = props.getProperty("browser");
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty("chrome_driver_path"));
                driver = new ChromeDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", props.getProperty("ie_driver_path"));
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty("edge_driver_path"));
                driver = new EdgeDriver();
                break;
            default:
            case "firefox":
                System.setProperty("webdriver.gecko.driver", props.getProperty("firefox_driver_path"));
                driver = new FirefoxDriver();
        }
        long implWait = Long.parseLong(props.getProperty("driver_implicitly_wait"));
        driver.manage().timeouts().implicitlyWait(implWait, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(props.getProperty("ui_url"));
    }

    protected static void initProperties() {
        props = new Properties();
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            props.load(input);
        } catch (IOException ex) {
            System.out.println("File 'config.properties' is not found!");
            System.exit(1);
        }
    }
}
