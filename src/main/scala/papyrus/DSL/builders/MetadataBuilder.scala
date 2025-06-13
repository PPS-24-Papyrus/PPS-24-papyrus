package papyrus.DSL.builders

import papyrus.logic.metadata.Metadata
import papyrus.logic.styleObjects.MainStyle
import papyrus.logic.utility.TypesInline.{Charset, Extension, Language}
import papyrus.DSL.DefaultValues

enum Field:
  case NameFile, Extension, Language, Title, Author, Charset, StyleSheet, Style

class MetadataBuilder extends Builder[Metadata]:

  private var _nameFile: String = DefaultValues.nameFile
  private var _extension: Extension = DefaultValues.extension
  private var _language: Language = DefaultValues.language
  private var _title: String = DefaultValues.title
  private var _author: String = DefaultValues.author
  private var _charset: Charset = DefaultValues.charset
  private var _styleSheet: String = DefaultValues.styleSheet
  private var _style: MainStyle = MainStyle()

  private val modifiedFields = scala.collection.mutable.Set.empty[Field]

  private def setOnce[T](field: Field, setter: T => Unit)(value: T): MetadataBuilder =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    setter(value)
    modifiedFields += field
    this

  def withNameFile(value: String): MetadataBuilder =
    setOnce(Field.NameFile, (v: String) => _nameFile = v)(value)

  def withExtension(value: Extension): MetadataBuilder =
    setOnce(Field.Extension, (v: Extension) => _extension = v)(value)

  def withLanguage(value: Language): MetadataBuilder =
    setOnce(Field.Language, (v: Language) => _language = v)(value)

  def withTitle(value: String): MetadataBuilder =
    setOnce(Field.Title, (v: String) => _title = v)(value)

  def withAuthor(value: String): MetadataBuilder =
    setOnce(Field.Author, (v: String) => _author = v)(value)

  def withCharset(value: Charset): MetadataBuilder =
    setOnce(Field.Charset, (v: Charset) => _charset = v)(value)

  def withStyleSheet(value: String): MetadataBuilder =
    setOnce(Field.StyleSheet, (v: String) => _styleSheet = v)(value)

  def withStyle(value: MainStyle): MetadataBuilder =
    setOnce(Field.Style, (v: MainStyle) => _style = v)(value)

  override def build: Metadata =
    Metadata(_nameFile, _extension, _style, _language, _title, _author, _charset, _styleSheet)
