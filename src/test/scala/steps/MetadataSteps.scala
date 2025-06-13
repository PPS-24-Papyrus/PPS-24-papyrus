package steps

import io.cucumber.scala.{EN, ScalaDsl}
import org.scalatest.Assertions.*
import papyrus.DSL.builders.{MetadataBuilder, PapyrusBuilder}
import papyrus.logic.metadata.Metadata
import papyrus.logic.utility.TypesInline.{Charset, Extension, Language}


class MetadataSteps extends ScalaDsl with EN {

  private var metadataBuilder: Option[MetadataBuilder] = None
  private var papyrusBuilder: Option[PapyrusBuilder] = None
  private var renderedMetadata: Option[Metadata] = None
  private var error: Option[Throwable] = None
  
  Given("""I create a new Metadata""") { () =>
    metadataBuilder = Some(MetadataBuilder())
  }

  Given("""I create a new Papyrus document""") { () =>
    papyrusBuilder = Some(PapyrusBuilder())
  }

  Given("""I set the font Metadata:""") { (data: Map[String, String]) =>
    metadataBuilder = metadataBuilder.map { builder =>
      data.foldLeft(builder) {
        case (b, ("nameFile", value)) => b.withNameFile(value)
        case (b, ("extension", value)) => b.withExtension(value.asInstanceOf[Extension])
        case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
        case (b, ("title", value)) => b.withTitle(value)
        case (b, ("author", value)) => b.withAuthor(value)
        case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
        case (b, ("styleSheet", value)) => b.withStyleSheet(value)
        case (b, _) => b
      }
    }
  }

  When("""I render the Metadata""") { () =>
    try {
      renderedMetadata = metadataBuilder.map(_.build)
    } catch {
      case e: Throwable => error = Some(e)
    }
  }

  When("""I set the Metadata:""") { (data: Map[String, String]) =>
    metadataBuilder = metadataBuilder.map { builder =>
      data.foldLeft(builder) {
        case (b, ("nameFile", value)) => b.withNameFile(value)
        case (b, ("extension", value)) => b.withExtension(value.asInstanceOf[Extension])
        case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
        case (b, ("title", value)) => b.withTitle(value)
        case (b, ("author", value)) => b.withAuthor(value)
        case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
        case (b, ("styleSheet", value)) => b.withStyleSheet(value)
        case (b, _) => b
      }
    }
  }

  When("""I try to set the metadata again:""") { (data: Map[String, String]) =>
    try {
      metadataBuilder = metadataBuilder.map { builder =>
        data.foldLeft(builder) {
          case (b, ("nameFile", value)) => b.withNameFile(value)
          case (b, ("extension", value)) => b.withExtension(value.asInstanceOf[Extension])
          case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
          case (b, ("title", value)) => b.withTitle(value)
          case (b, ("author", value)) => b.withAuthor(value)
          case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
          case (b, ("styleSheet", value)) => b.withStyleSheet(value)
          case (b, _) => b
        }
      }
    } catch {
      case e: Throwable => error = Some(e)
    }
  }
  

  Then("""The result should be an default Metadata structure""") { () =>
    val defaultMetadata = MetadataBuilder().build
    assert(renderedMetadata.contains(defaultMetadata))
  }

  Then("""The metadata should contain:""") { (data: Map[String, String]) =>
    val expectedMetadata = data.foldLeft(MetadataBuilder()) {
      case (b, ("nameFile", value)) => b.withNameFile(value)
      case (b, ("extension", value)) => b.withExtension(value.asInstanceOf[Extension])
      case (b, ("language", value)) => b.withLanguage(value.asInstanceOf[Language])
      case (b, ("title", value)) => b.withTitle(value)
      case (b, ("author", value)) => b.withAuthor(value)
      case (b, ("charset", value)) => b.withCharset(value.asInstanceOf[Charset])
      case (b, ("styleSheet", value)) => b.withStyleSheet(value)
      case (b, _) => b
    }.build
    assert(renderedMetadata.contains(expectedMetadata))
  }

  Then("""The system should raise an error""") { () =>
    assert(error.isDefined)
  }
}