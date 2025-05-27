package papyrus.DSL.daCancellare

import scala.annotation.internal.Body

object PapyrusElement:

  trait PapyrusElement:
    def render: String

  trait MetadataElement extends PapyrusElement

  case class MetaTag(name: String, value: String) extends MetadataElement:
    def render: String = s"""<meta name="$name" content="$value">"""

  object MetadataSyntax:
    extension (s: String)
      def title: MetaTag = MetaTag("title", s)
      def author: MetaTag = MetaTag("author", s)
      def description: MetaTag = MetaTag("description", s)

  export MetadataSyntax.*

  trait ContentElement extends PapyrusElement

  private case class Text(content: String) extends ContentElement:
    def render: String = s"<p>${content}</p>"

  private case class Image(src: String, alt: String) extends ContentElement:
    def render: String = s"<img src='${src}' alt='${alt}'/>"

  case class Metadata(content: List[PapyrusElement]) extends PapyrusElement:
    def render: String = s"<head>${content.map(_.render).mkString("\n")}</head>"

  case class Content(content: List[PapyrusElement]) extends PapyrusElement:
    def render: String = s"<body>${content.map(_.render).mkString("\n")}</body>"
    
  private case class PapyrusDocument(meta: Metadata, body: Content) extends PapyrusElement:
    def render: String = """<html>""" + "\n" + meta.render + "\n" + body.render + "\n" + """</html>"""

  given Conversion[String, ContentElement] with
    def apply(str: String): ContentElement = Text(str)

  // DSL entry point
  def papyrus(meta: => Metadata, body: => Content): PapyrusElement =
    PapyrusDocument(
      meta, body
    )

  def metadata(elements: MetadataElement*): Metadata =
    Metadata(elements.toList)

  def content(elements: PapyrusElement*): Content =
    Content(elements.toList)

  def image(src: String, alt: String): ContentElement =
    Image(src, alt)
    
  def text(content: String): ContentElement =
    Text(content)