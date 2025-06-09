package logic.layerElement

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.logic.layerElement.captionElement.Image

class ImageTest extends AnyFunSuite with Matchers:

  test("Image should render correctly with a valid file"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", Some("Caption"), None, None)
    val rendered = image.render

    rendered should include("src=\"file:///")
    rendered should include("alt=\"A German Shepherd\"")
    rendered should include("<figcaption>Caption</figcaption>")


  test("Image should fail to render when file does not exist"):
    val invalidImagePath = "src/test/resources/NonExistentFile.png"
    val image = Image(invalidImagePath, "Non-existent image", None, None, None)
    val rendered = image.render

    rendered should include("Error loading image")
    rendered should include("File not found or invalid")


  test("Image should fail to render when file format is invalid"):
    val invalidFormatPath = "src/test/resources/image/InvalidFile.txt"
    val image = Image(invalidFormatPath, "Invalid format", None, None, None)
    val rendered = image.render

    rendered should include("Error loading image")
    rendered should include("Invalid forma")


  test("Image should render without a caption when caption is not provided"):
    val validImagePath = "src/test/resources/image/Pastore-tedesco.png"
    val image = Image(validImagePath, "A German Shepherd", None, None, None)
    val rendered = image.render

    rendered should include("src=\"file:///")
    rendered should include("alt=\"A German Shepherd\"")
    rendered should not include "<figcaption>"
