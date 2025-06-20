package papyrus.logic.utility

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.*
import io.github.iltotore.iron.constraint.string.*

/** Semantic type aliases with constraints for styling, layout, and metadata */
object TypesInline:

  /** Text alignment options: left, right, center, justify, etc. */
  type Alignment = String :| Match["left|right|center|justify|start|end"]

  /** Border width in pixels (0–200) */
  type Border = Int :| Interval.Closed[0, 200]

  /** Character set encoding (e.g. "utf-8") */
  type Charset = String :| Match["utf-8|iso-8859-1|windows-1252"]

  /** Accepts hex, rgb, or standard named CSS color strings */
  type ColorString = String :| Match["^#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6})$|^rgb\\((\\d{1,3}),\\s?(\\d{1,3}),\\s?(\\d{1,3})\\)$|aliceblue|antiquewhite|aqua|aquamarine|azure|beige|bisque|black|blanchedalmond|blue|blueviolet|brown|burlywood|cadetblue|chartreuse|chocolate|coral|cornflowerblue|cornsilk|crimson|cyan|darkblue|darkcyan|darkgoldenrod|darkgray|darkgreen|darkgrey|darkkhaki|darkmagenta|darkolivegreen|darkorange|darkorchid|darkred|darksalmon|darkseagreen|darkslateblue|darkslategray|darkslategrey|darkturquoise|darkviolet|deeppink|deepskyblue|dimgray|dimgrey|dodgerblue|firebrick|floralwhite|forestgreen|fuchsia|gainsboro|ghostwhite|gold|goldenrod|gray|green|greenyellow|grey|honeydew|hotpink|indianred|indigo|ivory|khaki|lavender|lavenderblush|lawngreen|lemonchiffon|lightblue|lightcoral|lightcyan|lightgoldenrodyellow|lightgray|lightgreen|lightgrey|lightpink|lightsalmon|lightseagreen|lightskyblue|lightslategray|lightslategrey|lightsteelblue|lightyellow|lime|limegreen|linen|magenta|maroon|mediumaquamarine|mediumblue|mediumorchid|mediumpurple|mediumseagreen|mediumslateblue|mediumspringgreen|mediumturquoise|mediumvioletred|midnightblue|mintcream|mistyrose|moccasin|navajowhite|navy|oldlace|olive|olivedrab|orange|orangered|orchid|palegoldenrod|palegreen|paleturquoise|palevioletred|papayawhip|peachpuff|peru|pink|plum|powderblue|purple|rebeccapurple|red|rosybrown|royalblue|saddlebrown|salmon|sandybrown|seagreen|seashell|sienna|silver|skyblue|slateblue|slategray|slategrey|snow|springgreen|steelblue|tan|teal|thistle|tomato|turquoise|violet|wheat|white|whitesmoke|yellow|yellowgreen"]

  /** File extension for output format */
  type Extension = String :| Match["html|pdf"]

  /** Valid font families (e.g. "Arial", "Roboto") */
  type FontFamily = String :| Match["Arial|Helvetica|Times New Roman|Courier New|Verdana|Georgia|Palatino|Garamond|Bookman|Tahoma|Trebuchet MS|Impact|Comic Sans MS|Consolas|Lucida Console|Lucida Sans Unicode|monospace|sans-serif|serif|Roboto|Open Sans|Lato|Montserrat|Oswald|Raleway|Poppins|Noto Sans|Ubuntu|Fira Sans|Nunito|Merriweather|Work Sans|Rubik|Inter|Inconsolata|DM Serif Display|Source Sans Pro|Quicksand|Oxygen|Cabin"]

  /** Font size in points (8–72) */
  type FontSize = Int :| Interval.Closed[8, 72]

  /** Font style: normal or italic */
  type FontStyle = String :| Match["normal|italic"]

  /** Font weight: normal or bold */
  type FontWeight = String :| Match["normal|bold"]

  /** Image or element height in pixels (0–1920) */
  type Height = Int :| Interval.Closed[0, 1920]

  /** Document language code (ISO 639-1) */
  type Language = String :| Match["en|it|fr|de|es|zh|ja|pt|ru|ar"]

  /** Spacing between letters (0.0–1.0 em) */
  type LetterSpacing = Double :| Interval.Closed[0.0, 1.0]

  /** Heading level (1–6) */
  type Level = Int :| Interval.Closed[1, 6]

  /** Line height multiplier (1.0–3.0) */
  type LineHeight = Double :| Interval.Closed[1.0, 3.0]

  /** Outer margin in pixels (0–200) */
  type Margin = Int :| Interval.Closed[0, 200]

  /** Inner padding in pixels (0–200) */
  type Padding = Int :| Interval.Closed[0, 200]

  /** CSS file path that must end with .css */
  type StyleSheet = String :| EndWith[".css"]

  /** Text decoration options: none, underline, overline */
  type TextDecoration = String :| Match["none|underline|overline"]

  /** Text transform options: none, uppercase, lowercase, capitalize */
  type TextTransform = String :| Match["none|uppercase|lowercase|capitalize"]

  /** Width in pixels (0–1920) */
  type Width = Int :| Interval.Closed[0, 1920]

  /** Word spacing in em (0.0–20.0) */
  type WordSpacing = Double :| Interval.Closed[0.0, 20.0]

  /** Float direction: left, right, or none */
  type Float = String :| Match["left|right|none"]

  /** Basic alignment: left, right, or center */
  type Align = String :| Match["left|right|center"]

  /** List type: ordered or unordered */
  type ListType = String :| Match["ol|ul"]
