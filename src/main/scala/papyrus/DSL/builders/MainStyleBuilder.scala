package papyrus.DSL.builders

import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders.{BackgroundColor, TextAlign}
import papyrus.logic.utility.TypesInline.*
import papyrus.DSL.DefaultValues
import papyrus.logic.styleObjects.MainStyle

enum MainStyleField:
  case Font, FontSize, LineHeight, TextColor, BackgroundColor, TextAlign, Margin

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

  def withFont(value: FontFamily): MainStyleBuilder =
    setOnce(MainStyleField.Font, (v: FontFamily) => _font = v)(value)

  def withFontSize(value: FontSize): MainStyleBuilder =
    setOnce(MainStyleField.FontSize, (v: FontSize) => _fontSize = v)(value)

  def withLineHeight(value: LineHeight): MainStyleBuilder =
    setOnce(MainStyleField.LineHeight, (v: LineHeight) => _lineHeight = v)(value)

  def withTextColor(value: ColorString): MainStyleBuilder =
    setOnce(MainStyleField.TextColor, (v: ColorString) => _textColor = v)(value)

  def withBackgroundColor(value: ColorString): MainStyleBuilder =
    setOnce(MainStyleField.BackgroundColor, (v: ColorString) => _backgroundColor = v)(value)

  def withTextAlign(value: Alignment): MainStyleBuilder =
    setOnce(MainStyleField.TextAlign, (v: Alignment) => _textAlign = v)(value)

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
