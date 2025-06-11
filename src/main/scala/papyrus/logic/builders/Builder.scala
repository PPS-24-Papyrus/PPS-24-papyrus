package papyrus.logic.builders

trait Builder[T]:
  def build: T

