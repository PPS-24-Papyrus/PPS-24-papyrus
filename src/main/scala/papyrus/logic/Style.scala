package papyrus.logic

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

object Style:

  def textColor(color: ColorString): String =
    s"color: $color;"

  def backgroundColor(color: ColorString): String =
    s"background-color: $color;"

  def font(font: FontFamily): String =
    s"font-family: $font;"

  def fontSize(size: FontSize): String =
    s"font-size: ${size}px;"

  def fontWeight(fontWeight: FontWeight): String = s"font-weight: $fontWeight;"

  def fontStyle (fontStyle: FontStyle): String = s"font-style: $fontStyle;"

  def textDecoration(textDecoration: TextDecoration): String = s"text-decoration: $textDecoration;"

  def textTransform(textTransform: TextTransform): String = s"text-transform: $textTransform;"

  def letterSpacing(spacing: LetterSpacing): String =
    s"letter-spacing: ${spacing}px;"

  def wordSpacing(spacing: WordSpacing): String =
    s"word-spacing: ${spacing}px;"

  def lineHeight(lineHeight: LineHeight): String =
    s"line-height: $lineHeight;"

  def textAlign(position: Alignment): String =
    s"text-align: $position;"

  def width(width: Width): String =
    s"width: ${width}px;"

  def height(height: Height): String =
    s"height: ${height}px;"

  def margin(margin: Margin): String =
    s"margin: ${margin}px;"

  def margin(top: Margin, right: Margin, bottom: Margin, left: Margin): String =
    s"margin: ${top}px ${right}px ${bottom}px ${left}px;"

  def padding(padding: Padding): String =
    s"padding: ${padding}px;"

  def padding(top: Padding, right: Padding, bottom: Padding, left: Padding): String =
    s"padding: ${top}px ${right}px ${bottom}px ${left}px;"

  def border(border: Border): String =
    s"border: ${border}px solid black;"  // puoi personalizzare tipo e colore se necessario

  def border(top: Border, right: Border, bottom: Border, left: Border): String =
    s"border-width: ${top}px ${right}px ${bottom}px ${left}px;"
