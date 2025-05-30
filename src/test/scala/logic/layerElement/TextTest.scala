package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.{should, shouldEqual}
import papyrus.logic.styleObjects.TextStyle
import io.github.iltotore.iron.autoRefine
import org.scalatest.matchers.must.Matchers.not
import papyrus.logic.layerElement.text.Text

class TextTest extends AnyFunSuite:

  test("Text should render text correctly"):
    val text: Text = Text("Sample Text")(TextStyle())
    text.render shouldEqual "<p class=\"cls-3E8\">Sample Text</p>"

  test("Text should render correct style when style is provided"):
    val textStyle = TextStyle(fontStyle = "italic", color = "blue")
    val text = Text("Styled Text")(textStyle)
    text.renderStyle shouldEqual ".cls-3E9 {\n  color: blue; font-weight: none; font-style: italic; text-decoration: none;\n}"

  test("Text should generate unique CSS class for each instance"):
    val text1 = Text("Text 1")(TextStyle())
    val text2 = Text("Text 2")(TextStyle())
    text1.render should not equal text2.render  
  