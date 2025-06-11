package papyrus.logic.builders

import papyrus.logic.metadata.Metadata
import papyrus.logic.styleObjects.MainStyle
import papyrus.logic.utility.TypesInline.{Charset, Extension, Language, StyleSheet}
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues


class MetadataBuilder extends Builder[Metadata]:

  // -- Campi privati con valori di default
  private var _nameFile: String = DefaultValues.nameFile
  private var _extension: Extension = DefaultValues.extension
  private var _language: Language = DefaultValues.language
  private var _title: String = DefaultValues.title
  private var _author: String = DefaultValues.author
  private var _charset: Charset = DefaultValues.charset
  private var _styleSheet: String = DefaultValues.styleSheet
  private var _style: MainStyle = MainStyle()

  // -- Getter e setter interni
  private def nameFile: String = _nameFile
  private def nameFile_=(value: String): Unit = _nameFile = value

  private def extension: Extension = _extension
  private def extension_=(value: Extension): Unit = _extension = value

  private def language: Language = _language
  private def language_=(value: Language): Unit = _language = value

  private def title: String = _title
  private def title_=(value: String): Unit = _title = value

  private def author: String = _author
  private def author_=(value: String): Unit = _author = value

  private def charset: Charset = _charset
  private def charset_=(value: Charset): Unit = _charset = value

  private def styleSheet: String = _styleSheet
  private def styleSheet_=(value: String): Unit = _styleSheet = value

  private def style: MainStyle = _style
  private def style_=(value: MainStyle): Unit = _style = value

  // -- API pubblica per impostare i campi
  def withNameFile(value: String): Unit = nameFile = value
  def withExtension(value: Extension): Unit = extension = value
  def withLanguage(value: Language): Unit = language = value
  def withTitle(value: String): Unit = title = value
  def withAuthor(value: String): Unit = author = value
  def withCharset(value: Charset): Unit = charset = value
  def withStyleSheet(value: String): Unit = styleSheet = value
  def withStyle(value: MainStyle): Unit = style = value

  // -- Costruisce l'oggetto Metadata usando i valori correnti
  override def build: Metadata =
    Metadata(nameFile, extension, style, language, title, author, charset, styleSheet)
