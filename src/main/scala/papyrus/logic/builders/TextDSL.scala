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
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str))
    builder.addCell(CellBuilder().withContent(c))
    tb.addRow(builder)
    builder

  def hs(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).asHeader())
    builder.addCell(CellBuilder().withContent(c))
    tb.addRow(builder)
    builder

  def hsh(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).asHeader())
    builder.addCell(CellBuilder().withContent(c).asHeader())
    tb.addRow(builder)
    builder

  def -|(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).withColspan(2))
    builder.addCell(CellBuilder().withContent(c))
    tb.addRow(builder)
    builder

  def -|-(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).withColspan(2))
    builder.addCell(CellBuilder().withContent(c).withColspan(2))
    tb.addRow(builder)
    builder

  def |-(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str))
    builder.addCell(CellBuilder().withContent(c).withColspan(2))
    tb.addRow(builder)
    builder

  def ^|(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).withRowspan(2))
    builder.addCell(CellBuilder().withContent(c))
    tb.addRow(builder)
    builder

  def ^|^(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str).withRowspan(2))
    builder.addCell(CellBuilder().withContent(c).withRowspan(2))
    tb.addRow(builder)
    builder

  def |^(c: String)(using tb: TableBuilder): RowBuilder =
    val builder: RowBuilder = RowBuilder()
    builder.addCell(CellBuilder().withContent(str))
    builder.addCell(CellBuilder().withContent(c).withRowspan(2))
    tb.addRow(builder)
    builder

given Conversion[String, TextDSL] with
  def apply(str: String): TextDSL = TextDSL(str)

