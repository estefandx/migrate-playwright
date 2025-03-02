package com.epam.mentoring.taf.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.epam.mentoring.taf.steps",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumbertRunnerTest extends AbstractTestNGCucumberTests {
}