package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.Listing
import papyrus.logic.layerElement.text.Item
import io.github.iltotore.iron.autoRefine


class ListingTest extends AnyFunSuite with Matchers:

  test("Item renders correctly with simple string") {
    val item = Item("Example")
    item.render.string shouldBe "<li>Example</li>"
    item.renderStyle.string shouldBe ""
  }

  test("Empty item renders as empty <li>") {
    val item = Item("")
    item.render.string shouldBe "<li></li>"
  }

  test("Listing renders empty unordered list") {
    val listing = Listing("ul")
    listing.render.string shouldBe "<ul>\n\n</ul>"
    listing.renderStyle.string shouldBe ""
  }

  test("Listing renders unordered list with one item") {
    val item = Item("Uno")
    val listing = Listing("ul", item)
    listing.render.string shouldBe "<ul>\n<li>Uno</li>\n</ul>"
  }

  test("Listing renders ordered list with multiple items") {
    val items = Seq(Item("Primo"), Item("Secondo"))
    val listing = Listing("ol", items*)
    listing.render.string shouldBe "<ol>\n<li>Primo</li>\n<li>Secondo</li>\n</ol>"
  }

  test("Listing can contain other listings (nested)") {
    val sublist = Listing("ul", Item("a"), Item("b"))
    val listing = Listing("ul", Item("main 1"), sublist, Item("main 2"))
    listing.render.string shouldBe
      "<ul>\n<li>main 1</li>\n<ul>\n<li>a</li>\n<li>b</li>\n</ul>\n<li>main 2</li>\n</ul>"
  }
