package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import io.github.iltotore.iron.autoRefine
import papyrus.utility.DefaultValues
import papyrus.logic.Renderer.Text.*
import papyrus.logic.metadata.MetaTag.{MetaTag, authorTag, charsetTag, styleSheetTag, titleTag}
import papyrus.logic.styleObjects.MainStyle
import papyrus.utility.TypesInline.*

object MetaTag:
  /** Represents an HTML meta or link tag */
  trait MetaTag:
    /** The name or type of the tag (e.g. "author", "charset") */
    def name: String

    /** The tag's content or href */
    def content: String

    /** Renders the tag as HTML */
    def render: MainText

  /** Creates a `<meta name="title" ...>` tag */
  def titleTag(title: String): MetaTag =
    MetaTagImpl("title", title)

  /** Creates a `<meta name="author" ...>` tag */
  def authorTag(author: String): MetaTag =
    MetaTagImpl("author", author)

  /** Creates a `<meta name="charset" ...>` tag */
  def charsetTag(charset: Charset): MetaTag =
    MetaTagImpl("charset", charset)

  /** Creates a `<link rel="stylesheet" ...>` tag */
  def styleSheetTag(link: StyleSheet): MetaTag =
    new MetaTagImpl("stylesheet", link):
      override def render: MainText =
        s"""<link rel="stylesheet" href="$content"></link>""".toMainText

  private class MetaTagImpl(override val name: String, override val content: String) extends MetaTag:
    override def render: MainText = s"""<meta name="$name" content="$content"></meta>""".toMainText

trait Metadata extends Renderer:
  def style: MainStyle
  def nameFile: String
  def language: Language
  def extension: Extension
  def savingPath: String
  def metaTags: Seq[MetaTag]

object Metadata:
  def apply(nameFile: String = DefaultValues.nameFile,
            extension: Extension = DefaultValues.extension,
            savingPath: String = "",
            style: MainStyle = MainStyle(),
            language: Language = DefaultValues.language,
            title: String = DefaultValues.title,
            author: String = DefaultValues.author,
            charset: Charset = DefaultValues.charset,
            styleSheet: StyleSheet = DefaultValues.styleSheet): Metadata
  = MetadataImpl(nameFile, extension, savingPath, style, language, Seq(titleTag(title), authorTag(author), charsetTag(charset), styleSheetTag(styleSheet)))

  private class MetadataImpl(override val nameFile: String,
                             override val extension: Extension,
                             override val savingPath: String,
                             override val style: MainStyle,
                             override val language: Language,
                             override val metaTags: Seq[MetaTag]) extends Metadata:
    override def render: MainText = s"""<head>
                                     | ${metaTags.map(_.render).mkString("\n")}
                                     |</head>""".stripMargin.toMainText

    override def renderStyle: StyleText =
      s"""${style.tag} {\n  ${style.renderStyle}\n}""".toStyleText