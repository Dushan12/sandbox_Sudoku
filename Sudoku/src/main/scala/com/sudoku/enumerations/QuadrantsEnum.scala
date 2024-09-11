package com.sudoku.enumerations

sealed trait Quadrant

object Quadrants {
  final case class Quadrant1() extends Quadrant

  final case class Quadrant2() extends Quadrant

  final case class Quadrant3() extends Quadrant

  final case class Quadrant4() extends Quadrant

  final case class Quadrant5() extends Quadrant

  final case class Quadrant6() extends Quadrant

  final case class Quadrant7() extends Quadrant

  final case class Quadrant8() extends Quadrant

  final case class Quadrant9() extends Quadrant


  def getAll: List[Quadrant] = List(
    Quadrant1(),
    Quadrant2(),
    Quadrant3(),
    Quadrant4(),
    Quadrant5(),
    Quadrant6(),
    Quadrant7(),
    Quadrant8(),
    Quadrant9(),
  )
}