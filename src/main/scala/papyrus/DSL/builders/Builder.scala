package papyrus.DSL.builders

trait Builder[T]:
  def build: T

