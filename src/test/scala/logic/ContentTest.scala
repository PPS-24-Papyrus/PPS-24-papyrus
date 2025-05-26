package logic

import org.scalatest.funsuite.AnyFunSuite
import papyrus.logic.content.Content
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.layerElement.text.Title
import io.github.iltotore.iron.autoRefine

class ContentTest extends AnyFunSuite:

  test("Content should render title correctly") 
    val content = Content(Title("Test Title", 1))
    assert(content.render.contains("<h1>Test Title</h1>"))
  