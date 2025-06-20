package papyrus.DSL.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.metadata.Metadata

class MetadataBuilderProxy(
                            get: () => MetadataBuilder,
                            set: MetadataBuilder => Unit
                          ) extends MetadataBuilder() {

  override def withNameFile(value: String): MetadataBuilder =
    val updated = get().withNameFile(value)
    set(updated)
    this

  override def withExtension(value: Extension): MetadataBuilder =
    val updated = get().withExtension(value)
    set(updated)
    this

  override def withSavingPath(value: String): MetadataBuilder =
    val updated = get().withSavingPath(value)
    set(updated)
    this

  override def withLanguage(value: Language): MetadataBuilder =
    val updated = get().withLanguage(value)
    set(updated)
    this

  override def withTitle(value: String): MetadataBuilder =
    val updated = get().withTitle(value)
    set(updated)
    this

  override def withAuthor(value: String): MetadataBuilder =
    val updated = get().withAuthor(value)
    set(updated)
    this

  override def withCharset(value: Charset): MetadataBuilder =
    val updated = get().withCharset(value)
    set(updated)
    this

  override def withStyleSheet(value: StyleSheet): MetadataBuilder =
    val updated = get().withStyleSheet(value)
    set(updated)
    this

  override def withStyle(value: MainStyleBuilder): MetadataBuilder =
    val updated = get().withStyle(value)
    set(updated)
    this

  override def getStyleBuilder: MainStyleBuilder =
    get().getStyleBuilder

  override def build: Metadata =
    get().build
}
