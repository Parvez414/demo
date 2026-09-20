# HealQA Enterprise AI Test Automation Starter Suite

Welcome **parvez** from **DEMO**!
Your workspace evaluation license is active until **2026-10-20 14:10:43.0**.

## 🚀 1-Minute Quickstart (Zero Configuration)

This project comes pre-bundled with the **HealQA AI Core SDK** inside `.repo/`.
You do **not** need a GitHub Personal Access Token or `~/.m2/settings.xml`.

### Prerequisites
- Java JDK 17 or higher (`java -version`)
- Apache Maven 3.8+ (`mvn -version`)
- Google Chrome browser

### Execute Tests
Run the test suite directly from this directory:
```bash
mvn clean test
```

---

## 📖 Core SDK Developer Reference

### 1. Page Object & AI Element Registration (`BasePage`)
All page objects inherit from `com.automation.pages.BasePage`. Register elements inside `initElements()` using semantic descriptions that allow the AI self-healing engine to autonomously recover locators when UI updates occur:

```java
public class ConsumerLoginPage extends BasePage {
    public ButtonComponent loginBtn;

    public ConsumerLoginPage() {
        super("ConsumerLoginPage"); // Page identifier for healing logs and telemetry
    }

    @Override
    protected void initElements() {
        // register(key, semanticDescription, By locator)
        register("username", "Username text input field", By.name("username"));
        register("password", "Password text input field", By.name("password"));
        register("loginBtn", "Login submit button", By.cssSelector("button[type='submit']"));

        // Wrap element as typed SDK UI Component
        loginBtn = initComponent(ButtonComponent.class, getElement("loginBtn"));
    }
}
```

### 2. Supported Selenium & 3-Tier Resilient Operations
Interactions are executed through the **3-Tier Resilient Action Engine**, which automatically falls back to alternative interaction strategies before failing:

| Operation | Method | Resilient 3-Tier Fallback Pipeline |
| :--- | :--- | :--- |
| **Click** | `click(getElement("btn"))` or `ElementActions.click(By)` | **Tier 1**: Auto-scroll + Native click &rarr; **Tier 2**: Actions API `moveToElement` + `click` &rarr; **Tier 3**: JavaScript Event Dispatch |
| **Type** | `sendKeys(getElement("txt"), val)` | **Tier 1**: Auto-scroll + Native clear & type &rarr; **Tier 2**: Actions API (Ctrl+A &rarr; Backspace &rarr; type) &rarr; **Tier 3**: JavaScript value injection + synthetic input/change/blur dispatch |
| **Read Text** | `getText(getElement("header"))` | **Tier 1**: `element.getText()` &rarr; **Tier 2**: `value` attribute &rarr; **Tier 3**: `textContent` &rarr; **Tier 4**: `innerText` / placeholder |
| **Visibility** | `isDisplayed(getElement("el"))` | Safe boolean check without throwing `NoSuchElementException` |
| **Wait** | `waitForVisibility(el)` / `waitForClickable(el)` | Explicit polling against dynamic DOM updates & hydration |
| **Mouse Actions** | `ActionUtils.hover(el)`, `doubleClick(el)`, `rightClick(el)`, `dragAndDrop(src, tgt)` | High-level user interaction sequences with JavaScript fallback |
| **Navigation** | `ElementActions.navigateToUrl(url)` | Universal page hydration wait with automatic self-recovery reload if rendered blank |

### 3. Thread-Safe Cross-Step State Sharing (`ScenarioContext`)
Because Cucumber step instances are stateless and test scenarios execute in parallel, never use class static fields to pass state. Use `ScenarioContext` (backed by `ThreadLocal` and automatically cleared after each scenario by `SeleniumHooks`):

```java
// Step 1: Store dynamic state generated during execution
@Given("I generate a dynamic employee profile")
public void generateData() {
    String name = DataGenerator.randomFullName();
    ScenarioContext.set("employeeName", name);
}

// Step 2: Retrieve the state in a subsequent step definition
@When("I search for the employee by name")
public void searchEmployee() {
    String name = ScenarioContext.getString("employeeName");
    directoryPage.search(name);
}
```

### 4. JSON Test Data Management (`TestDataManager`)
Read structured test data directly from JSON without creating POJO classes. Loads from `src/test/resources/data/{env}/*.json` with fallback to `src/test/resources/data/*.json`:

```java
// Load dynamic TestData container
TestData profile = TestDataManager.getData("user-profiles.activeAdmin");
String role = profile.getString("role");
String status = profile.getString("status");

// Direct dot-notation value access
String email = TestDataManager.getString("user-profiles.activeAdmin.email");

// Dynamic synthetic token replacement
// Values like {{random_email}}, {{timestamp}}, {{uuid}} are automatically populated!
```

### What This Suite Validates Out-of-the-Box
1. **Standard Automation Flow (`ConsumerLogin.feature`)**: Application navigation, element registration, and dashboard validation.
2. **Autonomous AI Locator Recovery (`ConsumerAISelfHealing.feature`)**: Uses a deliberately broken locator ID (`invalid_broken_consumer_user_input_99999`) and auto-recovers the element in real-time.
3. **Data-Driven & State Sharing Flow (`ConsumerDataDriven.feature`)**: Loads dynamic criteria from `user-profiles.json`, stores state in `ScenarioContext`, and validates across steps.
4. **Real-Time Telemetry Streaming**: Test results and self-healing telemetry stream to your platform Web Control Center.
5. **CI/CD Ready**: Includes pre-configured GitHub Actions pipeline (`.github/workflows/test.yml`) for automated headless test execution in GitHub.
