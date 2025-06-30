package papyrus.utility

import io.github.iltotore.iron.autoRefine
import papyrus.utility.TypesInline.*

/** Default values used across the Papyrus DSL for metadata, styles, and content elements */
object DefaultValues:

  // === Metadata defaults ===
  val nameFile: String = "document"
  val extension: Extension = "pdf"
  val language: Language = "en"
  val title: String = "My Elegant Document"
  val author: String = "Author Name"
  val charset: Charset = "utf-8"
  val styleSheet: StyleSheet = "style.css"

  // === Body text defaults ===
  val font: FontFamily = "Times New Roman"
  val fontSize: FontSize = 12
  val lineHeight: LineHeight = 1.75
  val textColor: ColorString = "#222222"
  val backgroundColor: ColorString = "white"
  val margin: Margin = 100
  val color: ColorString = "#2F4F4F"
  val fontWeight: FontWeight = "normal"
  val fontStyle: FontStyle = "normal"
  val textDecoration: TextDecoration = "none"
  val bodyAlign: Alignment = "justify"

  // === Heading H1 ===
  val fontTitleH1: FontFamily = "Helvetica"
  val fontSizeTitleH1: FontSize = 32
  val textColorTitleH1: ColorString = "black"
  val textAlignTitleH1: Alignment = "center"
  val titleTextH1: String = "Welcome H1"
  val levelH1: Level = 1

  // === Heading H2 ===
  val fontTitleH2: FontFamily = "Helvetica"
  val fontSizeTitleH2: FontSize = 24
  val textColorTitleH2: ColorString = "#222222"
  val textAlignTitleH2: Alignment = "left"
  val titleTextH2: String = "Welcome H2"
  val levelH2: Level = 2

  // === Heading H3 ===
  val fontTitleH3: FontFamily = "Helvetica"
  val fontSizeTitleH3: FontSize = 16
  val textColorTitleH3: ColorString = "#333333"
  val textAlignTitleH3: Alignment = "left"
  val titleTextH3: String = "Welcome H3"
  val levelH3: Level = 3

  // === Default content text ===
  val defaultText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
  val colorText: ColorString = "#222222"
  val textAlignText: Alignment = "justify"
  val fontWeightText: FontWeight = "normal"
  val fontStyleText: FontStyle = "normal"
  val textDecorationText: TextDecoration = "none"

  // === Table defaults ===
  val backgroundColorTable: ColorString = "#f2f2f2"
  val marginTable: Margin = 30
  val textAlignTable: Alignment = "left"
  val widthTable: Width = 1000
  val alignTable: Align = "center"

  // === List item default ===
  val defaultItem: String = "Elemento lista"
  val defaultListType : ListType = "ul"

  // === Image defaults ===
  val defaultImageSrc: String = "default.png"
  val defaultImageAlt: String = "Nessuna descrizione"
