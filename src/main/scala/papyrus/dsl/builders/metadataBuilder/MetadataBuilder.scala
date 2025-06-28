package papyrus.dsl.builders.metadataBuilder

import papyrus.logic.metadata.Metadata
import papyrus.utility.TypesInline.{Charset, Extension, Language, StyleSheet}
import papyrus.utility.DefaultValues
import papyrus.dsl.builders.Builder

enum Field:
  case NameFile, Extension, SavingPath, Language, Title, Author, Charset, StyleSheet, Style

/** Builds document metadata with constrained fields */
case class MetadataBuilder(
                            nameFile: String = DefaultValues.nameFile,
                            extension: Extension = DefaultValues.extension,
                            savingPath: String = "",
                            language: Language = DefaultValues.language,
                            title: String = DefaultValues.title,
                            author: String = DefaultValues.author,
                            charset: Charset = DefaultValues.charset,
                            styleSheet: StyleSheet = DefaultValues.styleSheet,
                            styleBuilder: MainStyleBuilder = MainStyleBuilder(),
                            modifiedFields: Set[Field] = Set.empty
                          ) extends Builder[Metadata]:

  private def setOnce[T](field: Field, update: MetadataBuilder => MetadataBuilder)(value: T): MetadataBuilder =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    update(this).copy(modifiedFields = modifiedFields + field)

  def getStyleBuilder: MainStyleBuilder = styleBuilder

  def withNameFile(value: String): MetadataBuilder =
    setOnce(Field.NameFile, _.copy(nameFile = value))(value)

  def withExtension(value: Extension): MetadataBuilder =
    setOnce(Field.Extension, _.copy(extension = value))(value)

  def withSavingPath(value: String): MetadataBuilder =
    setOnce(Field.SavingPath, _.copy(savingPath = value))(value)

  def withLanguage(value: Language): MetadataBuilder =
    setOnce(Field.Language, _.copy(language = value))(value)

  def withTitle(value: String): MetadataBuilder =
    setOnce(Field.Title, _.copy(title = value))(value)

  def withAuthor(value: String): MetadataBuilder =
    setOnce(Field.Author, _.copy(author = value))(value)

  def withCharset(value: Charset): MetadataBuilder =
    setOnce(Field.Charset, _.copy(charset = value))(value)

  def withStyleSheet(value: StyleSheet): MetadataBuilder =
    setOnce(Field.StyleSheet, _.copy(styleSheet = value))(value)

  def withStyle(value: MainStyleBuilder): MetadataBuilder =
    this.copy(styleBuilder = value)

  override def build: Metadata =
    Metadata(nameFile, extension, savingPath, styleBuilder.build, language, title, author, charset, styleSheet)

object MetadataBuilder:
  extension (mb: MetadataBuilder)
    /** Sets filename */
    def nameFile(value: String): MetadataBuilder = mb.withNameFile(value)

    /** Sets file extension ("html" or "pdf") */
    def extension(value: Extension): MetadataBuilder = mb.withExtension(value)

    /** Sets absolute saving path */
    def savingPath(value: String): MetadataBuilder = mb.withSavingPath(value)

    /** Sets language ("en", "it", "fr", "de", etc.) */
    def language(value: Language): MetadataBuilder = mb.withLanguage(value)

    /** Sets document title */
    def title(value: String): MetadataBuilder = mb.withTitle(value)

    /** Sets author name */
    def author(value: String): MetadataBuilder = mb.withAuthor(value)

    /** Sets charset ("utf-8", "iso-8859-1", "windows-1252") */
    def charset(value: Charset): MetadataBuilder = mb.withCharset(value)

    /** Sets stylesheet path (String ending with ".css"). Papyrus create a default stylesheet */
    def styleSheet(value: StyleSheet): MetadataBuilder = mb.withStyleSheet(value)

    /** Sets style parameters with another builder */
    def style(value: MainStyleBuilder): MetadataBuilder = mb.withStyle(value)
