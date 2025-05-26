package papyrus.logic.utility

object StyleTypesInline:

  import Utils.BoundedInt

  object FontSize:
    opaque type FontSize = BoundedInt.BoundedInt
    inline def apply(inline value: Int): FontSize = BoundedInt(8, 72)(value)
    extension (fontSize: FontSize) def value: Int = fontSize.value

  object Distance:
    opaque type Distance = Int

    def apply(value: Int): Distance =
      if value >= 0 && value <= 200 then value
      else throw new IllegalArgumentException(s"Distance must be in [0, 200]: got $value")

    extension (d: Distance) def value: Int = d

  object Margin:
    opaque type Margin = Distance.Distance
    def apply(value: Int): Margin = Distance(value)
    extension (margin: Margin) def value: Int = margin.value

  object Padding:
    opaque type Padding = Distance.Distance
    def apply(value: Int): Padding = Distance(value)
    extension (padding: Padding) def value: Int = padding.value

  object Border:
    opaque type Border = Distance.Distance
    def apply(value: Int): Border = Distance(value)
    extension (border: Border) def value: Int = border.value

  object Dimension:
    opaque type Dimension = Int

    def apply(value: Int): Dimension =
      if value >= 0 && value <= 1920 then value
      else throw new IllegalArgumentException(s"Dimension must be between 0 and 1920: got $value")

    extension (dimension: Dimension) def value: Int = dimension

  object Width:
    opaque type Width = Dimension.Dimension

    def apply(value: Int): Width = Dimension(value)

    extension (width: Width) def value: Int = width.value

  object Height:
    opaque type Height = Dimension.Dimension

    def apply(value: Int): Height = Dimension(value)

    extension (height: Height) def value: Int = height.value

  object LineHeight:
    opaque type LineHeight = Double

    def apply(value: Double): LineHeight =
      if value >= 1.0 && value <= 3.0 then value
      else throw new IllegalArgumentException(s"LineHeight must be between 1.0 and 3.0: got $value")

    extension (lineHeight: LineHeight) def value: Double = lineHeight

  import Utils.StringEnumUtils.*

  object Alignment:

    opaque type Alignment = String

    inline def apply(inline value: String): Alignment =
      validateStringValue(value, "left", "center", "right")

    extension (alignment: Alignment) def value: String = alignment

  object FontFamily:

    opaque type FontFamily = String

    inline def apply(inline value: String): FontFamily =
      validateStringValue(value,
        "Arial", "Helvetica", "Times New Roman", "Courier New", "Verdana",
        "Georgia", "Palatino", "Garamond", "Bookman", "Tahoma", "Trebuchet MS",
        "Impact", "Comic Sans MS", "Consolas", "Lucida Console", "Lucida Sans Unicode",
        "monospace", "sans-serif", "serif", "Roboto", "Open Sans", "Lato", "Montserrat",
        "Oswald", "Raleway", "Poppins", "Noto Sans", "Ubuntu", "Fira Sans", "Nunito",
        "Merriweather", "Work Sans", "Rubik", "Inter", "Inconsolata", "DM Serif Display",
        "Source Sans Pro", "Quicksand", "Oxygen", "Cabin"
      )

    extension (fontFamily: FontFamily) def value: String = fontFamily


  object Charset:
    opaque type Charset = String

    inline def apply(inline value: String): Charset =
      validateStringValue(value, "utf-8", "iso-8859-1", "windows-1252")

    extension (charset: Charset) def value: String = charset

  object Language:
    opaque type Language = String

    inline def apply(inline value: String): Language =
      validateStringValue(value, "en", "it", "fr", "de", "es", "zh")

    extension (lang: Language) def value: String = lang