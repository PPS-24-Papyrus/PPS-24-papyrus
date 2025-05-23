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

