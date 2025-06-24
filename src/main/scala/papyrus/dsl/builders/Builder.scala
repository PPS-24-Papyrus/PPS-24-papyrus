package papyrus.dsl.builders

/** Generic builder interface for constructing instances of type T */
trait Builder[T]:
  def build: T
