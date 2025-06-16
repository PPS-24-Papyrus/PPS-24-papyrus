package logic

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.*

class ContentTest extends AnyFunSuite with Matchers:

  test("Content should render title and layer elements correctly"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Test Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Sample Text")(textStyle)
    val content = Content(Some(title), text)
    content.render.string.replaceAll("""cls-.{3}""", "") shouldEqual s"""<body>\n<h1>Test Title</h1>\n<span class="">Sample Text</span></body>"""

  test("Content should render styles correctly"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Test Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Sample Text")(textStyle)
    val content = Content(Some(title), text)
    content.renderStyle.string.replaceAll("""cls-.{3}""", "") shouldEqual "h1 {\n  font-family: Helvetica; font-size: 24px; color: black; text-align: center;\n}\n. {\n  color: blue;\n}"

  test("Content should handle empty title and layer elements"):
    val content = Content(None, Seq.empty[LayerElement]: _*)
    content.render.string shouldEqual "<body>\n</body>"
    content.renderStyle.string shouldEqual ""

  test("Content should render mixed title and layer elements"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Mixed Content Title", 1)(titleStyle)
    val textStyle = TextStyle(color = "blue")
    val text = Text("Mixed Content Text")(textStyle)
    val content = Content(Some(title), text)
    content.render.string.replaceAll("""cls-.{3}""", "") shouldEqual s"""<body>\n<h1>Mixed Content Title</h1>\n<span class="">Mixed Content Text</span></body>"""