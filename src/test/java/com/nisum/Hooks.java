package com.nisum;

import com.napt.framework.ui.runner.EnvVariables;
import com.napt.framework.ui.runner.WebDriverManager;
import com.napt.framework.ui.utils.StepUtils;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * Hooks Class
 */
public class Hooks {

    private static final Logger log = Logger.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario sc) throws MalformedURLException {
        log.info("Scenario: " + sc.getName());
        if(!EnvVariables.getEnvVariables().get("testType").toString().toLowerCase().equalsIgnoreCase("API")) {
            WebDriverManager.setDriver2();
            if (!StepUtils.MEW()) {
                WebDriverManager.getDriver().manage().window().maximize();
            }
            WebDriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Capture a selenium screenshot when a test fails and add it to the Cucumber generated report
     * if the scenario has accessed selenium in its lifetime
     *
     * @throws ClassCastException, WebDriverException
     */
    private void screenshotCapture(Scenario sc) {
        if (sc.isFailed() && !EnvVariables.getEnvVariables().get("testType").toString().toLowerCase().equalsIgnoreCase("API")) {
            log.info("sc is Failed: " + sc.isFailed());
            if (WebDriverManager.getDriver() instanceof TakesScreenshot) {
                TakesScreenshot takeScreenshot = (TakesScreenshot) WebDriverManager.getDriver();
                try {
                    byte[] data = takeScreenshot.getScreenshotAs(OutputType.BYTES);
                    sc.embed(data, "image/png");
                    log.info("Screenshot Embed it in the report");
                } catch (ClassCastException cce) {
                    cce.printStackTrace();
                } catch (WebDriverException wde) {
                    System.err.println(wde.getMessage());
                }
            } else {
                log.warn("This web driver implementation cannot create screenshots: " + WebDriverManager.getDriver().getClass().getName());
            }
        }
    }

    @After
    public void afterScenario(Scenario sc) {
        if (!EnvVariables.getEnvVariables().get("testType").toString().toLowerCase().equalsIgnoreCase("API")) {
            try {
                screenshotCapture(sc);
                WebDriverManager.destroyDriver();
                log.info("Web driver quit successful");
            } catch (WebDriverException e) {
                log.warn("Driver could not be reset, please see debug log for more details");
                log.debug(e.getMessage());
            }
        }
    }
}
