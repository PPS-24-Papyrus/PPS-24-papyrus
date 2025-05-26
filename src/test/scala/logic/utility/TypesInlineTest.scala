package logic.utility

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.*
import papyrus.logic.utility.TypesInline.*
import io.github.iltotore.iron.autoRefine

class TypesInlineTest extends AnyFunSuite:

  test("All valid values for TypesInline"):

    val fontSize: FontSize = 12
    fontSize shouldBe 12

    val margin: Margin = 100
    margin shouldBe 100

    val padding: Padding = 50
    padding shouldBe 50

    val border: Border = 10
    border shouldBe 10

    val width: Width = 1024
    width shouldBe 1024

    val height: Height = 768
    height shouldBe 768

    val lineHeight: LineHeight = 1.5
    lineHeight shouldBe 1.5

    val fontFamily: FontFamily = "Roboto"
    fontFamily shouldBe "Roboto"

    val alignment: Alignment = "center"
    alignment shouldBe "center"

    val charset: Charset = "utf-8"
    charset shouldBe "utf-8"

    val styleSheet: StyleSheet = "theme.css"
    styleSheet shouldBe "theme.css"

    val language: Language = "en"
    language shouldBe "en"

    val colorHex: ColorString = "#ffcc00"
    colorHex shouldBe "#ffcc00"

    val colorRGB: ColorString = "rgb(0, 128, 255)"
    colorRGB shouldBe "rgb(0, 128, 255)"

    val colorName: ColorString = "turquoise"
    colorName shouldBe "turquoise"
