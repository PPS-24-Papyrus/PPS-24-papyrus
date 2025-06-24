package logic


import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import papyrus.dsl.builders.MetadataBuilder
import io.github.iltotore.iron.autoRefine

class MetadataTest extends AnyFunSuite with Matchers:

  test("MetadataBuilder should create metadata with all attributes"):
    val metadata = MetadataBuilder()
      .withTitle("Title test")
      .withAuthor("luca dany")
      .withCharset("utf-8")
      .withStyleSheet("styletest.css")
      .build

    val render = metadata.render.string

    render should include("""<meta name="title" content="Title test"></meta>""")
    render should include("""<meta name="author" content="luca dany"></meta>""")
    render should include("""<meta name="charset" content="utf-8"></meta>""")
    render should include("""<link rel="stylesheet" href="styletest.css"></link>""")

  test("MetadataBuilder should create default metadata"):
    val metadata = MetadataBuilder().build

    val render = metadata.render.string

    render should include("""<meta name="title" content="My Elegant Document"></meta>""")
    render should include("""<meta name="author" content="Author Name"></meta>""")
    render should include("""<meta name="charset" content="utf-8"></meta>""")
    render should include("""<link rel="stylesheet" href="style.css"></link>""")

  test("MetadataBuilder should override attributes when set multiple times"):
    val metadata = MetadataBuilder()
      .withNameFile("testNameFile")
      .withExtension("pdf")
      .build

    metadata.nameFile shouldBe "testNameFile"
    metadata.extension shouldBe "pdf"

