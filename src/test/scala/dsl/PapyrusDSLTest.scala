package dsl

import papyrus.dsl.DSL.PapyrusApplication

object PapyrusDSLTest extends PapyrusApplication:

  papyrus:
    metadata:
      nameFile:
        "Third Sprint"
      language:
        "en"
      author:
        "LucaDani"
      extension:
        "html"
      margin:
        150
    content:
      title:
        "End 3rd Sprint"
      text:
        "Normale" color "red"
      section:
        title:
          "Table and listing"
        text:
          "Let's try to print a table." newLine "Ciao" newLine "Ciao"
        table:
          withList:
            List(List("1", "2", "3"),List("4", "5", "6"),List("7", "8", "9"))
          renderTable:
            (s: String) => s
        table:
          "1" | "2" | "3"
          "4" | "5" | "6"
          "7" | "8" | "9"
          caption:
            "This is our first table:"
          alignTable:
            "center"
        subsection:
          title:
            "Listing" textColor "red"
          underline:
            "Prova grassetto"
          text:
            "\nWhy do we use it?\nIt is a long established" newLine "Ciao"
          listing:
            listType:
              "ol"
            item:
              "First element"
            item:
              "Second element"
      section:
        title:
          "Image"
        text:
          "This is our first image:"
        image:
          "src/main/resources/PapyrusLogo.png" caption "This is papyrus logo" alternative "No image found" width 200