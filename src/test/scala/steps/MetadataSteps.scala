package steps

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.Assertions._

class MetadataSteps extends ScalaDsl with EN {

  Given("""I create a Metadata empty""") { () =>
    succeed
  }

  Given("""I create a new Metadata""") { () =>
    succeed
  }

  Given("""I create a new Papyrus document""") { () =>
    succeed
  }

  Given("""I set the metadata:""") { (data: Map[String, String]) =>
    succeed
  }

  Given("""I define a content block with title {string}""") { (title: String) =>
    succeed
  }

  When("""I render the Metadata""") { () =>
    succeed
  }

  When("""I set the Metadata:""") { (data: Map[String, String]) =>
    succeed
  }

  When("""I try to set the metadata again:""") { (data: Map[String, String]) =>
    succeed
  }

  When("""I try to define another content block with title {string}""") { (title: String) =>
    succeed
  }

  Then("""The result should be an default Metadata structure""") { () =>
    succeed
  }

  Then("""The metadata should contain:""") { (data: Map[String, String]) =>
    succeed
  }

  Then("""The system should raise an error""") { () =>
    succeed
  }
}
