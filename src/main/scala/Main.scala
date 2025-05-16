
import HtmlConverter.HtmlLauncher
import papyrus.DSL.PapyrusElement.*

@main def run(): Unit =
  val doc = papyrus(
    metadata(
      List()
    ),
    content(
      List(
        text("body content"),
        text("body content2"),
        image("image.png", "An image")
      )
    )
  )

  val output = doc.render
  println(output)
  HtmlLauncher.launch(output, "Titolo")

