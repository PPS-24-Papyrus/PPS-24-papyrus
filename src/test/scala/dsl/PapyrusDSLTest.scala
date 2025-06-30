package dsl

import papyrus.dsl.DSL.PapyrusApplication

object PapyrusDSLTest extends PapyrusApplication:

  papyrus:
    metadata:
      nameFile:
        "Papyrus"
      language:
        "en"
      extension:
        "html"
      margin:
        50
    content:
      title:
        "Lorem ipsum"
      section:
        title:
          "Lorem ipsum"
        subsection:
          title:
            "Lorem ipsum text"
          text:
            "Lorem ipsum" fontWeight "bold"
          text:
            "dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        subsection:
          title:
            "Lorem ipsum listing"
          listing:
            ordered:
              "length"
            item:
              "Lorem ipsum"
            listing:
              ordered:
                "alphabetical"
              item:
                "Lorem"
              item:
                "Ipsum"
            item:
              "Dolor sit"
            item:
              "Amet consectetur adipiscing elit"
      section:
        title:
          "Lorem ipsum table and images"
        subsection:
          title:
            "Lorem ipsum table"
          table:
            "cell 1" | "cell 2"  | "cell 3"  | "cell 4"
            "cell 5" | "cell 6"  | "cell 7"  | "cell 8"
            "cell 9" | "cell 10" | "cell 11" | "cell 12"
            captionTable:
              "Lorem ipsum table caption"
        subsection:
          title:
            "Lorem ipsum image"
          image:
            "src/main/resources/PapyrusLogo.png" captionImage "logo" alternative "Image not found" width 200