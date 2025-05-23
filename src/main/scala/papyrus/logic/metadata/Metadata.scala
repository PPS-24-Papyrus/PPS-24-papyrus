package papyrus.logic.metadata

import papyrus.logic.Renderer.Renderer
import papyrus.logic.Style.Style

object Metadata:

  trait Metadata extends Renderer:
    def style(style: Style): Unit
    def nameFile(name: String): Unit
    def title(title: String): Unit
    def author(author: String): Unit
    def charset(charset: String): Unit
    def styleSheet(link: String): Unit
    def language(language: String): Unit

  object Metadata:
    def apply() = ???