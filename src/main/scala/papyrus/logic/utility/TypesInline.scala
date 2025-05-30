package papyrus.logic.utility

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.*
import io.github.iltotore.iron.constraint.string.*

object TypesInline:
  type Alignment = String :| Match["left|right|center|justify|start|end"]
  type Border = Int :| Interval.Closed[0, 200]
  type Charset = String :| Match["utf-8|iso-8859-1|windows-1252"]
  type ColorString = String :| Match["^#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6})$|^rgb\\((\\d{1,3}),\\s?(\\d{1,3}),\\s?(\\d{1,3})\\)$|aliceblue|antiquewhite|aqua|aquamarine|azure|beige|bisque|black|blanchedalmond|blue|blueviolet|brown|burlywood|cadetblue|chartreuse|chocolate|coral|cornflowerblue|cornsilk|crimson|cyan|darkblue|darkcyan|darkgoldenrod|darkgray|darkgreen|darkgrey|darkkhaki|darkmagenta|darkolivegreen|darkorange|darkorchid|darkred|darksalmon|darkseagreen|darkslateblue|darkslategray|darkslategrey|darkturquoise|darkviolet|deeppink|deepskyblue|dimgray|dimgrey|dodgerblue|firebrick|floralwhite|forestgreen|fuchsia|gainsboro|ghostwhite|gold|goldenrod|gray|green|greenyellow|grey|honeydew|hotpink|indianred|indigo|ivory|khaki|lavender|lavenderblush|lawngreen|lemonchiffon|lightblue|lightcoral|lightcyan|lightgoldenrodyellow|lightgray|lightgreen|lightgrey|lightpink|lightsalmon|lightseagreen|lightskyblue|lightslategray|lightslategrey|lightsteelblue|lightyellow|lime|limegreen|linen|magenta|maroon|mediumaquamarine|mediumblue|mediumorchid|mediumpurple|mediumseagreen|mediumslateblue|mediumspringgreen|mediumturquoise|mediumvioletred|midnightblue|mintcream|mistyrose|moccasin|navajowhite|navy|oldlace|olive|olivedrab|orange|orangered|orchid|palegoldenrod|palegreen|paleturquoise|palevioletred|papayawhip|peachpuff|peru|pink|plum|powderblue|purple|rebeccapurple|red|rosybrown|royalblue|saddlebrown|salmon|sandybrown|seagreen|seashell|sienna|silver|skyblue|slateblue|slategray|slategrey|snow|springgreen|steelblue|tan|teal|thistle|tomato|turquoise|violet|wheat|white|whitesmoke|yellow|yellowgreen"]
  type Extension = String :| Match["html|pdf"]
  type FontFamily = String :| Match["Arial|Helvetica|Times New Roman|Courier New|Verdana|Georgia|Palatino|Garamond|Bookman|Tahoma|Trebuchet MS|Impact|Comic Sans MS|Consolas|Lucida Console|Lucida Sans Unicode|monospace|sans-serif|serif|Roboto|Open Sans|Lato|Montserrat|Oswald|Raleway|Poppins|Noto Sans|Ubuntu|Fira Sans|Nunito|Merriweather|Work Sans|Rubik|Inter|Inconsolata|DM Serif Display|Source Sans Pro|Quicksand|Oxygen|Cabin"]
  type FontSize = Int :| Interval.Closed[8, 72]
  type FontStyle = String :| Match["normal|italic"]
  type FontWeight = String :| Match["normal|bold"]
  type Height = Int :| Interval.Closed[0, 1920]
  type Language = String :| Match["en|it|fr|de|es|zh|ja|pt|ru|ar"]
  type LetterSpacing = Double :| Interval.Closed[0.0, 1.0]
  type Level = Int :| Interval.Closed[1, 6]
  type LineHeight = Double :| Interval.Closed[1.0, 3.0]
  type Margin = Int :| Interval.Closed[0, 200]
  type Padding = Int :| Interval.Closed[0, 200]
  type StyleSheet = String :| EndWith[".css"]
  type TextDecoration = String :| Match["none|underline|overline"]
  type TextTransform = String :| Match["none|uppercase|lowercase|capitalize"]
  type Width = Int :| Interval.Closed[0, 1920]
  type WordSpacing = Double :| Interval.Closed[0.0, 20.0]
