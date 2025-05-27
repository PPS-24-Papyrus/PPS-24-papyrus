package papyrus.DSL.daCancellare

import HtmlConverter.HtmlLauncher
import PapyrusElement.*

@main def run(): Unit =
  val doc: PapyrusElement = papyrus(
    metadata(
      "Titolo".title,
      "Luca".title,
      "Mario".author
    ), content(
      "body content",
      "body content2", "ciccio",
      image("image.png", "An image"),
      "lalla"
    )
  )

  val output = doc.render
  println(output)
  //HtmlLauncher.launchHTML(output, "Titolo")

