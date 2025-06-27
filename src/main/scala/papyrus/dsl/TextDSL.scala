package papyrus.dsl

import papyrus.dsl.builders.{ContentBuilder, PapyrusBuilder}
import io.github.iltotore.iron.autoRefine
import papyrus.utility.SectionCounter
import papyrus.dsl.builders.tableBuider.{CellBuilder, RowBuilder, TableBuilder, TableBuilderProxy}
import papyrus.dsl.builders.sectionBuilder.{SectionBuilder, SubSectionBuilder}
import papyrus.dsl.builders.metadataBuilder.{MetadataBuilder, MainStyleBuilder, MetadataBuilderProxy}
import papyrus.dsl.builders.textBuilder.{TextBuilder, TitleBuilder}

object TextDSL:

  private def applyTextStyle(init: TextBuilder ?=> TextBuilder, style: TextBuilder => TextBuilder)(
    using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given baseBuilder: TextBuilder = TextBuilder()

    val updatedBuilder = style(init)
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(updatedBuilder.build)
      case cb: ContentBuilder => cb.addLayerElement(updatedBuilder.build)
      case sb: SectionBuilder => sb.addLayerElement(updatedBuilder.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(updatedBuilder.build)

  /** Insert plain text */
  def text(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, identity)

  /** Insert bold text */
  def bold(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontWeight("bold"))

  /** Insert italic text */
  def italic(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.fontStyle("italic"))

  /** Insert underlined text */
  def underline(init: TextBuilder ?=> TextBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    applyTextStyle(init, _.textDecoration("underline"))


object TitleDSL:

  /** Define a title with automatic numbering depending on context */
  def title(init: TitleBuilder ?=> TitleBuilder)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()

    val configuredBuilder = init
    val baseTitle: String = configuredBuilder.build.title
    val numberedTitle: String = generateNumberedTitle(baseTitle, ctx)
    val numberedBuilder: TitleBuilder = generateLevelTitle(configuredBuilder, numberedTitle, ctx)
    ctx match
      case pb: PapyrusBuilder => pb.setTitle(numberedBuilder.build)
      case cb: ContentBuilder => cb.setTitle(numberedBuilder.build)
      case sb: SectionBuilder => sb.setTitle(numberedBuilder.build)
      case ssb: SubSectionBuilder => ssb.setTitle(numberedBuilder.build)

  private def generateNumberedTitle(baseTitle: String, ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): String =
    ctx match
      case _: SectionBuilder => s"${SectionCounter.nextSection()} $baseTitle"
      case _: SubSectionBuilder => s"${SectionCounter.nextSubsection()} $baseTitle"
      case _ => baseTitle

  private def generateLevelTitle(builder: TitleBuilder, title: String, ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder): TitleBuilder =
    ctx match
      case _: SectionBuilder => builder.title(title).level(2)
      case _: SubSectionBuilder => builder.title(title).level(3)
      case _ => builder.title(title).level(1)
    