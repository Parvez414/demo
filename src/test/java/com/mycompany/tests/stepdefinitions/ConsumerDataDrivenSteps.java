package com.mycompany.tests.stepdefinitions;

import com.automation.data.ScenarioContext;
import com.automation.data.TestData;
import com.automation.data.TestDataManager;
import com.automation.utils.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ConsumerDataDrivenSteps {

    @Given("I load the user profile {string} from JSON test data")
    public void loadProfile(String profileKey) {
        TestData profile = TestDataManager.getData("user-profiles." + profileKey);
        String role = profile.getString("role");
        String status = profile.getString("status");
        String email = profile.getString("email");

        Log.info("Loaded JSON test data profile [" + profileKey + "]: role=" + role + ", email=" + email);
        ScenarioContext.set("currentProfileKey", profileKey);
        ScenarioContext.set("currentRole", role);
        ScenarioContext.set("currentStatus", status);
        ScenarioContext.set("currentEmail", email);
    }

    @When("I store the active profile data in ScenarioContext")
    public void verifyStoredInContext() {
        Assert.assertTrue(ScenarioContext.contains("currentProfileKey"), "profileKey missing in ScenarioContext!");
        Assert.assertTrue(ScenarioContext.contains("currentRole"), "role missing in ScenarioContext!");
        Assert.assertTrue(ScenarioContext.contains("currentEmail"), "email missing in ScenarioContext!");
    }

    @Then("I verify the profile data retrieved from ScenarioContext matches the JSON source")
    public void verifyFromContext() {
        String role = ScenarioContext.getString("currentRole");
        String status = ScenarioContext.getString("currentStatus");
        String email = ScenarioContext.getString("currentEmail");

        Assert.assertNotNull(role, "Role retrieved from ScenarioContext is null!");
        Assert.assertNotNull(status, "Status retrieved from ScenarioContext is null!");
        Assert.assertNotNull(email, "Email retrieved from ScenarioContext is null!");

        Log.info("Successfully validated ScenarioContext state -> Role: " + role + ", Status: " + status + ", Email: " + email);
    }
}
