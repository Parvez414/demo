package com.mycompany.tests.stepdefinitions;

import com.automation.config.ConfigReader;
import com.automation.users.User;
import com.automation.users.UserManager;
import com.automation.utils.ElementActions;
import com.automation.utils.Log;
import com.mycompany.tests.pages.ConsumerDashboardPage;
import com.mycompany.tests.pages.ConsumerLoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ConsumerLoginSteps {

    private final ConsumerLoginPage loginPage = new ConsumerLoginPage();
    private final ConsumerDashboardPage dashboardPage = new ConsumerDashboardPage();
    private User currentUser;

    @Given("I acquire a consumer user with role {string}")
    public void acquireConsumerUser(String role) {
        currentUser = UserManager.getUser(role);
        Log.info("Leased test user: " + currentUser.username());
    }

    @When("I navigate to the application portal")
    public void navigateToPortal() {
        String baseUrl = ConfigReader.get("base.url");
        ElementActions.navigateToUrl(baseUrl);
    }

    @When("the AI pre-flight agent validates consumer page elements")
    public void validateConsumerPageElements() {
        try {
            com.automation.utils.WaitUtils.waitForPresence(org.openqa.selenium.By.cssSelector("input[name='password'], input[type='password']"), 15);
        } catch (Exception ignored) {}
        loginPage.validateAndHealPageElements();
    }

    @When("I perform login using consumer credentials")
    public void performLogin() {
        loginPage.login(currentUser.username(), currentUser.password());
    }

    @Then("I should see the dashboard loaded successfully")
    public void verifyDashboard() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard failed to load!");
        Log.info("Dashboard successfully validated!");
    }
}
