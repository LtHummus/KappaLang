package com.lthummus.kappalang

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import com.lthummus.kappalang.translator.EmoteTranslator._

/**
  * A small utility for converting Brainfuck code to KappaLang. The usage is as follows:
  * BrainfuckToKappaCode bfcodefile tokenwidthofimage tokenheightofimage diroftokenfiles outputfile
  *
  * The dimensions have to be exact, so if you need to pad your code, you should (I did it by just
  * adding the a character to the end.  Also remember the trailing slash at the end of the diroftokens
  */
object BrainfuckToKappaLang extends App {

  val bfCode = scala.io.Source.fromFile(args(0)).getLines.mkString
  val widthTokens = args(1).toInt
  val heightTokens = args(2).toInt

  val dir = args(3)
  val out = args(4)

  val SymbolMap = Map(
    '-' -> ImageIO.read(new File(dir + "BibleThump.png")),
    ']' -> ImageIO.read(new File(dir + "twitchraid.png")),
    '.' -> ImageIO.read(new File(dir + "KAPOW.png")),
    '[' -> ImageIO.read(new File(dir + "riPepperonis.png")),
    ',' -> ImageIO.read(new File(dir + "NotLikeThis.png")),
    '+' -> ImageIO.read(new File(dir + "Kappa.png")),
    '<' -> ImageIO.read(new File(dir + "RitzMitz.png")),
    '>' -> ImageIO.read(new File(dir + "FrankerZ.png"))
  )

  val NopFile = ImageIO.read(new File(dir + "nop.png"))

  if (heightTokens * widthTokens != bfCode.length) {
    println("Can't build that with the given dimensions")
    println("Dimensions given: " + widthTokens + "x" + heightTokens)
    println("Code length: " + bfCode.length)
  }

  val tokenCords = for {
    y <- 0 until heightTokens
    x <- 0 until widthTokens
  } yield (x * TokenWidth, y * TokenHeight)

  val symbols = bfCode.map(SymbolMap.getOrElse(_, NopFile))

  val image = new BufferedImage(widthTokens * TokenWidth,
    heightTokens * TokenHeight,
    BufferedImage.TYPE_INT_RGB)

  val graphics = image.getGraphics


  for (x <- tokenCords zip symbols) {
    graphics.drawImage(x._2, x._1._1, x._1._2, null)
  }

  ImageIO.write(image, "png", new File(out))

}
