import org.junit.runner.RunWith
import io.cucumber.junit.{Cucumber, CucumberOptions}

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array("src/test/resources/features"))
class RunCucumberTest
