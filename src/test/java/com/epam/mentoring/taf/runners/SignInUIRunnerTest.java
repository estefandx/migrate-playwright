package com.epam.mentoring.taf.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/ui/user_sign_in.feature",
        glue = "com.epam.mentoring.taf.steps",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class SignInUIRunnerTest extends AbstractTestNGCucumberTests {
}