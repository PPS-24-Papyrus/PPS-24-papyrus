package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.metadata.MetaTag.{MetaTag, authorTag, charsetTag, styleSheetTag, titleTag}
import papyrus.logic.styleObjects.MainStyle

object MetaTag:
  trait MetaTag:
    def name: String
    def content: String
    def render: String

  def titleTag(title: String): MetaTag = MetaTagImpl("title", title)
  def authorTag(author: String): MetaTag = MetaTagImpl("author", author)
  def charsetTag(charset: Charset): MetaTag = MetaTagImpl("charset", charset)
  def styleSheetTag(link: StyleSheet): MetaTag = new MetaTagImpl("stylesheet", link):
    override def render: String = s"""<link rel="stylesheet" href="$content">"""

  private class MetaTagImpl(override val name: String, override val content: String) extends MetaTag:
    override def render: String = s"""<meta name="$name" content="$content">"""

trait Metadata extends Renderer:
  def style: MainStyle
  def nameFile: String
  def language: Language
  def extension: Extension
  def metaTags: Seq[MetaTag]

object Metadata:
  def apply(nameFile: String = "newFile",
            extension: Extension = "html",
            style: MainStyle = MainStyle(),
            language: Language = "en",
            title: String = "index",
            author: String = "Unknown",
            charset: Charset = "utf-8",
            styleSheet: String = "style.css"): Metadata
  = MetadataImpl(nameFile, extension, style, language, Seq(titleTag(title), authorTag(author), charsetTag(charset), styleSheetTag("style.css")))

  private class MetadataImpl(override val nameFile: String,
                             override val extension: Extension,
                             override val style: MainStyle,
                             override val language: Language,
                             override val metaTags: Seq[MetaTag]) extends Metadata:
    override def render: String = s"""<head>
                                     | ${metaTags.map(_.render).mkString("\n")}
                                     |</head>""".stripMargin

    override def renderStyle: String =
      s""".${style.tag} {\n  ${style.renderStyle}\n}"""