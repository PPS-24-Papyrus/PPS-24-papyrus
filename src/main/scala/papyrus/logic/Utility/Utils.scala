package papyrus.logic.Utility

import scala.compiletime.error

object Utils:

  object BoundedInt:

    opaque type BoundedInt = Int

    inline def apply(inline min: Int, inline max: Int)(inline value: Int): BoundedInt =
      inline if value >= min && value <= max then value
      else error(s"Value $value not in [$min, $max]")

    extension (b: BoundedInt)
      def value: Int = b

  object BoundedDouble:

    opaque type BoundedDouble = Double

    inline def apply(inline min: Double, inline max: Double)(inline value: Double): BoundedDouble =
      inline if value >= min && value <= max then value
      else error(s"Value $value not in [$min, $max]")

    extension (b: BoundedDouble)
      def value: Double = b

  object StringEnumUtils:

    inline def validateStringValue(inline value: String, inline allowed: String*): String =
      inline allowed match
        case Array() => error("Allowed values list cannot be empty")
        case _ =>
          inline if containsValue(value, allowed) then value
          else error(s"Invalid value: '$value'. Allowed: ${allowed.mkString(", ")}")

    private inline def containsValue(inline v: String, inline values: Seq[String]): Boolean =
      inline values match
        case Seq() => false
        case Seq(head, tail@_*) =>
          inline if v == head then true
          else containsValue(v, tail)