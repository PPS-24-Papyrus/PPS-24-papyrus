package papyrus.dsl.builders

import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders.{BackgroundColor, TextAlign}
import papyrus.logic.utility.TypesInline.*
import papyrus.dsl.DefaultValues
import papyrus.logic.styleObjects.MainStyle

private enum MainStyleField:
  case Font, FontSize, LineHeight, TextColor, BackgroundColor, TextAlign, Margin

/** Builder for constructing a MainStyle*/
class MainStyleBuilder extends Builder[MainStyle]:

  private var _font: FontFamily = DefaultValues.font
  private var _fontSize: FontSize = DefaultValues.fontSize
  private var _lineHeight: LineHeight = DefaultValues.lineHeight
  private var _textColor: ColorString = DefaultValues.colorText
  private var _backgroundColor: ColorString = DefaultValues.backgroundColor
  private var _textAlign: Alignment = DefaultValues.bodyAlign
  private var _margin: Margin = DefaultValues.margin

  private val modifiedFields = scala.collection.mutable.Set.empty[MainStyleField]

  private def setOnce[T](field: MainStyleField, setter: T => Unit)(value: T): MainStyleBuilder =
    if modifiedFields.contains(field) then
      throw new IllegalStateException(s"$field has already been set")
    setter(value)
    modifiedFields += field
    this

  /** Sets the font family (only once) */
  def withFont(value: FontFamily): MainStyleBuilder =
    setOnce(MainStyleField.Font, (v: FontFamily) => _font = v)(value)

  /** Sets the font size (only once) */
  def withFontSize(value: FontSize): MainStyleBuilder =
    setOnce(MainStyleField.FontSize, (v: FontSize) => _fontSize = v)(value)

  /** Sets the line height (only once) */
  def withLineHeight(value: LineHeight): MainStyleBuilder =
    setOnce(MainStyleField.LineHeight, (v: LineHeight) => _lineHeight = v)(value)

  /** Sets the text color (only once) */
  def withTextColor(value: ColorString): MainStyleBuilder =
    setOnce(MainStyleField.TextColor, (v: ColorString) => _textColor = v)(value)

  /** Sets the background color (only once) */
  def withBackgroundColor(value: ColorString): MainStyleBuilder =
    setOnce(MainStyleField.BackgroundColor, (v: ColorString) => _backgroundColor = v)(value)

  /** Sets the text alignment (only once) */
  def withTextAlign(value: Alignment): MainStyleBuilder =
    setOnce(MainStyleField.TextAlign, (v: Alignment) => _textAlign = v)(value)

  /** Sets the margin around the content (only once) */
  def withMargin(value: Margin): MainStyleBuilder =
    setOnce(MainStyleField.Margin, (v: Margin) => _margin = v)(value)

  override def build: MainStyle = MainStyle(
    font = _font,
    fontSize = _fontSize,
    lineHeight = _lineHeight,
    textColor = _textColor,
    backgroundColor = _backgroundColor,
    textAlign = _textAlign,
    margin = _margin
  )
