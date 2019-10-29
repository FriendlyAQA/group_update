package test;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;

import java.util.Properties;

public class TestBase {
    static Properties props;
    final static Logger logger;

    static {
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
    }

    protected void softAssert(String result, String... options) {
        boolean matched = false;
        for (String option : options) {
            if (result.equals(option)) {
                matched = true;
                break;
            }
        }
        if (!matched) {
            throw new AssertionError("No option matches to :" + result);
        }
    }
}
