package papyrus.logic.builders

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine


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

given Conversion[String, TextDSL] with
  def apply(str: String): TextDSL = TextDSL(str)