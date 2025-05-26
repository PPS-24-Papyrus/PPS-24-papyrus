package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import papyrus.logic.layerElement.text.Title
import papyrus.logic.Utility.TypesInline.Level

class TitleTest extends AnyFunSuite:

  test("Title should render correct HTML with level 1"):
    val title = Title("Test Title", 1)
    assert(title.render == "<h1>Test Title</h1>")

  test("Title should render correct HTML with level 3"):
    val title = Title("Another Title", 3)
    assert(title.render == "<h3>Another Title</h3>")

  test("Title should throw an exception for invalid level"):
    assertThrows[IllegalArgumentException]:
      Title("Invalid Title", 7) // Level 7 is outside the valid range (1-6)

  test("Title should render empty style if none is provided"):
    val title = Title("No Style Title", 4)
    assert(title.renderStyle == "")