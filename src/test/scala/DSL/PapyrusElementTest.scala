package DSL

import org.scalatest.funsuite.AnyFunSuite
import papyrus.DSL.daCancellare.PapyrusElement

class PapyrusElementTest extends AnyFunSuite {

  import PapyrusElement._

  test("Try for commit"){
    assert(true)
  }

  test("Text element should render correctly") {
    val t = text("Hello World")
    assert(t.render == "<p>Hello World</p>")
  }

  test("Image element should render correctly") {
    val img = image("image.png", "An image")
    assert(img.render == "<img src='image.png' alt='An image'/>")
  }

  test("Metadata should render list of elements inside <head>") {
    val meta = metadata(title("title"), author("author"))
    assert(meta.render == "<head><meta name=\"title\" content=\"title\">\n<meta name=\"author\" content=\"author\"></head>")
  }

  test("Content should render list of elements inside <body>") {
    val cont = content(text("body content"))
    assert(cont.render == "<body><p>body content</p></body>")
  }
}

