package papyrus.dsl

import papyrus.dsl.builders.listBuilder.{ItemBuilder, ListBuilder, ListBuilderImpl, ListBuilderProxy}
import papyrus.dsl.builders.{ContentBuilder, PapyrusBuilder}
import papyrus.utility.TypesInline.{ListType, SortingList}
import papyrus.dsl.builders.tableBuider.{CellBuilder, RowBuilder, TableBuilder, TableBuilderProxy}
import papyrus.dsl.builders.sectionBuilder.{SectionBuilder, SubSectionBuilder}
import papyrus.dsl.builders.metadataBuilder.{MainStyleBuilder, MetadataBuilder, MetadataBuilderProxy}

object ListDSL:

  /** Define a list with optional nested lists and sorting */
  def listing(init: ListBuilder ?=> Unit)(using ctx: PapyrusBuilder | ContentBuilder | SectionBuilder | SubSectionBuilder | ListBuilder): Unit =
    var internalBuilder: ListBuilder = ctx match
      case lb: ListBuilder =>
        ListBuilderImpl(listType = lb.listType, order = lb.order, reference = lb.reference)
      case _ =>
        ListBuilderImpl()
    val proxy = ListBuilderProxy(() => internalBuilder, updated => internalBuilder = updated)

    given ListBuilder = proxy

    init
    ctx match
      case pb: PapyrusBuilder => pb.addLayerElement(proxy.build)
      case cb: ContentBuilder => cb.addLayerElement(proxy.build)
      case sb: SectionBuilder => sb.addLayerElement(proxy.build)
      case ssb: SubSectionBuilder => ssb.addLayerElement(proxy.build)
      case lb: ListBuilder => lb.add(internalBuilder)

  /** Define a list item, valid only inside listing */
  def item(init: ItemBuilder ?=> ItemBuilder)(using ctx: ListBuilder): Unit =
    given builder: ItemBuilder = ItemBuilder()

    ctx.add(init)

  /** Set list type ("ul" or "ol") */
  def listType(init: ListBuilder ?=> ListType)(using ctx: ListBuilder): Unit =
    ctx.withListType(init)

  /** Set list sorting ("alphabetical", "length", "reverse", "levenshtein") */
  def ordered(init: ListBuilder ?=> SortingList)(using ctx: ListBuilder): Unit =
    ctx.withSortingProperties(init)

  /** Set reference string for Levenshtein sorting */
  def reference(init: ListBuilder ?=> String)(using ctx: ListBuilder): Unit =
    ctx.withReference(init)