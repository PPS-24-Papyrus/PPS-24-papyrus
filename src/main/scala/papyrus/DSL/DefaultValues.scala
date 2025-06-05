package papyrus.DSL

import io.github.iltotore.iron.autoRefine
import papyrus.logic.utility.TypesInline.*

object DefaultValues:
  val nameFile: String = "document"
  val extension: Extension = "pdf"
  val language: Language = "en"
  val title: String = "My Elegant Document"
  val author: String = "Author Name"
  val charset: Charset = "utf-8"
  val styleSheet: StyleSheet = "style.css"

  // Tipografia del corpo
  val font: FontFamily = "Times New Roman"
  val fontSize: FontSize = 12
  val lineHeight: LineHeight = 1.75
  val textColor: ColorString = "#222222"
  val backgroundColor: ColorString = "#FAFAFA"

  // Layout
  val margin: Margin = 100 // in px, generoso per leggibilità
  val color: ColorString = "#2F4F4F" // SlateGray
  val fontWeight: FontWeight = "normal"
  val fontStyle: FontStyle = "normal"
  val textDecoration: TextDecoration = "none"
  val bodyAlign: Alignment = "justify"

  // === Titoli H1 ===
  val fontTitleH1: FontFamily = "Helvetica"
  val fontSizeTitleH1: FontSize = 32
  val textColorTitleH1: ColorString = "black"
  val textAlignTitleH1: Alignment = "center"
  val titleTextH1: String = "Welcome H1"
  val levelH1: Level = 1

  // === Titoli H2 ===
  val fontTitleH2: FontFamily = "Helvetica"
  val fontSizeTitleH2: FontSize = 24
  val textColorTitleH2: ColorString = "#222222"
  val textAlignTitleH2: Alignment = "left"
  val titleTextH2: String = "Welcome H2"
  val levelH2: Level = 2

  // === Titoli H3 ===
  val fontTitleH3: FontFamily = "Helvetica"
  val fontSizeTitleH3: FontSize = 16
  val textColorTitleH3: ColorString = "#333333"
  val textAlignTitleH3: Alignment = "left"
  val titleTextH3: String = "Welcome H3"
  val levelH3: Level = 3

  // Contenuto predefinito
  val defaultText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus fermentum nulla sed nisi dapibus, at tincidunt nisi tincidunt. Sed vitae velit vel nisi venenatis sollicitudin. Suspendisse potenti. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Curabitur ac lorem non enim imperdiet scelerisque. Morbi ut cursus nulla, nec ultrices odio. Duis feugiat risus ut sapien bibendum porttitor. Etiam at lectus vel purus varius tristique. Maecenas efficitur vehicula magna, nec sollicitudin lorem convallis sed.\n\nFusce vitae turpis eget neque porttitor vulputate. Mauris ullamcorper metus at felis feugiat, et viverra odio sagittis. Vestibulum aliquam lorem nec luctus finibus. Curabitur lobortis, eros ac convallis pretium, neque felis rhoncus risus, nec efficitur lacus odio et erat. Donec sit amet felis sit amet erat fermentum suscipit. Integer cursus sapien nec magna feugiat, id efficitur elit efficitur. Cras sodales dapibus sapien, a fringilla justo dignissim vel. Sed tincidunt mauris eget arcu laoreet cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n\nAenean et neque vitae tortor tincidunt malesuada nec non sapien. Sed pulvinar ante at erat egestas, nec varius nulla imperdiet. Proin a metus a neque fermentum efficitur. Cras rutrum dolor eu dignissim vestibulum. Nam ac leo sed odio tincidunt luctus. Integer in arcu vitae ligula scelerisque cursus. Curabitur blandit lectus at felis rutrum, a hendrerit tellus facilisis. Nulla facilisi. Aliquam nec lobortis risus. Nam mattis feugiat elit at efficitur. Aliquam erat volutpat. Integer hendrerit sem sit amet sapien euismod, et dignissim tortor malesuada.\n\nPraesent lacinia, nunc ac dignissim tincidunt, justo risus tincidunt nisi, in feugiat eros ante sed nibh. Mauris sit amet scelerisque ligula. Nullam in eros ut ante fermentum porttitor. Donec sit amet turpis non risus suscipit luctus. Nunc finibus diam a libero tincidunt fermentum. Integer nec justo porta, sodales nunc et, gravida ipsum. Sed accumsan tortor ac diam rhoncus, at dignissim nulla congue.\n\nVivamus lacinia malesuada nunc, id fermentum ex accumsan sed. Sed nec diam non nunc gravida faucibus. Integer dignissim nunc vel augue tincidunt, nec pulvinar augue scelerisque. Etiam vestibulum sem id dui ornare efficitur. Fusce id lacus id nisi cursus varius. Quisque suscipit sem ut neque commodo, vitae fermentum magna laoreet. Suspendisse potenti. Pellentesque sed eros sed velit eleifend tincidunt. Etiam vitae congue est. Integer vel finibus sapien. Curabitur fermentum convallis justo. Duis non nisl eget ligula placerat convallis.\n\nSed pharetra risus vel augue porta, sit amet tempus justo vestibulum. Donec vestibulum lorem non mi imperdiet, ut tempus massa vehicula. Integer vitae volutpat augue, non sagittis neque. Aenean id sem a justo vestibulum hendrerit. Curabitur vel finibus metus. Proin nec dapibus est. Nullam ut sem quam. Integer eget felis eu leo facilisis fringilla. Quisque fermentum lorem metus, nec hendrerit metus finibus non.\n\nDonec mattis magna nec mi fermentum blandit. Curabitur convallis mi quis ante accumsan, a tincidunt augue feugiat. In pretium accumsan risus. Vivamus vel vulputate magna. Integer a sem orci. Curabitur porta nulla non sapien sodales, nec porta nulla accumsan. Duis ultrices felis at sollicitudin hendrerit. Aliquam et sem fermentum, congue magna in, finibus odio. Vivamus aliquam libero id sem scelerisque, at ultricies sem convallis. Nullam ut magna ac sem malesuada malesuada.\n\nNam et lorem vitae nisl dignissim malesuada. Aenean laoreet libero vitae mi fermentum, a facilisis elit sodales. Pellentesque ut nisl ac enim suscipit dapibus. Suspendisse potenti. Pellentesque ut lacinia nibh. Sed sit amet ex sed nunc faucibus ultrices. Aliquam erat volutpat. Sed convallis sem in massa egestas fringilla. Sed at diam vitae ipsum pretium scelerisque."
  val colorText: ColorString = "#222222"
  val textAlignText: Alignment = "justify"
  val fontWeightText: FontWeight = "normal"
  val fontStyleText: FontStyle = "normal"
  val textDecorationText: TextDecoration = "none"
  
  // table
  val backgroundColorTable: ColorString = "#f2f2f2"
  val marginTable: Margin = 30
  val textAlignTable: Alignment = "left"
  val widthTable: Width = 1000

// Item
  val defaultItem: String = "Elemento lista"
