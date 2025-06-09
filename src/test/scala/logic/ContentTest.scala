package logic

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}
import java.util.Optional
import io.github.iltotore.iron.autoRefine

class ContentTest extends AnyFunSuite with Matchers:

  test("Content should render title and layer elements correctly"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Test Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Sample Text")(textStyle)
    val content = Content(Optional.of(title), text)
    content.render shouldEqual "<body><h1>Test Title</h1><p class=\"cls-3E8\">Sample Text</p></body>"

  test("Content should render styles correctly"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Test Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Sample Text")(textStyle)
    val content = Content(Optional.of(title), text)
    content.renderStyle shouldEqual "h1 {\n  font-family: Helvetica; font-size: 24px; color: black; text-align: center;\n}\n.cls-3E9 {\n  color: blue;\n}"

  test("Content should handle empty title and layer elements"):
    val content = Content(Optional.empty(), Seq.empty[LayerElement]: _*)
    content.render shouldEqual "<body></body>"
    content.renderStyle shouldEqual ""

  test("Content should render mixed title and layer elements"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Mixed Content Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Mixed Content Text")(textStyle)
    val content = Content(Optional.of(title), text)
    content.render shouldEqual "<body><h1>Mixed Content Title</h1><p class=\"cls-3EA\">Mixed Content Text</p></body>"