package papyrus.DSL

import scala.annotation.internal.Body

object PapyrusElement:

  sealed trait PapyrusElement:
    def render: String

  private case class Text(content: String) extends PapyrusElement:
    def render: String = s"<p>${content}</p>"

  private case class Image(src: String, alt: String) extends PapyrusElement:
    def render: String = s"<img src='${src}' alt='${alt}'/>"

  case class Metadata(content: List[PapyrusElement]) extends PapyrusElement:
    def render: String = s"<head>${content.map(_.render).mkString("\n")}</head>"

  case class Content(content: List[PapyrusElement]) extends PapyrusElement:
    def render: String = s"<body>${content.map(_.render).mkString("\n")}</body>"
    
  private case class PapyrusDocument(meta: Metadata, body: Content) extends PapyrusElement:
    def render: String = """<html>""" + "\n" + meta.render + "\n" + body.render + "\n" + """</html>"""

  given Conversion[String, PapyrusElement] with
    def apply(str: String): PapyrusElement = Text(str)

  // DSL entry point
  def papyrus(meta: => Metadata, body: => Content): PapyrusElement =
    PapyrusDocument(
      meta, body
    )

  def metadata(elements: PapyrusElement*): Metadata =
    Metadata(elements.toList)

  def content(elements: PapyrusElement*): Content =
    Content(elements.toList)

  def image(src: String, alt: String): PapyrusElement =
    Image(src, alt)
    
  def text(content: String): PapyrusElement =
    Text(content)