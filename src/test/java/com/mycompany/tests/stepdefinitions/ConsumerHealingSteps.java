package com.mycompany.tests.stepdefinitions;

import com.automation.ai.PageElement;
import com.automation.users.User;
import com.automation.users.UserManager;
import com.automation.utils.Log;
import com.mycompany.tests.pages.ConsumerHealingDemoPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ConsumerHealingSteps {

    private final ConsumerHealingDemoPage healingPage = new ConsumerHealingDemoPage();
    private User currentUser;

    @When("the AI pre-flight agent inspects and heals broken elements on the Login page")
    public void validateAndHealLoginPage() {
        try {
            com.automation.utils.WaitUtils.waitForPresence(By.cssSelector("input[name='password'], input[type='password']"), 15);
        } catch (Exception ignored) {}
        healingPage.validateAndHealPageElements();
    }

    @Then("the broken username locator should be automatically healed by the AI agent")
    public void verifyUsernameHealed() {
        PageElement brokenUsername = healingPage.getElement("brokenUsername");
        By currentBy = brokenUsername.getCurrentBy();
        Assert.assertNotEquals(currentBy, brokenUsername.getOriginalBy(), "Element was not healed!");
        Log.info("Verified consumer element healed from: " + brokenUsername.getOriginalBy() + " -> " + currentBy);
    }

    @And("I login using the AI-healed elements")
    public void loginWithHealedElements() {
        currentUser = UserManager.getUser("admin");
        healingPage.loginWithHealedElement(currentUser.username(), currentUser.password());
        Log.info("Successfully logged in using AI-healed locator!");
    }
}
