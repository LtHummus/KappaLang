package com.lthummus.kappalang.interperter

/**
  * A simple brainfuck interperter.  Takes a string and executes it.  Input
  * is a little wonky because console input is buffered.
  */
class BrainFuckInterperter(program: Seq[Char]) {
  def moveBackToMatch(): Unit = {
    var level = 1
    while (level > 0) {
      if (dataPointer < 0) {
        //ERROR HERE
        println("Fell off beginning looking for brace")
      }
      instructionPointer -= 1
      if (program(instructionPointer) == ']')
        level += 1
      else if (program(instructionPointer) == '[')
        level -= 1
    }
  }

  def moveForwardToMatch(): Unit = {
    var level = 1
    while (level > 0) {
      if (dataPointer > program.length - 1) {
        //ERROR HERE
        println("Fell off end looking for brace")
      }
      instructionPointer += 1
      if (program(instructionPointer) == '[')
        level += 1
      else if (program(instructionPointer) == ']')
        level -= 1
    }
  }

  def incrementMemory(): Unit = {
    memorySpace(dataPointer) += 1
    if (memorySpace(dataPointer) > 255)
      memorySpace(dataPointer) = 0
  }

  def decrementMemory(): Unit = {
    memorySpace(dataPointer) -= 1
    if (memorySpace(dataPointer) < 0)
      memorySpace(dataPointer) = 255
  }

  def readCharacter = {
    val in = System.in.read()
    println("read " + in)
    in
  }

  val MemorySize = 30000

  val memorySpace: Array[Int] = Array.fill[Int](MemorySize)(0)

  var instructionPointer = 0
  var dataPointer = 0

  while (instructionPointer < program.length) {
    program(instructionPointer) match {
      case '+' => incrementMemory()
      case '-' => decrementMemory()
      case '>' => dataPointer += 1
      case '<' => dataPointer = math.max(0, dataPointer - 1)
      case '.' => print(memorySpace(dataPointer).toChar)
      case ',' => memorySpace(dataPointer) = readCharacter
      case '[' => if (memorySpace(dataPointer) == 0) moveForwardToMatch()
      case ']' => if (memorySpace(dataPointer) != 0) moveBackToMatch()
      case _ => /* nop */
    }
    instructionPointer += 1
  }



}
