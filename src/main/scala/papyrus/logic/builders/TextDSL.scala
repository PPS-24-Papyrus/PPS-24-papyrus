package papyrus.logic.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.layerElement.captionElement.Row


class TextDSL(val str: String):

  def titleColor(c: ColorString)(using tb: TitleBuilder): TextDSL =
    tb.title = str
    tb.textColor = c
    str

  def alignment(t: Alignment)(using tb: TitleBuilder): TextDSL =
    tb.title = str
    tb.textAlign = t
    str

  def fontSize(fs: FontSize)(using tb: TitleBuilder): TextDSL =
    tb.title = str
    tb.fontSize = fs
    str

  def fontFamily(ff: FontFamily)(using tb: TitleBuilder): TextDSL =
    tb.title = str
    tb.font = ff
    str

  def color(c: ColorString)(using tb: TextBuilder): TextDSL =
    tb.value = str
    tb.color = c
    str

  def fontWeight(w: FontWeight)(using tb: TextBuilder): TextDSL =
    tb.value = str
    tb.fontWeight = w
    str

  def fontStyle(s: FontStyle)(using tb: TextBuilder): TextDSL =
    tb.value = str
    tb.fontStyle = s
    str

  def textDecoration(d: TextDecoration)(using tb: TextBuilder): TextDSL =
    tb.value = str
    tb.textDecoration = d
    str

  def alternative(a: String)(using ib: ImageBuilder): TextDSL =
    ib.alt = a
    str

  def caption(c: String)(using ib: ImageBuilder): TextDSL =
    ib.caption = Some(c)
    str
  
  def width(w: Width)(using ib: ImageBuilder): TextDSL =
    ib.width = Some(w)
    str
    
  def alignmentImage(a: Float)(using ib: ImageBuilder): TextDSL =
    ib.alignment = Some(a)
    str

  def |(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, identity, _.withContent(c))

  def hs(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.asHeader(), _.withContent(c))

  def hsh(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.asHeader(), _.withContent(c).asHeader())

  def -|(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.withColspan(2), _.withContent(c))

  def -|-(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.withColspan(2), _.withContent(c).withColspan(2))

  def |-(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, identity, _.withContent(c).withColspan(2))

  def ^|(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.withRowspan(2), _.withContent(c))

  def ^|^(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, _.withRowspan(2), _.withContent(c).withRowspan(2))

  def |^(c: String)(using tb: TableBuilder): RowBuilder =
    makeRow(c, identity, _.withContent(c).withRowspan(2))

  private def makeRow(
               c: String,
               left: CellBuilder => CellBuilder,
               right: CellBuilder => CellBuilder
             )(using tb: TableBuilder): RowBuilder =
    val builder = RowBuilder()
    builder.addCell(left(CellBuilder().withContent(str)))
    builder.addCell(right(CellBuilder().withContent(c)))
    tb.addRow(builder)
    builder


given Conversion[String, TextDSL] with
  def apply(str: String): TextDSL = TextDSL(str)

