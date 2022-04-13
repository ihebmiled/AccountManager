package org.account.manager.bdd.account;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/org/sg.account/account.feature"
        },
        plugin = {"pretty", "html:target/cucumber"},
        strict = true,
        glue = "org.account.manager.bdd.account")
public class AccountTest {
}
