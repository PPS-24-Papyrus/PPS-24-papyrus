package steps

import io.cucumber.datatable.DataTable
import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.matchers.should.Matchers.*
import papyrus.dsl.builders.{MainStyleBuilder, MetadataBuilder}
import papyrus.logic.metadata.Metadata
import papyrus.logic.styleObjects.MainStyle
import papyrus.utility.TypesInline.{Charset, ColorString, Extension, FontFamily, FontSize, Language, StyleSheet}
import papyrus.logic.Renderer.*


class MetadataSteps extends ScalaDsl with EN:

  private var metadataBuilder: Option[MetadataBuilder] = None
  private var renderedMetadata: Option[Metadata] = None
  private var styleBuilder: Option[MainStyleBuilder] = None
  private var builtStyle: Option[MainStyle] = None
  private var error: Option[Throwable] = None

  Given("""I create a new Metadata"""):
    metadataBuilder = Some(MetadataBuilder())

  import scala.jdk.CollectionConverters._


  When("""I set the Metadata:"""): (table: DataTable) =>
    val data: Map[String, String] = table.asMaps(classOf[String], classOf[String]).asScala.head.asScala.toMap
    metadataBuilder = metadataBuilder.map { builder =>
      data.foldLeft(builder) {
        case (b, ("fileName", value)) => b.withNameFile(value)
        case (b, ("output-type", value)) => b.withExtension(value.asInstanceOf[Extension])
        case (b, ("author", value)) => b.withAuthor(value)
        case (b, ("title", value)) => b.withTitle(value)
        case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
        case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
        case (b, ("styleSheet", value)) => b.withStyleSheet(value.asInstanceOf[StyleSheet])
        case (b, _) => b
      }
    }
    renderedMetadata = metadataBuilder.map(_.build)

  When("""I render the Metadata"""):
    try
      renderedMetadata = metadataBuilder.map(_.build)
    catch
      case e: Throwable => error = Some(e)

  Then("""The result should be an default Metadata structure:"""): (expectedOutput: String) =>
    renderedMetadata should not be empty
    renderedMetadata.get.render.string should include(expectedOutput.stripMargin)

  Then("""The metadata should contain:"""): (table: DataTable) =>
    val data: Map[String, String] = table.asMaps(classOf[String], classOf[String]).asScala.head.asScala.toMap
    val expectedMetadata = data.foldLeft(MetadataBuilder()) {
      case (b, ("fileName", value)) => b.withNameFile(value)
      case (b, ("output-type", value)) => b.withExtension(value.asInstanceOf[Extension])
      case (b, ("author", value)) => b.withAuthor(value)
      case (b, ("title", value)) => b.withTitle(value)
      case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
      case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
      case (b, ("styleSheet", value)) => b.withStyleSheet(value.asInstanceOf[StyleSheet])
      case (b, _) => b
    }.build.render
    renderedMetadata should not be empty
    renderedMetadata.get.render.string should include(expectedMetadata.string)

  Given("""I create a style text"""):
    styleBuilder = Some(new MainStyleBuilder())

  When("""I set the style text:"""): (table: DataTable) =>
    val data: Map[String, String] = table.asMaps(classOf[String], classOf[String]).asScala.head.asScala.toMap
    styleBuilder = styleBuilder.map { builder =>
      data.foldLeft(builder) {
        case (b, ("font-family", value)) => b.withFont(value.asInstanceOf[FontFamily])
        case (b, ("font-size", value)) => b.withFontSize(value.asInstanceOf[FontSize])
        case (b, ("color", value)) => b.withTextColor(value.asInstanceOf[ColorString])
        case (b, _) => b
      }
    }
    builtStyle = styleBuilder.map(_.build)

  Then("""The style text should contain:"""): (table: DataTable) =>
    val expected: Map[String, String] = table.asMaps(classOf[String], classOf[String]).asScala.head.asScala.toMap
    val expectedStyle = expected.foldLeft(new MainStyleBuilder()) {
      case (b, ("font-family", value)) => b.withFont(value.asInstanceOf[FontFamily])
      case (b, ("font-size", value)) => b.withFontSize(value.asInstanceOf[FontSize])
      case (b, ("color", value)) => b.withTextColor(value.asInstanceOf[ColorString])
      case (b, _) => b
    }.build
    builtStyle should not be empty
    builtStyle.get.renderStyle.string should include(expectedStyle.renderStyle.string)

