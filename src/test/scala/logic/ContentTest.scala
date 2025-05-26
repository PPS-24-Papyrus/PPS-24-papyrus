package logic

import org.scalatest.funsuite.AnyFunSuite
import papyrus.logic.Content.Content
import papyrus.logic.layerElement.LayerElement

class ContentTest extends AnyFunSuite:

  test("Content should render title correctly") 
    val content = Content("Test Title")
    assert(content.render.contains("<h1>Test Title</h1>"))
  