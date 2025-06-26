package papyrus.dsl

import papyrus.dsl.builders.MetadataBuilder
import papyrus.logic.utility.TypesInline.*

object MetadataDSL:

  /** Set filename */
  def nameFile(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withNameFile(init)

  /** Set file extension (e.g. "pdf", "html") */
  def extension(init: MetadataBuilder ?=> Extension)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withExtension(init)

  /** Set saving path */
  def path(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withSavingPath(init)

  /** Set document language (e.g. "en", "it") */
  def language(init: MetadataBuilder ?=> Language)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withLanguage(init)

  /** Set metadata title */
  def metadataTitle(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withTitle(init)

  /** Set author */
  def author(init: MetadataBuilder ?=> String)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withAuthor(init)

  /** Set charset (e.g. "utf-8") */
  def charset(init: MetadataBuilder ?=> Charset)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withCharset(init)

  /** Set CSS style sheet file */
  def styleSheet(init: MetadataBuilder ?=> StyleSheet)(using mb: MetadataBuilder): MetadataBuilder =
    mb.withStyleSheet(init)

  /** Set font family */
  def font(init: MetadataBuilder ?=> FontFamily)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFont(init))

  /** Set font size (pixels) */
  def fontSize(init: MetadataBuilder ?=> FontSize)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withFontSize(init))

  /** Set line height (e.g. 1.5) */
  def lineHeight(init: MetadataBuilder ?=> LineHeight)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withLineHeight(init))

  /** Set text color (e.g. "#000000") */
  def textColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextColor(init))

  /** Set background color */
  def backgroundColor(init: MetadataBuilder ?=> ColorString)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withBackgroundColor(init))

  /** Set text alignment ("left", "right", "center", "justify") */
  def textAlign(init: MetadataBuilder ?=> Alignment)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withTextAlign(init))

  /** Set margin (pixels) */
  def margin(init: MetadataBuilder ?=> Margin)(using msb: MetadataBuilder): MetadataBuilder =
    msb.withStyle(msb.getStyleBuilder.withMargin(init))