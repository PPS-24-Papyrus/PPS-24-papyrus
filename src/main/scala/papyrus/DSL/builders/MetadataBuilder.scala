package papyrus.DSL.builders

import papyrus.logic.metadata.Metadata
import papyrus.logic.styleObjects.MainStyle
import papyrus.logic.utility.TypesInline.{Charset, Extension, Language, StyleSheet}
import papyrus.DSL.DefaultValues

enum Field:
  case NameFile, Extension, SavingPath, Language, Title, Author, Charset, StyleSheet, Style

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

  def withStyle(value: MainStyleBuilder) : MetadataBuilder =
    this.copy(styleBuilder = value)

  override def build: Metadata =
    Metadata(nameFile, extension, savingPath, styleBuilder.build, language, title, author, charset, styleSheet)
