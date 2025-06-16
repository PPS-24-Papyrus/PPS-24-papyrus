package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.captionElement.Image
import io.github.iltotore.iron.autoRefine
import papyrus.logic.Renderer.*

class ImageTest extends AnyFunSuite with Matchers:

  test("Image should render correctly with a valid file"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", Some("Caption"), None, None)
    val rendered = image.render.string

    rendered should include("src=\"file:///")
    rendered should include("alt=\"A German Shepherd\"")
    rendered should include("<figcaption>Caption</figcaption>")


  test("Image should fail to render when file does not exist"):
    val invalidImagePath = "src/test/resources/NonExistentFile.png"
    val image = Image(invalidImagePath, "Non-existent image", None, None, None)
    val rendered = image.render.string

    rendered should include("Error loading image")
    rendered should include("File not found or invalid")


  test("Image should fail to render when file format is invalid"):
    val invalidFormatPath = "src/test/resources/image/InvalidFile.txt"
    val image = Image(invalidFormatPath, "Invalid format", None, None, None)
    val rendered = image.render.string

    rendered should include("Error loading image")
    rendered should include("Invalid forma")


  test("Image should render without a caption when caption is not provided"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, None, None)
    val rendered = image.render.string

    rendered should include("src=\"file:///")
    rendered should include("alt=\"A German Shepherd\"")
    rendered should not include "<figcaption>"


  test("Image should render style with width and alignment"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, Some(300), Some("left"))
    val renderedStyle = image.renderStyle.string

    renderedStyle should include(".cls-")
    renderedStyle should include("width: 300px;")
    renderedStyle should include("float: left;")
    renderedStyle should include("text-align: center;")

  test("Image should render style with only width"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, Some(500), None)
    val renderedStyle = image.renderStyle.string

    renderedStyle should include(".cls-")
    renderedStyle should include("width: 500px;")
    renderedStyle should include("height: auto;")
    renderedStyle should not include "float:"

  test("Image should render style with only alignment"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, None, Some("right"))
    val renderedStyle = image.renderStyle.string

    renderedStyle should include(".cls-")
    renderedStyle should include("float: right;")
    renderedStyle should include("text-align: center;")
    renderedStyle should not include "width:"

  test("Image should render empty style when no width or alignment is provided"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, None, None)
    val renderedStyle = image.renderStyle.string

    renderedStyle shouldBe empty
