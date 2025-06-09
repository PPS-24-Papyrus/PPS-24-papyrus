package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.{should, shouldEqual}
import papyrus.logic.styleObjects.TextStyle
import io.github.iltotore.iron.autoRefine
import org.scalatest.matchers.must.Matchers.{be, include, not}
import papyrus.logic.layerElement.text.Text

class TextTest extends AnyFunSuite:

  test("Text should render text correctly"):
    val text: Text = Text("Sample Text")(TextStyle())
    text.render shouldEqual "<p class=\"cls-3E8\">Sample Text</p>"

  test("Text should render correct style when style is provided"):
    val textStyle = TextStyle(fontStyle = "italic", color = "blue")
    val text = Text("Styled Text")(textStyle)
    text.renderStyle shouldEqual ".cls-3E9 {\n  color: blue; font-style: italic;\n}"

  test("Text should render empty text correctly") {
    val text: Text = Text("")(TextStyle())
    text.render shouldEqual "<p class=\"cls-3EA\"></p>"
  }

  test("Text should render correct style with multiple attributes"):
    val textStyle = TextStyle(fontStyle = "italic", color = "red", textDecoration = "underline", fontWeight = "bold")
    val text = Text("Styled Text")(textStyle)
    text.renderStyle shouldEqual ".cls-3EB {\n  color: red; font-weight: bold; font-style: italic; text-decoration: underline;\n}"


  test("Text should render default style when no attributes are provided"):
    val textStyle = TextStyle()
    val text = Text("Default Style Text")(textStyle)
    text.renderStyle shouldEqual ".cls-3EC {\n  \n}"

  test("Text should handle invalid style attributes gracefully"):
    val textStyle = TextStyle(fontStyle = "italic", color = "aqua")
    val text = Text("Invalid Style Text")(textStyle)
    text.renderStyle shouldEqual ".cls-3ED {\n  color: aqua; font-style: italic;\n}"

  test("Text should render correctly with long text"):
    val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " * 10
    val text: Text = Text(longText)(TextStyle())
    text.render should include("Lorem ipsum dolor sit amet")
    text.render.length should be > 100