package papyrus.logic.styleObjects

import papyrus.logic.Renderer.RendererStyle
import papyrus.utility.TypesInline.{Align, Alignment, ColorString, Margin, Width}
import io.github.iltotore.iron.autoRefine
import papyrus.dsl.DefaultValues
import papyrus.logic.Renderer.Text.*
import papyrus.utility.IdGenerator

/** Describes the visual style of a table element */
trait TableStyle extends RendererStyle:

  /** Background color used for header cells */
  def backgroundColor: ColorString

  /** Outer margin of the table container */
  def margin: Margin

  /** Text alignment inside cells */
  def textAlign: Alignment

  /** Table width in pixels */
  def width: Width

  /** Alignment of the table on the page (flex alignment) */
  def alignment: Align

  /** CSS class tag used to identify the table */
  def tag: String


object TableStyle:

  /** Creates a TableStyle instance with optional custom properties */
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

    override def renderStyle: StyleText =
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
      ).mkString("\n\n").toStyleText


    override def tag: String =
        s"cls-$id"