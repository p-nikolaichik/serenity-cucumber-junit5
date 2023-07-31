package org.example;

import net.serenitybdd.core.webdriver.enhancers.BeforeAWebdriverScenario;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.SupportedWebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class BeforeWebdriver implements BeforeAWebdriverScenario {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BeforeWebdriver.class);

    @Override
    public MutableCapabilities apply(EnvironmentVariables environmentVariables,
                                     SupportedWebDriver driver,
                                     TestOutcome testOutcome,
                                     MutableCapabilities capabilities) {

        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromeLocalStatePrefs = new HashMap<>();
        List<String> experimentalFlags = new ArrayList<>();
        experimentalFlags.add("same-site-by-default-cookies@2");
        chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);
        options.setExperimentalOption("localState", chromeLocalStatePrefs);
        //accept self-signed certificate
        options.setAcceptInsecureCerts(true);
        //overcome CORS issues with SSL
        options.addArguments("--disable-web-security");
        options.addArguments("--start-maximized");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);

        // This capabilities can be used for selenoid grid
        String featureFileName = "Mock test";
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("name", featureFileName);
        selenoidOptions.put("sessionTimeout", "5m");
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", false);
        capabilities.setCapability("selenoid:options", selenoidOptions);

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        log.info("Capabilities has been set up");
        return capabilities;
    }
}
