package com.lthummus.kappalang.translator

import java.awt.image.BufferedImage

import com.lthummus.kappalang.translator.EmoteTranslator._

import scala.math._

/**
  * This is the meat of it all right here.  Basically we do image matching based on the hisogram of each "token" from
  * the source image.  I don't have any experience in image recognition, so this was the best I could do with the 10
  * minutes of research on Google University.
  */

case class Histogram(low: Int, med: Int, high: Int, vhigh: Int) {
  def difference(other: Histogram) = abs(low - other.low) + abs(med - other.med) + abs(high - other.high) + abs(vhigh - other.vhigh)
}

case class ColorData(red: Histogram, green: Histogram, blue: Histogram) {
  def compare(other: ColorData) = red.difference(other.red) + green.difference(other.green) + blue.difference(other.blue)
}

class EmoteTranslator(input: BufferedImage) {
  val height = input.getHeight
  val width = input.getWidth

  val heightTokens = height / TokenHeight
  val widthTokens = width / TokenWidth

  val tokenImages = for {
    y <- 0 until heightTokens
    x <- 0 until widthTokens
  } yield input.getSubimage(x * TokenWidth, y * TokenHeight, TokenWidth, TokenHeight)

  val tokens = tokenImages.map(getBestMatch).toList
  val brainfuckString = tokens.map(x =>
    x match {
      case KAPPA => '+'
      case BIBLE_THUMP => '-'
      case RITZ_MITZ => '<'
      case FRANKER_Z => '>'
      case KAPOW => '.'
      case NOT_LIKE_THIS => ','
      case RIPEPPERONIS => '['
      case TWITCH_RAID => ']'
      case NOP => ' ' // turns in to nop later
    }).mkString

}

object EmoteTranslator {

  val TokenWidth = 40
  val TokenHeight = 30

  sealed abstract class Token(val name: String, val order: Int) extends Ordered[Token] {
    override def toString = name
    def compare(that: Token) = this.order - that.order
  }

  case object BIBLE_THUMP extends Token("BIBLE_THUMP", 0)
  case object FRANKER_Z extends Token("FRANKER_Z", 1)
  case object KAPOW extends Token("KAPOW", 2)
  case object KAPPA extends Token("KAPPA", 3)
  case object NOT_LIKE_THIS extends Token("NOT_LIKE_THIS", 4)
  case object RIPEPPERONIS extends Token("RIPEPPERONIS", 5)
  case object RITZ_MITZ extends Token("RITZ_MITZ", 6)
  case object TWITCH_RAID extends Token("TWITCH_RAID", 7)
  case object NOP extends Token("NOP", 8)



  val HistogramMap: Map[Token,ColorData] = Map[Token,ColorData](
    BIBLE_THUMP -> ColorData(Histogram(316,95,436,67),Histogram(316,95,436,67),Histogram(316,95,436,67)),
    FRANKER_Z -> ColorData(Histogram(351,188,108,167),Histogram(351,188,108,167),Histogram(351,188,108,167)),
    KAPOW -> ColorData(Histogram(429,38,35,35),Histogram(429,38,35,35),Histogram(429,38,35,35)),
    KAPPA -> ColorData(Histogram(63,98,168,148),Histogram(63,98,168,148),Histogram(63,98,168,148)),
    NOT_LIKE_THIS -> ColorData(Histogram(255,215,208,40),Histogram(255,215,208,40),Histogram(255,215,208,40)),
    RIPEPPERONIS -> ColorData(Histogram(189,20,25,21),Histogram(189,20,25,21),Histogram(189,20,25,21)),
    RITZ_MITZ -> ColorData(Histogram(46,152,314,36),Histogram(46,152,314,36),Histogram(46,152,314,36)),
    TWITCH_RAID -> ColorData(Histogram(17,31,123,236),Histogram(17,31,123,236),Histogram(17,31,123,236))
  )

  def getBestMatch(img: BufferedImage) = {
    val data = EmoteTranslator.analyzeImage(img)

    val diff = HistogramMap.map(x => (x._1, x._2.compare(data))).toList.sortBy(_._2).head

    if (diff._2 < 100) diff._1 else NOP
  }

  def analyzeImage(file: BufferedImage) = {
    val colorList =
      for {x <- 0 until file.getWidth
           y <- 0 until file.getHeight} yield file.getRGB(x, y)

    val redValues = colorList.map(_ & 0x00FF0000 >> 16).filter(_ != 0xF2).toList
    val greenValues = colorList.map(_ & 0x0000FF00 >> 8).filter(_ != 0xF2).toList
    val blueValues = colorList.map(_ & 0x000000FF).filter(_ != 0xF2).toList

    val red = Histogram(redValues.count(_ <= 63), redValues.count(x => x > 63 && x <= 127),
      redValues.count(x => x > 127 && x <= 192), redValues.count(_ > 192))
    val green = Histogram(greenValues.count(_ <= 63), greenValues.count(x => x > 63 && x <= 127),
      greenValues.count(x => x > 127 && x <= 192), greenValues.count(_ > 192))
    val blue = Histogram(blueValues.count(_ <= 63), blueValues.count(x => x > 63 && x <= 127),
      blueValues.count(x => x > 127 && x <= 192), blueValues.count(_ > 192))

    ColorData(red, green, blue)
  }
}
