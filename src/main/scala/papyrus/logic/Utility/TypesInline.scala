package papyrus.logic.Utility

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.*
import io.github.iltotore.iron.constraint.string.*

object TypesInline:
  type FontSize = Int :| Interval.Closed[8, 72]
  opaque type Distance = Int :| Interval.Closed[0, 200]
  type Margin = Distance
  type Padding = Distance
  type Border = Distance
  opaque type Dimension = Int :| Interval.Closed[0, 1920]
  type Width = Dimension
  type Height = Dimension
  type LineHeight = Double :| Interval.Closed[1.0, 3.0]
  type FontFamily = String :| Match["Arial|Helvetica|Times New Roman|Courier New|Verdana|Georgia|Palatino|Garamond|Bookman|Tahoma|Trebuchet MS|Impact|Comic Sans MS|Consolas|Lucida Console|Lucida Sans Unicode|monospace|sans-serif|serif|Roboto|Open Sans|Lato|Montserrat|Oswald|Raleway|Poppins|Noto Sans|Ubuntu|Fira Sans|Nunito|Merriweather|Work Sans|Rubik|Inter|Inconsolata|DM Serif Display|Source Sans Pro|Quicksand|Oxygen|Cabin"]
  type Alignment = String :| Match["left|right|center|justify|start|end"]
  type Charset = String :| Match["utf-8|iso-8859-1|windows-1252"]
  type StyleSheet = String :| EndWith[".css"]
  type Language = String :| Match["en|it|fr|de|es|zh|ja|pt|ru|ar"]


@main def prova(): Unit = {
  import TypesInline._
  val i: FontFamily = "Arial"

}