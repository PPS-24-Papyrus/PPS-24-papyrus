package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style
import papyrus.logic.utility.TypesInline.*

trait Metadata extends Renderer:
  def style(style: Style): String
  def nameFile(name: String): String
  def title(title: String): String
  def author(author: String): String
  def charset(charset: Charset): String
  def styleSheet(link: StyleSheet): String
  def language(language: Language): String
  
object Metadata:
  def apply(): Metadata = MetadataImpl()

  private class MetadataImpl() extends Metadata:
    override def style(style: Style): String = ???
    private def meta(tag: String)(value: String): String = s"""<meta name="$tag" content="$value">"""
    override def nameFile(name: String): String = ??? //Cos'è già questo?
    override def title(title: String = "New Papyrus"): String = this.meta("title")(title)
    override def author(author: String = "Unknown"): String = this.meta("author")(author)
    override def charset(charset: Charset): String = this.meta("charset")(charset)
    override def styleSheet(link: StyleSheet): String = s"""<link rel="stylesheet" href="$link">""" //se non erro questo tag non può essere visto da user
    override def language(language: Language): String = s"""lang="$language"""" //va direttamente nel tag html

    override def render: String = ???
    override def renderStyle: String = ???