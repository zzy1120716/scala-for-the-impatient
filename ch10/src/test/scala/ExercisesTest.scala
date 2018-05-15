import java.awt.geom.Ellipse2D
import java.awt.Point
import java.beans.{PropertyChangeEvent, PropertyChangeListener, PropertyChangeSupport}
import java.io.{InputStream, FileInputStream}

import org.scalatest.FunSuite

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
      def log(msg: String): Unit = {}
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
        val caesarMsg = for (c <- msg) yield c match {
          case a if 'A' <= a && a <= 'Z' => applyKey(c, 'A', key, 26)
          case a if 'a' <= a && a <= 'z' => applyKey(c, 'a', key, 26)
          case a if '0' <= a && a <= '9' => applyKey(c, '0', key, 10)
          case _ => c
        }
        super.log(caesarMsg)
      }

      def applyKey(c: Char, a: Char, key: Int, mod: Int): Char = {
        ((c - a + key + mod) % mod + a).toChar
      }
    }

    abstract class Messenger extends Logger

    val m1 = new Messenger with ConsoleLogger with CryptoLogger
    m1.log("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG")
    m1.log("Jane's score is 77.")

    val m2 = new {
      override val key = -3
    } with Messenger with ConsoleLogger with CryptoLogger
    m2.log("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG")

    /*val c1 = new CryptoLogger with ConsoleLogger
    println(c1.applyKey('C', 'A', 3, 26))*/
  }

  /**
    * The JavaBeans specification has the notion of a property change listener, a
    * standardized way for beans to communicate changes in their properties. The
    * PropertyChangeSupport class is provided as a convenience superclass for any bean
    * that wishes to support property change listeners. Unfortunately, a class that
    * already has another superclass—such as JComponent—must reimplement
    * the methods. Reimplement PropertyChangeSupport as a trait, and mix it into the
    * java.awt.Point class.
    */
  test("5") {
    trait ScalaPropertyChangeSupport {
      val pcs = new PropertyChangeSupport(this)

      def addPropertyChangeListener(listener: PropertyChangeListener): Unit = { pcs.addPropertyChangeListener(listener) }
      def addPropertyChangeListener(propertyName: String, listener: PropertyChangeListener): Unit = { pcs.addPropertyChangeListener(propertyName, listener) }
      def	fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Boolean, newValue: Boolean) { pcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue) }
      def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Int, newValue: Int) { pcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue) }
      def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Object, newValue: Object) { pcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue) }
      def firePropertyChange(evt: PropertyChangeEvent) { pcs.firePropertyChange(evt) }
      def	firePropertyChange(propertyName: String, oldValue: Boolean, newValue: Boolean) { pcs.firePropertyChange(propertyName, oldValue, newValue) }
      def	firePropertyChange(propertyName: String, oldValue: Int, newValue: Int) { pcs.firePropertyChange(propertyName, oldValue, newValue) }
      def	firePropertyChange(propertyName: String, oldValue: Object, newValue: Object) { pcs.firePropertyChange(propertyName, oldValue, newValue) }
      def getPropertyChangeListeners: Array[PropertyChangeListener] = pcs.getPropertyChangeListeners()
      def getPropertyChangeListeners(propertyName: String): Array[PropertyChangeListener] = pcs.getPropertyChangeListeners(propertyName)
      def hasListeners(propertyName: String): Boolean = pcs.hasListeners(propertyName)
      def removePropertyChangeListener(listener: PropertyChangeListener): Unit = pcs.removePropertyChangeListener(listener)
      def	removePropertyChangeListener(propertyName: String, listener: PropertyChangeListener): Unit = pcs.removePropertyChangeListener(listener)
    }

    class Listener extends PropertyChangeListener {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        println(s"(${evt.getSource.asInstanceOf[Point].x}, ${evt.getSource.asInstanceOf[Point].y})")
      }
    }

    trait BeansPoint extends Point {
      this: ScalaPropertyChangeSupport =>
      override def setLocation(x: Int, y: Int): Unit = {
        pcs.firePropertyChange("setLocation", (getX, getY), (x, y))
        super.setLocation(x, y)
      }
    }

    val listener = new Listener
    val p = new Point(1, 1) with BeansPoint with ScalaPropertyChangeSupport
    p.addPropertyChangeListener("setLocation", listener)
    p.setLocation(2, 3)
  }

  /**
    * In the Java AWT library, we have a class Container, a subclass of Component that
    * collects multiple components. For example, a Button is a Component, but a Panel
    * is a Container. That’s the composite pattern at work. Swing has JComponent and
    * JButton, but if you look closely, you will notice something strange. JComponent
    * extends Container, even though it makes no sense to add other components to,
    * say, a JButton. Ideally, the Swing designers would have preferred the design
    * in Figure 10–4.
    * But that’s not possible in Java. Explain why not. How could the design be
    * executed in Scala with traits?
    */
  test("6") {
    trait Component {}
    trait JComponent extends Component {}
    class JButton extends JComponent {}
    trait Container extends Component {}
    trait JContainer extends Container with JComponent {}
    class JPanel extends JContainer {}
  }

  /**
    * There are dozens of Scala trait tutorials with silly examples of barking dogs
    * or philosophizing frogs. Reading through contrived hierarchies can be tedious
    * and not very helpful, but designing your own is very illuminating. Make
    * your own silly trait hierarchy example that demonstrates layered traits,
    * concrete and abstract methods, and concrete and abstract fields.
    */
  test("8") {
    trait Shoes {
      val price: Double
      val brand: String = ""
      def desc: String = s"These $brand shoes' price is $price."
      def goSomewhere()
    }
    trait SportsShoes {}
    trait CasualShoes {}
    trait DressShoes {}
    trait Leather {}
    trait Suede {}
    trait Canvas {}
    class AirJordan12 extends Shoes with Leather with SportsShoes {
      val price = 220.0
      override val brand = "nike"
      override def goSomewhere(): Unit = { println("basketball court") }
    }

    val aj = new AirJordan12
    println(aj.desc)
    aj.goSomewhere()
  }

  /**
    * In the java.io library, you add buffering to an input stream with a
    * BufferedInputStream decorator. Reimplement buffering as a trait. For simplicity,
    * override the read method.
    */
  test("9") {
    trait Buffering {
      this: InputStream =>

      val BUF_SIZE: Int = 5
      private val buf = new Array[Byte](BUF_SIZE)
      private var bufSize: Int = 0
      private var pos: Int = 0

      override def read(): Int = {
        if(pos >= bufSize) {
          bufSize = this.read(buf, 0, BUF_SIZE)
          if (bufSize > 0) -1
          pos = 0
        }
        pos += 1
        buf(pos - 1)
      }
    }

    val fis = new FileInputStream("ch10/src/test/resources/09.txt") with Buffering
    for (i <- 1 to 10) println(fis.read().toChar)
    fis.close()
  }

  /**
    * Using the logger traits from this chapter, add logging to the solution of the
    * preceding problem that demonstrates buffering.
    */
  test("10") {
    trait Logger {
      def log(msg: String)
    }

    trait NoneLogger extends Logger {
      def log(msg: String): Unit = {}
    }

    trait PrintLogger extends Logger {
      def log(msg: String): Unit = println(msg)
    }

    trait Buffering {
      this: InputStream with Logger =>

      val BUF_SIZE: Int = 5
      private val buf = new Array[Byte](BUF_SIZE)
      private var bufSize: Int = 0
      private var pos: Int = 0

      override def read(): Int = {
        if (pos >= bufSize) {
          bufSize = this.read(buf, 0, BUF_SIZE)
          log("buffered %d bytes: %s".format(bufSize, buf.mkString(", ")))
          if (bufSize > 0) -1
          pos = 0
        }
        pos += 1
        buf(pos-1)
      }
    }

    val fis = new FileInputStream("ch10/src/test/resources/10.txt") with Buffering with PrintLogger
    for (i <- 1 to 10) println(fis.read().toChar)
    fis.close()
  }

  /**
    * Implement a class IterableInputStream that extends java.io.InputStream with the
    * trait Iterable[Byte].
    */
  test("11") {
    trait IterableInputStream extends java.io.InputStream with Iterable[Byte] {

      class InputStreamIterator(outer: IterableInputStream) extends Iterator[Byte] {
        def hasNext: Boolean = outer.available() > 0
        def next: Byte = outer.read().toByte
      }

      def iterator: Iterator[Byte] = new InputStreamIterator(this)
    }

    val fis = new FileInputStream("ch10/src/test/resources/11.txt") with IterableInputStream
    for (b <- fis) println(b.toChar)
    fis.close()
  }
}
