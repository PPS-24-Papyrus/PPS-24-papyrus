package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.Renderer.Text.*
import papyrus.logic.metadata.MetaTag.{MetaTag, authorTag, charsetTag, styleSheetTag, titleTag}
import papyrus.logic.styleObjects.MainStyle

object MetaTag:
  trait MetaTag:
    def name: String
    def content: String
    def render: MainText

  def titleTag(title: String): MetaTag = MetaTagImpl("title", title)
  def authorTag(author: String): MetaTag = MetaTagImpl("author", author)
  def charsetTag(charset: Charset): MetaTag = MetaTagImpl("charset", charset)
  def styleSheetTag(link: StyleSheet): MetaTag = new MetaTagImpl("stylesheet", link):
    override def render: MainText = s"""<link rel="stylesheet" href="$content"></link>""".toMainText

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
            styleSheet: String = DefaultValues.styleSheet): Metadata
  = MetadataImpl(nameFile, extension, savingPath, style, language, Seq(titleTag(title), authorTag(author), charsetTag(charset), styleSheetTag(DefaultValues.styleSheet)))

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