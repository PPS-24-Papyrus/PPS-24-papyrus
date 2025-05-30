import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.text.Title
import io.github.iltotore.iron.autoRefine
import papyrus.logic.styleObjects.TitleStyle

class TitleTest extends AnyFunSuite with Matchers:

  test("Title should render correct HTML with level 1"):
    val titleStyle = TitleStyle(fontSize = 24, textColor = "black")
    val title = Title("Test Title", 1)(titleStyle)
    title.render shouldEqual "<h1 class=\"cls-3E8\">Test Title</h1>"

  test("Title should render correct HTML with level 3"):
    val titleStyle = TitleStyle(font = "Arial", fontSize = 18, textColor = "gray")
    val title = Title("Another Title", 3)(titleStyle)
    title.render shouldEqual "<h3 class=\"cls-3E9\">Another Title</h3>"

  test("Title should render correct style for level 1"):
    val titleStyle = TitleStyle(textColor = "red", fontSize = 24)
    val title = Title("Styled Title", 1)(titleStyle)
    println(title.render)
    title.renderStyle shouldEqual ".cls-3EA {\n  font-family: Georgia; font-size: 24px; color: red; text-align: left;\n}"

  test("Title should render correct style for level 3"):
    val titleStyle = TitleStyle(font = "Arial", fontSize = 18, textColor = "gray")
    val title = Title("Styled Title", 3)(titleStyle)
    title.renderStyle shouldEqual ".cls-3EB {\n  font-family: Arial; font-size: 18px; color: gray; text-align: left;\n}"