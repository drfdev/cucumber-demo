package dev.drf.cucumber.demo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "dev.drf.cucumber.demo.steps",
                "dev.drf.cucumber.demo.storage"
        },
        tags = "@all",
//        dryRun = true,
        strict = true,
        snippets = CucumberOptions.SnippetType.UNDERSCORE
)
public class CucumberRunner {
}
