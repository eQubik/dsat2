# DSAT
## Data Structured Automation Testing
DSAT is a toolkit that facilitates an approach to test automation development by combining key testing frameworks and tools into a convenient wrapper. In DSAT, I focused on moving away from traditional structures for building UI, API, and other types of test automation (such as POM), towards flexibility. If previously you had to create myriad classes and test methods, and then painfully maintain them, now all you need to do is describe your "elements" in the desired data structure (hence the name Data Structured Automated Testing), assigning them a specific type of action, and then use them in your test scenario. Supporting your tests becomes much simpler with this approach, and the barrier to entry into automation is lowered to the ability to use a notepad.

Since the initial release (https://github.com/Emilvasily/dsat), which was more like a preview of the complete version, I've been actively involved in developing and enhancing DSAT. It was hard to imagine how many interesting and helpful functionality I could serve into a single tool. Now, I have a development plan, and I intend to regularly add new features that expand its capabilities. Soon, I will add support for Playwright, and Appium and WireMock are also on the roadmap. Additionally, my main priority at the moment is to make it easy to integrate into your CI. Being in the process of development, I improve the quality of the code day by day, mastering the features of Java(I've made a lot of progress while working on DSAT), and I look forward to your suggestions on this part.

## Getting started

Before getting started I want to mention, that at this moment, if you decide to use it at the daily basis, DSAT can require manual configurations and some adaptation from you. Of course, it already has basic functionality (like Click, SendKeys, etc. (see StaticVariables.action() method) for Selenium tests and for RestAssured tests(GET, POST, etc), but if you need something specific, you need to directly add/edit/delete existing parts of the code. I will make it much more flexible and scalable as soon as I can :)

1. Clone the repository if you haven't cloned it yet
2. Open the project in your favorite IDE and wait for dependencies to download
3. Inside com.equbik package you can find two classes Launcher and DSAT. The first one was made as an entry point, so you can start the tests from your command line. You can make it to start in other way (In other fork, I use an API calls to start the Suite), so navigate to DSAT class
4. DSAT is the main class of the framework. It creates an instance of the SuiteActionPerform class, which provides us with the parsed Scenarios, Adapter implementation, Executors map and Environment config. It also can create a Suite results class and make a JSON output 

**SuiteActionPerform**\
Used to parse the environment config and scenarios. It provides us with:
1. An Environment config instance
2. The Executors map, where the key is the name of the executor(e.g. selenium.local)
3. An AdapterConfig implementation
4. The list of the Scenarios in the Suite

When the DSAT instance is created, we have\
`private final Environment environment;`\
`private final HashMap<String, Executor> executors;`\
`private final Map<Scenario, Adapter> scenariosElements;`
initiated.\
Here `scenariosElements` is the Map, that contains Scenarios and their Elements as its Entry.
So, when the `performSuite()` is called, it will create a ScenarioActionPerform instance to perform each scenario.

# ...
Please come back later :)