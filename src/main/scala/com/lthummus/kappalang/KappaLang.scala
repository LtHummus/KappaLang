package com.lthummus.kappalang

import java.io.File
import javax.imageio.ImageIO

import com.lthummus.kappalang.interperter.BrainFuckInterperter
import com.lthummus.kappalang.translator.EmoteTranslator

/**
  * Takes a kappalang image as the first argument.  Translates the image to a brainfuck
  * program and then executes the brainfuck
  */
object KappaLang extends App {
  val analyzer = new EmoteTranslator(ImageIO.read(new File(args(0))))
  new BrainFuckInterperter(analyzer.brainfuckString)
}
