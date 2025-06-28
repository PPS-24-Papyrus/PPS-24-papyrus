package papyrus.dsl

import papyrus.dsl.builders.listBuilder.ItemBuilder
import papyrus.dsl.builders.metadataBuilder.{MetadataBuilder, MetadataBuilderProxy}
import papyrus.dsl.builders.sectionBuilder.{SectionBuilder, SubSectionBuilder}
import papyrus.dsl.builders.tableBuider.{CellBuilder, RowBuilder, TableBuilder}
import papyrus.dsl.builders.textBuilder.{TextBuilder, TitleBuilder}
import papyrus.dsl.builders.{ContentBuilder, ImageBuilder, PapyrusBuilder}


object DSL:

  /** A base trait for building structured documents using a Scala DSL.*/
  trait PapyrusApplication extends App:

    /** Entry point for building a Papyrus document */
    def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
      given builder: PapyrusBuilder = PapyrusBuilder()
      init
      builder.build()
  
    /** Define document metadata */
    def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
      var current: MetadataBuilder = MetadataBuilder()
      given MetadataBuilder = MetadataBuilderProxy(() => current, updated => current = updated)
      init
      pb.withMetadata(current)
  
    /** Define document content */
    def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
      given builder: ContentBuilder = ContentBuilder()
      init
      pb.withContent(builder)
      
    export DSL.*
    export io.github.iltotore.iron.autoRefine
    export DSL.given_Conversion_String_ImageBuilder
    export DSL.given_Conversion_String_TitleBuilder
    export DSL.given_Conversion_String_TextBuilder
    export DSL.given_Conversion_String_ItemBuilder
    export DSL.given_Conversion_List_RowBuilder
    export builders.tableBuider.RowBuilder.{|, -|, |-, -|-, ^|, |^, ^|^, hsh, hs}
    export builders.textBuilder.TextBuilder.{color, fontWeight, fontStyle, textDecoration, newLine}
    export builders.textBuilder.TitleBuilder.{level, font, fontSize, textColor, textAlign}
    export builders.ImageBuilder.{alternative, width, alignment, captionImage}
    export TextDSL.*
    export TitleDSL.*
    export MetadataDSL.*
    export ImageDSL.*
    export TableDSL.{captionTable, table, alignTable, widthTable, withList, renderTable, backgroundColorTable, textAlignTable, marginTable}
    export ListDSL.*
  
  /** Define a section, only valid inside Papyrus or Content */
  def section(init: SectionBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder): Unit =
    given builder: SectionBuilder = SectionBuilder()
    init
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(builder.build)
      case cb: ContentBuilder => cb.addLayerElement(builder.build)

  /** Define a subsection, only valid inside Section */
  def subsection(init: SubSectionBuilder ?=> Unit)(using cb: SectionBuilder): Unit =
    given builder: SubSectionBuilder = SubSectionBuilder()
    init
    cb.addLayerElement(builder.build)

  /** Convert List[T] to RowBuilder[T] */
  given[T]: Conversion[List[T], RowBuilder[T]] with
    def apply(list: List[T]): RowBuilder[T] =
      val rowBuilder = RowBuilder[T]()
      for str <- list do
        rowBuilder.addCell(CellBuilder().withContent(str))
      rowBuilder

  /** Convert String to TextBuilder */
  given Conversion[String, TextBuilder] with
    def apply(str: String): TextBuilder = TextBuilder(str)

  /** Convert String to TitleBuilder */
  given Conversion[String, TitleBuilder] with
    def apply(str: String): TitleBuilder = TitleBuilder(str)

  /** Convert String to ItemBuilder */
  given Conversion[String, ItemBuilder] with
    def apply(str: String): ItemBuilder = ItemBuilder(str)

  /** Convert String to ImageBuilder */
  given Conversion[String, ImageBuilder] with
    def apply(str: String): ImageBuilder = ImageBuilder(str)
