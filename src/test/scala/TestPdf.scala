
import java.io.{File, FileOutputStream}
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder

object TestPdf {
  def convertHtmlToPdf(htmlContent: String, outputPath: String): Unit = {
    val outputStream = new FileOutputStream(new File(outputPath))

    try {
      val builder = new PdfRendererBuilder()
      builder.useFastMode()
      builder.withHtmlContent(htmlContent, null)
      builder.toStream(outputStream)
      builder.run()
    } finally {
      outputStream.close()
    }
  }

  def main(args: Array[String]): Unit = {
    val htmlString =
      """
        |<html>
        |  <head>
        |    <title>Example PDF</title>
        |  </head>
        |  <body>
        |    <h1>Hello, PDF!</h1>
        |    <p>This PDF was generated from an HTML string using Scala.</p>
        |  </body>
        |</html>
        |""".stripMargin

    val outputPdfPath = "output.pdf"
    convertHtmlToPdf(htmlString, outputPdfPath)
    println(s"PDF generated at: $outputPdfPath")
  }
}
