package steps


import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.Assertions._

class PapyrusSteps extends ScalaDsl with EN {

  Given("""I create an empty Papyrus document""") { () =>
    succeed
  }

  Given("""I create a Papyrus document with the text {string}""") { (text: String) =>
    succeed
  }

  Given("""I create a Papyrus document with metadata""") { () =>
    succeed
  }

  Given("""I create a Papyrus document with metadata and the text {string}""") { (text: String) =>
    succeed
  }

  Given("""I create a new Papyrus document""") { () =>
    succeed
  }

  Given("""I define a content block with title {string}""") { (title: String) =>
    succeed
  }

  When("""I render the document""") { () =>
    succeed
  }

  When("""I render the document as PDF""") { () =>
    succeed
  }

  When("""I try to define another content block with title {string}""") { (title: String) =>
    succeed
  }

  Then("""The result should be an empty HTML structure""") { () =>
    succeed
  }

  Then("""The result should an HTML structure contain {string}""") { (html: String) =>
    succeed
  }

  Then("""The result should be a PDF file with the title {string} and the text {string}""") { (title: String, text: String) =>
    succeed
  }

  Then("""The system should raise an error""") { () =>
    succeed
  }
}
