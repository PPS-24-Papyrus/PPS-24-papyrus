package papyrus.dsl.builders.tableBuider

import papyrus.logic.layerElement.captionElement.{Cell, Row, Table}
import papyrus.utility.DefaultValues
import papyrus.logic.styleObjects.TableStyle
import papyrus.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import papyrus.logic.Renderer.Text.*
import papyrus.dsl.builders.LayerElementBuilder

enum FieldTable:
  case Caption, BackgroundColor, Margin, TextAlign,
  Width, Align, FunctionRender, Rows

/** Builds a styled table with immutable data */
case class TableBuilder[T](
                            caption: Option[String] = None,
                            backgroundColor: ColorString = DefaultValues.backgroundColorTable,
                            margin: Margin = DefaultValues.marginTable,
                            textAlign: Alignment = DefaultValues.textAlignTable,
                            width: Width = DefaultValues.widthTable,
                            alignment: Align = DefaultValues.alignTable,
                            functionRender: T => String = (t: T) => t.toString,
                            rows: Vector[RowBuilder[T]] = Vector.empty,
                            modifiedFields: Set[FieldTable] = Set.empty
                          ) extends LayerElementBuilder:

  private inline def setOnce[A](
                                 field: FieldTable,
                                 update: TableBuilder[T] => TableBuilder[T]
                               )(value: A): TableBuilder[T] =
    if modifiedFields.contains(field) then
      throw IllegalStateException(s"$field has already been set")
    update(this).copy(modifiedFields = modifiedFields + field)

  /** set caption table */
  def withCaption(value: String): TableBuilder[T] =
    setOnce(FieldTable.Caption, _.copy(caption = Some(value)))(value)

  /** set backgroun color table */
  def withBackgroundColor(value: ColorString): TableBuilder[T] =
    setOnce(FieldTable.BackgroundColor, _.copy(backgroundColor = value))(value)

  /** set margin table */
  def withMargin(value: Margin): TableBuilder[T] =
    setOnce(FieldTable.Margin, _.copy(margin = value))(value)

  /** set text align table */
  def withTextAlign(value: Alignment): TableBuilder[T] =
    setOnce(FieldTable.TextAlign, _.copy(textAlign = value))(value)

  /** set width table */
  def withWidth(value: Width): TableBuilder[T] =
    setOnce(FieldTable.Width, _.copy(width = value))(value)

  /** set alignment table */
  def withAlignment(value: Align): TableBuilder[T] =
    setOnce(FieldTable.Align, _.copy(alignment = value))(value)

  /** set function render for table cell element */
  def withFunctionRender(f: T => String): TableBuilder[T] =
    setOnce(FieldTable.FunctionRender, _.copy(functionRender = f))(f)

  /** Adds a single row */
  def addRow(rb: RowBuilder[T]): TableBuilder[T] =
    copy(rows = rows :+ rb)

  /** Adds many rows in one call */
  def addRows(rbs: RowBuilder[T]*): TableBuilder[T] =
    copy(rows = rows ++ rbs)

  override def build: Table[T] =
    Table(
      caption,
      rows.map(_.build).toList,
      TableStyle(backgroundColor, margin, textAlign, width, alignment),
      t => MainText(functionRender(t))
    )