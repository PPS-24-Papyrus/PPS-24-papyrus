package papyrus.dsl.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.metadata.Metadata

/** Builds document metadata proxy */

class MetadataBuilderProxy(
                            get: () => MetadataBuilder,
                            set: MetadataBuilder => Unit
                          ) extends MetadataBuilder() {

  /** Sets filename */
  override def withNameFile(value: String): MetadataBuilder =
    val updated = get().withNameFile(value)
    set(updated)
    this

  /** Sets file extension ("html" or "pdf") */
  override def withExtension(value: Extension): MetadataBuilder =
    val updated = get().withExtension(value)
    set(updated)
    this

  /** Sets absolute saving path */
  override def withSavingPath(value: String): MetadataBuilder =
    val updated = get().withSavingPath(value)
    set(updated)
    this

  /** Sets language ("en", "it", "fr", "de", etc.) */
  override def withLanguage(value: Language): MetadataBuilder =
    val updated = get().withLanguage(value)
    set(updated)
    this

  /** Sets document title */
  override def withTitle(value: String): MetadataBuilder =
    val updated = get().withTitle(value)
    set(updated)
    this

  /** Sets author name */
  override def withAuthor(value: String): MetadataBuilder =
    val updated = get().withAuthor(value)
    set(updated)
    this

  /** Sets charset ("utf-8", "iso-8859-1", "windows-1252") */
  override def withCharset(value: Charset): MetadataBuilder =
    val updated = get().withCharset(value)
    set(updated)
    this

  /** Sets stylesheet path (String ending with ".css"). Papyrus creates a default stylesheet */
  override def withStyleSheet(value: StyleSheet): MetadataBuilder =
    val updated = get().withStyleSheet(value)
    set(updated)
    this

  /** Sets style parameters with another builder */
  override def withStyle(value: MainStyleBuilder): MetadataBuilder =
    val updated = get().withStyle(value)
    set(updated)
    this

  override def getStyleBuilder: MainStyleBuilder =
    get().getStyleBuilder

  override def build: Metadata =
    get().build
}
