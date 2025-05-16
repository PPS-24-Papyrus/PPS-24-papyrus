
import papyrus.DSL.PapyrusElement.*

@main def run(): Unit =
  val doc = papyrus(
    metadata(
      List()
    ),
    content(
      List(
        text("body content"),
        image("image.png", "An image")
      )
    )
  )

  println(doc.render)

