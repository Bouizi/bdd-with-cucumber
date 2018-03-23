package br.custom.bookstore.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.custom.bookstore.steps",
        features = "classpath:features/CalculaPrecoPrazo.feature")
public class RunCalculaPrecoPrazoTest {
}
