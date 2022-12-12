package com.nisum.samplesite;

import com.napt.framework.ui.interactions.Element;
import com.napt.framework.ui.runner.EnvVariables;
import com.napt.framework.ui.runner.WebDriverManager;
import com.napt.framework.ui.utils.StepUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class DemoSite {

    WebDriver       driver              = WebDriverManager.getDriver();
    Integer         shortWaitSeconds    = Integer.parseInt(EnvVariables.getEnvVariables().get("explicitShortWait")!=null?EnvVariables.getEnvVariables().get("explicitShortWait"):"10");
    Integer         longWaitSeconds     = Integer.parseInt(EnvVariables.getEnvVariables().get("explicitLongWait")!=null?EnvVariables.getEnvVariables().get("explicitLongWait"):"30");

    AppiumDriver    appiumDriver;
    String testType;
    public DemoSite(){
        testType = EnvVariables.getEnvVariables().get("testType");

        System.out.println("TestType: " + testType);
        if(testType.equalsIgnoreCase("mobileAppInstalled")){
            appiumDriver = (AppiumDriver<MobileElement>) driver;
        }else{
            appiumDriver = null;
        }

    }



    WebDriverWait shortWait = new WebDriverWait(driver,shortWaitSeconds);
    WebDriverWait longWait = new WebDriverWait(driver,longWaitSeconds);

    public void swipeScreen(Direction dir) {
        System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = driver.manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            new TouchAction((PerformsTouchActions) driver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    @Given("that I navigate SauceDemo Home")
     public void iNavigateToSauceDemoHome(){
        String URL = EnvVariables.envVariables.get("WebURL");
        driver.get(URL);
    }

    @Then("I verify that I have landed correctly on the page")
    public void iVerifyHomePage(){
        Assert.assertTrue(StepUtils.onPage("saucehome"));
    }


    @When("I login as {string}")
    public void iLoginToSauceDemo(String user) throws InterruptedException {
        Thread.sleep(2000);
        if(testType.equalsIgnoreCase("mobileAppInstalled")) {
            appiumDriver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(user);
            appiumDriver.findElement(By.xpath("//*[@id='password']")).sendKeys("secret_sauce");
            appiumDriver.findElement(By.xpath("//*[@id='login-button']")).click();
        }else{
            Element.findElement("saucehome.textBox_userId").sendKeys(user);
            Element.findElement("saucehome.textBox_password").sendKeys("secret_sauce");
            Element.findElement("saucehome.button_login").click();
        }
    }

    @Then("I verify that I have logged in successfully")
    public void iVerifySuccessfulLogin(){
        Assert.assertTrue(StepUtils.onPage("sauceAllitems"));
    }


    @When("I select {string}")
    public void iSelectItemByName(String itemName){
        Element.findElement(Element.paramElement("sauceAllitems.li_inventoryItem",itemName)).click();

    }

    @When("I select a non-existent item {string}")
    public void iSelectNonExistentItemByName(String itemName){
        try{
            Element.findElement(Element.paramElement("sauceAllitems.li_inventoryItem",itemName)).click();
        }catch(Exception e){
            //System.out.println(e.getMessage());
        }


    }

    @Then("I can see the item detail page for {string}")
    public void iSeeItemDetailPageForItem(String itemName){
        Assert.assertTrue(StepUtils.onPage("sauceItemDetailPage"));
        Assert.assertTrue(Element.findElement(Element.paramElement("sauceItemDetailPage.itemName",itemName)).isDisplayed());
    }

    @Then("I am not navigated to Item Details Page")
    public void iSeeItemDetailPageForItem(){
        Assert.assertFalse(StepUtils.onPage("sauceItemDetailPage"));

    }


    @When("I add the item to cart")
    public void iAddItemToCart(){
        if(StepUtils.MEW() && !StepUtils.iOS()){
            swipeScreen(Direction.DOWN);
            swipeScreen(Direction.DOWN);
            Element.findElement("sauceItemDetailPage.buttonAddToCart").click();
        }else{
            Element.findElement("sauceItemDetailPage.buttonAddToCart").click();
        }

    }

    @Then("I can see the item being added in the cart {string}")
    public void iViewCart(String itemName){
        if(StepUtils.MEW() && !StepUtils.iOS()){
            swipeScreen(Direction.UP);
            swipeScreen(Direction.UP);
            Element.findElement("header.buttonViewCart").click();
        }else {
            Element.findElement("header.buttonViewCart").click();
            Assert.assertTrue(Element.findElement(Element.paramElement("viewcart.itemInCart", itemName)).isDisplayed());
        }
    }

    @When("I checkout the item")
    public void checkoutItem(){
        Element.findElement("viewcart.buttonCheckout").click();
    }

    @Then("I verify that I am on the checkout page")
    public void checkoutPage(){
        Assert.assertTrue(StepUtils.onPage("checkout"));

    }

    @When("I fill up firstName as {string} and lastName as {string} and zipcode as {string} and continue")
    public void iFillCheckoutDetails(String firstName, String lastName, String zipCode) {
        Assert.assertTrue(StepUtils.onPage("checkout"));
        Element.findElement("checkout.textboxFirstName").sendKeys(firstName);
        Element.findElement("checkout.textboxLastName").sendKeys(lastName);
        Element.findElement("checkout.textboxZipCode").sendKeys(zipCode);
        Element.findElement("checkout.buttonContinue").click();

    }

    @Then("I can see the item details for item {string} on overview page")
    public void iSeeItemDetailsOnOverviewPage(String itemName){
        Assert.assertTrue(StepUtils.onPage("overview"));
        Assert.assertTrue(Element.findElement(Element.paramElement("overview.itemName",itemName)).isDisplayed());
    }

    @When("I click on the finish button")
    public void iClickFinish(){
        Element.findElement("overview.buttonFinish").click();
    }


    @Then("I verify that the order is placed successfully")
    public void iVerifyOrderPlacedSuccessfully(){
        Assert.assertTrue(StepUtils.onPage("checkoutcomplete"));
        Assert.assertTrue(Element.findElement("checkoutcomplete.thankyou").isDisplayed());
    }

    @When("I switch to the {string} context")
    public void iswitchContext(String contextName){
        Set<String> contexts = appiumDriver.getContextHandles();
        for(String s:contexts){
            System.out.println(s);
        }
        System.out.println("Setting context to " + contextName);
        appiumDriver.context(contextName);
    }

    @When("I type the url {string}")
    public void iTypeURL(String webURL){
        By bySearchBar = By.id("com.android.chrome:id/search_box_text");
        WebElement weSearchBar = driver.findElement(bySearchBar);
        weSearchBar.click();

        By byURLBar = By.id("com.android.chrome:id/url_bar");
        WebElement weURLBar = driver.findElement(byURLBar);
        weURLBar.sendKeys(webURL);
        ((AndroidDriver)driver).pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
    }

    @When("I click {string} button of browser")
    public void iclickButton(String browserAction){
        if(browserAction.equalsIgnoreCase("back")){
            ((AndroidDriver)appiumDriver).pressKey(new KeyEvent(AndroidKey.BACK));
        }

        By byMenuButton = By.id("com.android.chrome:id/menu_button");
        WebElement weMenuButton = driver.findElement(byMenuButton);


    }

    @When("I click on the Menu and select {string} >> {string}")
    public void iSelectMenuItem(String itemType, String menuItem) throws InterruptedException {
        Thread.sleep(2000);
        appiumDriver.findElement(Element.element("appChromeHome.menuButton")).click();

        if(itemType.equalsIgnoreCase("MenuIcon")){
            appiumDriver.findElement(Element.paramElement("appChromeMenu.menuIcon",menuItem)).click();
        }else if(itemType.equalsIgnoreCase("MenuItem")){
            appiumDriver.findElement(Element.paramElement("appChromeMenu.menuItem",menuItem)).click();
        }
    }

    @Then("I validate that the website is bookmarked")
    public void iValidateWebsiteIsBookmarked(){
        Assert.assertTrue(EnvVariables.getEnvVariables().get("WebURL").toLowerCase().equals("https://" + appiumDriver.findElement(Element.element("appChromeMenu.bookmark")).getText()));
    }

}
