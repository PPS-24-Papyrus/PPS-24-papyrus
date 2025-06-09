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

    private def styleBlock(selector: String, rules: (String, String)*): String =
      val body = rules.map { case (prop, value) => s"  $prop: $value;" }.mkString("\n")
      s"$selector {\n$body\n}"

    override def renderStyle: String =
      val marginValue = Option.when(margin != DefaultValues.marginTable)(s"${margin}px").getOrElse("3% 0")
      val widthValue = Option.when(width != DefaultValues.widthTable)(s"${width}px").getOrElse("auto")

      List(
        styleBlock("table", "border-collapse" -> "collapse"),
        styleBlock(s".cls-$id",
          "width" -> widthValue,
          "margin" -> marginValue,
          "display" -> "flex",
          "justify-content" -> alignment
        ),
        styleBlock("th, td",
          "border" -> "1px solid #ddd",
          "padding" -> "8px"
        ),
        styleBlock(s".cls-$id th",
          "background-color" -> backgroundColor,
          "text-align" -> textAlign
        )
      ).mkString("\n\n")


    override def tag: String =
        s"cls-$id"