# DSAT
## Data Structured Automation Testing
DSAT is a toolkit that facilitates an approach to test automation development by combining key testing frameworks and tools into a convenient wrapper. In DSAT, I focused on moving away from traditional structures for building UI, API, and other types of test automation (such as POM), towards flexibility. If previously you had to create myriad classes and test methods, and then painfully maintain them, now all you need to do is describe your "elements" in the desired data structure (hence the name Data Structured Automated Testing), assigning them a specific type of action, and then use them in your test scenario. Supporting your tests becomes much simpler with this approach, and the barrier to entry into automation is lowered to the ability to use a notepad.

Since the initial release (https://github.com/Emilvasily/dsat), which was more like a preview of the complete version, I've been actively involved in developing and enhancing DSAT. It was hard to imagine how much interesting and helpful functionality I could serve into a single tool. Now, I have a development plan, and I intend to regularly add new features that expand its capabilities. Soon, I will add support for Playwright, and Appium and WireMock are also on the roadmap. Additionally, my main priority at the moment is to make it easy to integrate into your CI. Being in the process of development, I improve the quality of the code day by day, mastering the features of Java (I've made a lot of progress while working on DSAT), and I look forward to your suggestions on this part.
## Getting started

Before getting started, I want to mention that at this moment, if you decide to use DSAT on a daily basis, it may require manual configurations and some adaptation from you. Of course, it already has basic functionality (such as Click, SendKeys, etc. - see the StaticVariables.action() method) for Selenium tests and for RestAssured tests (GET, POST, etc.), but if you need something specific, you'll need to directly add/edit/delete existing parts of the code. I will make it much more flexible and scalable as soon as I can :)

1. Clone the repository if you haven't cloned it yet.
2. Open the project in your favorite IDE and configure the dependencies to download.
3. Inside the com.equbik package, you can find two classes: Launcher and DSAT. The first one was created as an entry point, allowing you to initiate the tests from the command line. You can configure it to start in other ways (in another fork, I use API calls to start the suite), so navigate to the DSAT class. 

![Alt text](src/main/resources/Scheme.png?raw=true "Scheme")

**DSAT** is the main class of the framework. It creates an instance of the SuiteActionPerform class, which provides us with the parsed Scenarios, Adapter implementation, Executors map, and Environment config. It also creates a Suite results class and generates JSON output.

**SuiteActionPerform**\
It's used to parse the environment config and scenarios. It provides us with an Environment instance, the Executors map (where the key is the name of the executor, e.g., selenium.local), an AdapterConfig implementation, and the list of Scenarios in the Suite.

When the **DSAT** instance is created, we have an Environment instance, executors map, and Scenario - Adapter map, which contains Scenarios and their Elements as its Entry. So, when the `performSuite()` method is called, it will create a ScenarioActionPerform instance to perform each scenario (only one at once at this moment).

**ScenarioActionPerform**\
It prepares steps for execution by creating a steps list. For each step, it creates a new StepActionPerform instance and provides it with its steps and variables. It also creates an Execution implementation according to the provided Executor (Selenium and RestAssured at the moment). Additionally, it can perform `postJobs()`, such as closing the browser.

**StepActionPerform**\
It configures each element (if needed) depending on its actionType and provided variables before execution.

**ElementActionPerform**\
It's used to invoke the necessary Action's `takeAction()` method depending on the Execution implementation.

4. You can start the Suite by providing an environment JSON config and Scenarios you want to test. Examples will be provided separately, and I will share the link here.
5. If you call DSAT's `getSuiteResult()` method, execution will generate "results" folder where you will find the JSON file represents the result of the Suite execution.

Due to the high workload, writing a comprehensive documentation takes me a long time. I'm also working on a series of video tutorials to make it easier for you to start working with the DSAT.
So, please stay connected and forget to follow me:
https://www.linkedin.com/in/emilvas/