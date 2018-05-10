import math._

class Point(val location: Long) extends AnyVal {
  def x: Int = (location / pow(10, 9).toInt).toInt
  def y: Int = (location % pow(10, 9).toInt).toInt
  override def toString: String = s"Point($x, $y)"
}

object Point {
  def apply(location: Long): Point = {
    if (location >= 0) new Point(location)
    else throw new IllegalArgumentException
  }
}