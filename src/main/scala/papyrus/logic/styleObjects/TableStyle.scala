package papyrus.logic.styleObjects

import papyrus.logic.Renderer.RendererStyle
import papyrus.logic.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}
import io.github.iltotore.iron.autoRefine
import papyrus.DSL.DefaultValues
import papyrus.logic.utility.IdGenerator

trait TableStyle extends RendererStyle:
  def backgroundColor: ColorString
  def margin: Margin
  def textAlign: Alignment
  def width: Width
  def alignment: Align
  def tag: String

object TableStyle:
  def apply(
             backgroundColor: ColorString = DefaultValues.backgroundColorTable,
             margin: Margin = DefaultValues.marginTable,
             textAlign: Alignment = DefaultValues.textAlignTable,
             width: Width = DefaultValues.widthTable,
             align: Align = DefaultValues.alignTable
           ): TableStyle = TableStyleImpl(backgroundColor, margin, textAlign, width, align)
  
  private class TableStyleImpl(
                                override val backgroundColor: ColorString,
                                override val margin: Margin,
                                override val textAlign: Alignment,
                                override val width: Width,
                                override val alignment: Align
                              ) extends TableStyle:
    private val id: String = IdGenerator.nextId()

    override def renderStyle: String =
      val marginValue = Option.when(margin != DefaultValues.marginTable)(s"${margin}px").getOrElse("3% 0")
      val widthValue = Option.when(width != DefaultValues.widthTable)(s"${width}px").getOrElse("auto")
      println(alignment)
      s"""
         |table {
         |  border-collapse: collapse;
         |}
         |
         |.cls-$id {
         |  width: $widthValue;
         |  margin: $marginValue;
         |  display: flex;
         |  justify-content: $alignment;
         |  
         |}  
         |
         |th, td {
         |  border: 1px solid #ddd;
         |  padding: 8px;
         |}
         |
         |.cls-$id th {
         |  background-color: $backgroundColor;
         |  text-align: $textAlign;
         |}
      """.stripMargin

    override def tag: String = 
      s"cls-$id"  