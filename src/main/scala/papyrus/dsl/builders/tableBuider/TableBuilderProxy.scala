package papyrus.dsl.builders.tableBuider

import papyrus.logic.layerElement.captionElement.Table
import papyrus.utility.TypesInline.*
import papyrus.dsl.builders.Builder

/** Immutable proxy that forwards mutations to the real builder */
class TableBuilderProxy[T](
                            get: () => TableBuilder[T],
                            set: TableBuilder[T] => Unit
                          ) extends TableBuilder[T](rows = Vector.empty[RowBuilder[T]]):

  override def withCaption(value: String): TableBuilder[T] =
    set(get().withCaption(value)); this

  override def withBackgroundColor(value: ColorString): TableBuilder[T] =
    set(get().withBackgroundColor(value)); this

  override def withMargin(value: Margin): TableBuilder[T] =
    set(get().withMargin(value)); this

  override def withTextAlign(value: Alignment): TableBuilder[T] =
    set(get().withTextAlign(value)); this

  override def withWidth(value: Width): TableBuilder[T] =
    set(get().withWidth(value)); this

  override def withAlignment(value: Align): TableBuilder[T] =
    set(get().withAlignment(value)); this

  override def withFunctionRender(f: T => String): TableBuilder[T] =
    set(get().withFunctionRender(f)); this

  override def addRow(rb: RowBuilder[T]): TableBuilder[T] =
    set(get().addRow(rb)); this

  override def build: Table[T] =
    get().build
