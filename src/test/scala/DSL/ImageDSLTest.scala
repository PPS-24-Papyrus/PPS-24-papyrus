package DSL

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.DSL.builders.ImageBuilder
import papyrus.DSL.DSL.given_Conversion_String_ImageBuilder
import papyrus.DSL.builders.ImageBuilder.*
import io.github.iltotore.iron.autoRefine

class ImageDSLTest extends AnyFunSuite with Matchers:

  private def image(init: ImageBuilder ?=> ImageBuilder): ImageBuilder =
    val builder = init(using ImageBuilder())
    builder

  test("Image DSL should create an image with caption and alternative text"):
    val img = image:
      "src/test/resources/image/Pastore-tedesco.png" caption "A German Shepherd" alternative "A beautiful dog"
    val render = img.build.render.string
    render should include("alt=\"A beautiful dog\">")
    render should include("<figcaption>A German Shepherd</figcaption>")

  test("Image DSL should create an image with width and alignment"):
    val img = image:
      "src/test/resources/image/Pastore-tedesco.png" width 300 alignment "left"
    val render = img.build.renderStyle.string
    render should include("width: 300px;")
    render should include("float: left;")

  test("Image DSL should check invalid image format"):
    val img = image:
      "src/test/resources/image/InvalidFile.txt"
    val render = img.build.render.string
    render should include("Invalid format")
    render should include("Error loading image")

  test("Image DSL should check not found image file"):
    val img = image:
      "image.png"
    val render = img.build.render.string
    render should include("File not found or invalid: ")