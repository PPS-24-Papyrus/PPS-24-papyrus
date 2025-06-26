# DSL

## Grammatica del DSL (EBNF)

La sintassi del linguaggio Papyrus è formalizzata attraverso la seguente grammatica in forma EBNF. Questa definizione evidenzia l’organizzazione ad albero del documento, i vincoli di contesto e le possibili personalizzazioni inline. Ogni documento inizia con la keyword `papyrus`, e può contenere metadati, contenuto strutturato e stili.

```ebnf
document      ::= "papyrus" ":" metadata content ;
metadata      ::= "metadata" ":" { metaProperty } ;
content       ::= "content" ":" { element } ;

metaProperty  ::= extension | titleMeta | author | language | charset
                | styleSheet | nameFile | path | font | fontSize
                | lineHeight | textColor | backgroundColor | textAlign | margin ;

element       ::= textElement
                | titleElement
                | section
                | image
                | table
                | listing ;

section       ::= "section" ":" { element } ;
subsection    ::= "subsection" ":" { element } ;

textElement   ::= "text" ":" textContent { inlineStyle } ;
titleElement  ::= "title" ":" string { inlineStyle } ;

listing       ::= "listing" ":" { listProperty | item } ;
item          ::= "item" ":" string ;

image         ::= "image" ":" imageBuilder ;

table         ::= "table" ":" tableBuilder ;

inlineStyle   ::= fontWeight | fontStyle | textDecoration | color ;

textContent   ::= string | string "newLine" string ;

extension     ::= "extension" ":" string ;
titleMeta     ::= "metadataTitle" ":" string ;
author        ::= "author" ":" string ;
language      ::= "language" ":" string ;
charset       ::= "charset" ":" string ;
styleSheet    ::= "styleSheet" ":" string ;
nameFile      ::= "nameFile" ":" string ;
path          ::= "path" ":" string ;
font          ::= "font" ":" string ;
fontSize      ::= "fontSize" ":" number ;
lineHeight    ::= "lineHeight" ":" number ;
textColor     ::= "textColor" ":" color ;
backgroundColor ::= "backgroundColor" ":" color ;
textAlign     ::= "textAlign" ":" alignment ;
margin        ::= "margin" ":" number ;

fontWeight    ::= "fontWeight" ":" string ;
fontStyle     ::= "fontStyle" ":" string ;
textDecoration ::= "textDecoration" ":" string ;
color         ::= "#hex" | "rgb(...)" | namedColor ;
alignment     ::= "left" | "right" | "center" | "justify" ;

imageBuilder  ::= string { imageProp } ;
imageProp     ::= "alt" ":" string
                | "caption" ":" string
                | "width" ":" number ;

tableBuilder  ::= ... (* dipende dalla variante usata: DSL o tabella manuale *) ;
listProperty  ::= "listType" ":" string | "ordered" ":" string | "reference" ":" string ;
```

**Nota:** La grammatica definita in EBNF **non descrive i vincoli semantici** legati al contesto di utilizzo delle keyword. Ad esempio:
- `subsection` può essere dichiarata solo all’interno di una `section`;
- `item` è valido unicamente dentro un `listing`.
Tali vincoli non sono codificati direttamente nella grammatica, ma sono **garantiti staticamente a compile-time** tramite i tipi impliciti (`using`) del linguaggio Scala.

---

## Struttura gerarchica del documento (albero DSL)
```
papyrus
├── metadata
│   ├── title
│   ├── author
│   ├── language
│   ├── charset
│   ├── font
│   ├── fontSize
│   ├── textColor
│   ├── backgroundColor
│   ├── textAlign
│   ├── margin
│   └── ...
└── content
    ├── title
    ├── text
    ├── image
    ├── table
    ├── listing
    │   ├── item
    │   ├── item
    │   ├── listing
    │   │   ├── item
    │   │   └── ...
    │   └── ...
    ├── section
    │   ├── title
    │   ├── text / image / table / listing
    │   ├── subsection
    │   │   ├── title
    │   │   ├── text / image / table / listing
    │   │   └── ...
    │   └── ...
    └── ...
```

