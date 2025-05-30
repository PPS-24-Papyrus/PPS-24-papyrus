package papyrus.DSL

import papyrus.logic.layerElement.text.{Text, Title}
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine
import papyrus.logic.builders.{ContentBuilder, MetadataBuilder, PapyrusBuilder, TextBuilder, TitleBuilder, TextDSL}
import papyrus.logic.layerElement.LayerElement
import papyrus.logic.styleObjects.{TextStyle, TitleStyle}

import java.util.Optional

object DSL:

  def papyrus(init: PapyrusBuilder ?=> Unit): Unit =
    given builder: PapyrusBuilder = PapyrusBuilder()
    init
    builder.build()

  def metadata(init: MetadataBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    pb.metadata = builder.build()

  def content(init: ContentBuilder ?=> Unit)(using pb: PapyrusBuilder): Unit =
    given builder: ContentBuilder = ContentBuilder()
    init
    pb.content = builder.build()


  def title(init: TitleBuilder ?=> TextDSL)(using cb: ContentBuilder): Unit =
    given builder: TitleBuilder = TitleBuilder()
    val textWrapper = init
    builder.title = textWrapper.str
    cb.setTitle(builder.build())

  def text(init: TextBuilder ?=> TextDSL)(using cb: ContentBuilder): Unit =
    given builder: TextBuilder = TextBuilder()
    val textWrapper = init
    builder.value = textWrapper.str
    cb.addLayerElement(builder.build())

  def nameFile(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withNameFile(init.str)

  def extension(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withExtension(init.str.asInstanceOf[Extension])

  def language(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withLanguage(init.str.asInstanceOf[Language])

  def metadataTitle(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withTitle(init.str)

  def author(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withAuthor(init.str)

  def charset(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    val updated: Unit = mb.withCharset(init.str.asInstanceOf[Charset])

  def styleSheet(init: MetadataBuilder ?=> TextDSL)(using mb: MetadataBuilder): Unit =
    given builder: MetadataBuilder = MetadataBuilder()
    init
    mb.withStyleSheet(init.str)


  given Conversion[String, TextDSL] with
    def apply(str: String): TextDSL = TextDSL(str)

  @main def provaFunc(): Unit =
    papyrus:
      metadata:
        nameFile:
          "ciao"
        language:
          "it"
        author:
          "Luca"
        extension:
          "html"
      content:
        title:
          "Inizio Sprint 3"
        text:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus fermentum nulla sed nisi dapibus, at tincidunt nisi tincidunt. Sed vitae velit vel nisi venenatis sollicitudin. Suspendisse potenti. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Curabitur ac lorem non enim imperdiet scelerisque. Morbi ut cursus nulla, nec ultrices odio. Duis feugiat risus ut sapien bibendum porttitor. Etiam at lectus vel purus varius tristique. Maecenas efficitur vehicula magna, nec sollicitudin lorem convallis sed.\n\nFusce vitae turpis eget neque porttitor vulputate. Mauris ullamcorper metus at felis feugiat, et viverra odio sagittis. Vestibulum aliquam lorem nec luctus finibus. Curabitur lobortis, eros ac convallis pretium, neque felis rhoncus risus, nec efficitur lacus odio et erat. Donec sit amet felis sit amet erat fermentum suscipit. Integer cursus sapien nec magna feugiat, id efficitur elit efficitur. Cras sodales dapibus sapien, a fringilla justo dignissim vel. Sed tincidunt mauris eget arcu laoreet cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n\nAenean et neque vitae tortor tincidunt malesuada nec non sapien. Sed pulvinar ante at erat egestas, nec varius nulla imperdiet. Proin a metus a neque fermentum efficitur. Cras rutrum dolor eu dignissim vestibulum. Nam ac leo sed odio tincidunt luctus. Integer in arcu vitae ligula scelerisque cursus. Curabitur blandit lectus at felis rutrum, a hendrerit tellus facilisis. Nulla facilisi. Aliquam nec lobortis risus. Nam mattis feugiat elit at efficitur. Aliquam erat volutpat. Integer hendrerit sem sit amet sapien euismod, et dignissim tortor malesuada.\n\nPraesent lacinia, nunc ac dignissim tincidunt, justo risus tincidunt nisi, in feugiat eros ante sed nibh. Mauris sit amet scelerisque ligula. Nullam in eros ut ante fermentum porttitor. Donec sit amet turpis non risus suscipit luctus. Nunc finibus diam a libero tincidunt fermentum. Integer nec justo porta, sodales nunc et, gravida ipsum. Sed accumsan tortor ac diam rhoncus, at dignissim nulla congue.\n\nVivamus lacinia malesuada nunc, id fermentum ex accumsan sed. Sed nec diam non nunc gravida faucibus. Integer dignissim nunc vel augue tincidunt, nec pulvinar augue scelerisque. Etiam vestibulum sem id dui ornare efficitur. Fusce id lacus id nisi cursus varius. Quisque suscipit sem ut neque commodo, vitae fermentum magna laoreet. Suspendisse potenti. Pellentesque sed eros sed velit eleifend tincidunt. Etiam vitae congue est. Integer vel finibus sapien. Curabitur fermentum convallis justo. Duis non nisl eget ligula placerat convallis.\n\nSed pharetra risus vel augue porta, sit amet tempus justo vestibulum. Donec vestibulum lorem non mi imperdiet, ut tempus massa vehicula. Integer vitae volutpat augue, non sagittis neque. Aenean id sem a justo vestibulum hendrerit. Curabitur vel finibus metus. Proin nec dapibus est. Nullam ut sem quam. Integer eget felis eu leo facilisis fringilla. Quisque fermentum lorem metus, nec hendrerit metus finibus non.\n\nDonec mattis magna nec mi fermentum blandit. Curabitur convallis mi quis ante accumsan, a tincidunt augue feugiat. In pretium accumsan risus. Vivamus vel vulputate magna. Integer a sem orci. Curabitur porta nulla non sapien sodales, nec porta nulla accumsan. Duis ultrices felis at sollicitudin hendrerit. Aliquam et sem fermentum, congue magna in, finibus odio. Vivamus aliquam libero id sem scelerisque, at ultricies sem convallis. Nullam ut magna ac sem malesuada malesuada.\n\nNam et lorem vitae nisl dignissim malesuada. Aenean laoreet libero vitae mi fermentum, a facilisis elit sodales. Pellentesque ut nisl ac enim suscipit dapibus. Suspendisse potenti. Pellentesque ut lacinia nibh. Sed sit amet ex sed nunc faucibus ultrices. Aliquam erat volutpat. Sed convallis sem in massa egestas fringilla. Sed at diam vitae ipsum pretium scelerisque."










