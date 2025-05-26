import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("src/test/resources/features"),
  glue = Array("steps"),
  plugin = Array("pretty", "html:target/cucumber-report.html")
)
class RunCucumberTest