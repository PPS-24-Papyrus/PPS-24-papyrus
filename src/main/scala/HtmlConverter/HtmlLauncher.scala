package HtmlConverter

import java.awt.Desktop
import java.io.File
import java.nio.file.{Files, Paths}

object HtmlLauncher:
  def launch(htmlString: String, title: String): Unit =
    val path = Files.createTempFile(title, ".html")
    Files.write(path, htmlString.getBytes())

    if (Desktop.isDesktopSupported) then
      Desktop.getDesktop.browse(path.toFile.toURI)





