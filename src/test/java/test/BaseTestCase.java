package test;

import com.friendly.aqa.database.DataBase;
import com.friendly.aqa.pageobject.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTestCase extends TestBase {

    @BeforeClass
    public static void init() {
        initProperties();
        initDriver();
        DataBase.connectDb(props.getProperty("db_url"), props.getProperty("db_user"), props.getProperty("db_password"));
    }

    protected LoginPage loginPage() {
        return new LoginPage(driver);
    }

    protected GroupUpdatePage groupUpdatePage() {
        return new GroupUpdatePage(driver);
    }

    @AfterClass
    public static void tearDownMethod() {
        if (driver != null)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        {
            driver.quit();
            DataBase.disconnectDb();
        }
    }
}
