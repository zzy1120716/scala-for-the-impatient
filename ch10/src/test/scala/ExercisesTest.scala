import java.awt.geom.Ellipse2D
import java.awt.Point

import org.scalatest.FunSuite

import scala.collection.BitSet

class ExercisesTest extends FunSuite {

  /**
    * The java.awt.Rectangle class has useful methods translate and grow that are unfortunately
    * absent from classes such as java.awt.geom.Ellipse2D. In Scala, you
    * can fix this problem. Define a trait RectangleLike with concrete methods translate
    * and grow. Provide any abstract methods that you need for the implementation,
    * so that you can mix in the trait like this:
    *   val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    *   egg.translate(10, -10)
    *   egg.grow(10, 20)
    */
  test("1") {
    trait RectangleLike {
      def getX: Double
      def getY: Double
      def getWidth: Double
      def getHeight: Double
      def setFrame(x: Double, y: Double, width: Double, height: Double)
      def translate(dx: Double, dy: Double): Unit = {
        setFrame(getX + dx, getY + dy, getWidth, getHeight)
      }
      def grow(dx: Double, dy: Double): Unit = {
        setFrame(getX + dx, getY + dy, getWidth + 2 * dx, getHeight + 2 * dy)
      }
      override def toString = s"[$getX, $getY, $getWidth, $getHeight]"
    }
    val egg = new Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    assert(egg.toString === "[5.0, 10.0, 20.0, 30.0]")
    egg.translate(10, -10)
    assert(egg.toString === "[15.0, 0.0, 20.0, 30.0]")
    egg.grow(10, 20)
    assert(egg.toString === "[25.0, 20.0, 40.0, 70.0]")
  }

  /**
    * Define a class OrderedPoint by mixing scala.math.Ordered[Point] into java.awt.Point.
    * Use lexicographic ordering, i.e. (x, y) < (x’, y’) if x < x’ or x = x’ and y < y’.
    */
  test("2") {
    class OrderedPoint(x: Int, y: Int) extends Point(x, y) with scala.math.Ordered[Point] {
      override def compare(that: Point): Int = if (this.x > that.x || (this.x == that.x && this.y > that.y)) 1 else if (this.x == that.x && this.y == that.y) 0 else -1
      override def toString: String = s"($x, $y)"
    }
    val p1 = new OrderedPoint(1, 1)
    val p2 = new OrderedPoint(1, 0)
    val p3 = new OrderedPoint(1, 1)
    val p4 = new OrderedPoint(2, 1)
    assert(p1 > p2)
    assert(p1 == p3)
    assert(p1 < p4)
  }

  /**
    * Provide a CryptoLogger trait that encrypts the log messages with the Caesar cipher.
    * The key should be 3 by default, but it should be overridable by the user.
    * Provide usage examples with the default key and a key of –3.
    */
  test("4") {

    trait Logger {
      def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String): Unit = println(msg)
    }

    trait CryptoLogger extends Logger {
      val key = 3
      abstract override def log(msg: String): Unit = {
        //super.log((for (c <- msg) yield (c + key).toChar).mkString)
        //super.log(msg.map((c : Char) => (c + key).toChar).mkString)
        //super.log(msg.map(_ + key).map(_.toChar).mkString)
        
      }
    }

    abstract class Messenger extends Logger

    val m1 = new Messenger with ConsoleLogger with CryptoLogger
    m1.log("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG")

    val m2 = new {
      override val key = -3
    } with Messenger with ConsoleLogger with CryptoLogger
    m2.log("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG")
  }
}
