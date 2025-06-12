package papyrus.DSL.builders

import papyrus.logic.layerElement.text.Title
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

// Typeclass per generalizzare la gestione del titolo
trait TitleHandler[T]:
  def setTitle(t: T, title: Title): Unit
  def setLevel(t: T, builder: TitleBuilder): Unit

given TitleHandler[ContentBuilder] with
  def setTitle(cb: ContentBuilder, title: Title): Unit =
    cb.setTitle(title)

  override def setLevel(t: ContentBuilder, builder: TitleBuilder): Unit =
    builder.level(1)

given TitleHandler[SectionBuilder] with
  def setTitle(sb: SectionBuilder, title: Title): Unit =
    sb.setTitle(title)

  override def setLevel(sb: SectionBuilder, builder: TitleBuilder): Unit =
    builder.level(2) // forza il livello a 2 per le sezioni
