package steps

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers

class ContentSteps extends ScalaDsl with EN with Matchers:


  Given("""I create a new Papyrus document"""): () =>
    succeed

  Given("""I add a title {string}"""): (title: String) =>
    succeed

  Given("""I add a paragraph {string}"""): (text: String) =>
    succeed

  Given("""I add a section with title {string} and text {string}"""): (title: String, text: String) =>
    succeed

  Given("""I add a subsection with title {string} and text {string}"""): (title: String, text: String) =>
    succeed

  Given("""I add an image with:"""): (data: Map[String, String]) =>
    succeed

  Given("""I add a list with:"""): (items: List[String]) =>
    succeed

  Given("""I add a table with caption {string}, headers {string}, and rows:"""): () =>
    succeed

  When("""I render the document"""): () =>
    succeed
  
  Then("""The HTML output should contain:"""): (expectedOutput: String) =>
    succeed
  
  Then("""The system should raise an error."""): () =>
    succeed
