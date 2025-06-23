package papyrus.logic

import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

/** Provides CSS style string builders for various visual properties */
object Style:

  /** Sets the text color */
  def textColor(color: ColorString): String =
    s"color: $color;"

  /** Sets the background color */
  def backgroundColor(color: ColorString): String =
    s"background-color: $color;"

  /** Sets the font family */
  def font(font: FontFamily): String =
    s"font-family: $font;"

  /** Sets the font size in px */
  def fontSize(size: FontSize): String =
    s"font-size: ${size}px;"

  /** Sets the font weight ("normal", "bold", etc.) */
  def fontWeight(fontWeight: FontWeight): String =
    s"font-weight: $fontWeight;"

  /** Sets the font style ("normal", "italic", etc.) */
  def fontStyle(fontStyle: FontStyle): String =
    s"font-style: $fontStyle;"

  /** Sets the text decoration ("underline", "none", etc.) */
  def textDecoration(textDecoration: TextDecoration): String =
    s"text-decoration: $textDecoration;"

  /** Applies a text transformation ("uppercase", "lowercase", etc.) */
  def textTransform(textTransform: TextTransform): String =
    s"text-transform: $textTransform;"

  /** Sets letter spacing in px */
  def letterSpacing(spacing: LetterSpacing): String =
    s"letter-spacing: ${spacing}px;"

  /** Sets word spacing in px */
  def wordSpacing(spacing: WordSpacing): String =
    s"word-spacing: ${spacing}px;"

  /** Sets line height */
  def lineHeight(lineHeight: LineHeight): String =
    s"line-height: $lineHeight;"

  /** Sets horizontal text alignment ("left", "right", "center", "justify") */
  def textAlign(position: Alignment): String =
    s"text-align: $position;"

  /** Sets element width in px */
  def width(width: Width): String =
    s"width: ${width}px;"

  /** Sets element height in px */
  def height(height: Height): String =
    s"height: ${height}px;"

  /** Sets all margins equally in px */
  def margin(margin: Margin): String =
    s"margin: ${margin}px;"

  /** Sets margin for top, right, bottom, and left in px */
  def margin(top: Margin, right: Margin, bottom: Margin, left: Margin): String =
    s"margin: ${top}px ${right}px ${bottom}px ${left}px;"

  /** Sets all paddings equally in px */
  def padding(padding: Padding): String =
    s"padding: ${padding}px;"

  /** Sets padding for top, right, bottom, and left in px */
  def padding(top: Padding, right: Padding, bottom: Padding, left: Padding): String =
    s"padding: ${top}px ${right}px ${bottom}px ${left}px;"

  /** Sets a solid black border with uniform width in px */
  def border(border: Border): String =
    s"border: ${border}px solid black;"

  /** Sets border width for top, right, bottom, and left in px */
  def border(top: Border, right: Border, bottom: Border, left: Border): String =
    s"border-width: ${top}px ${right}px ${bottom}px ${left}px;"
