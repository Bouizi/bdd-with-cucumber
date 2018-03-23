package br.custom.exemplo.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.custom.exemplo.steps",
        features = "classpath:features/Calculadora.feature"
)
public class RunCalculadoraTest {
}

