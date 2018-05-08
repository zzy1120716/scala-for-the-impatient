import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * Write an object Conversions with methods inchesToCentimeters, gallonsToLiters, and
    * milesToKilometers.
    */
  test("1") {
    object Conversions {
      def inchesToCentimeters(inches: Double): Double = inches * 2.54
      def gallonsToLiters(gallons: Double): Double = gallons * 3.78541178
      def milesToKilometers(miles: Double): Double = miles * 1.609344
    }

    assert(Conversions.inchesToCentimeters(1.0) === 2.54)
    assert(Conversions.gallonsToLiters(1.0) === 3.78541178)
    assert(Conversions.milesToKilometers(1.0) === 1.609344)
  }

  /**
    * The preceding problem wasn’t very object-oriented. Provide a general superclass
    * UnitConversion and define objects InchesToCentimeters, GallonsToLiters, and
    * MilesToKilometers that extend it.
    */
  test("2") {
    class UnitConversion(factor: Double) {
      def convert(value: Double): Double = value * factor
    }

    object InchesToCentimeters extends UnitConversion(2.54)
    object GallonsToLiters extends UnitConversion(3.78541178)
    object MilesToKilometers extends UnitConversion(1.609344)

    assert(InchesToCentimeters.convert(1.0) === 2.54)
    assert(GallonsToLiters.convert(1.0) === 3.78541178)
    assert(MilesToKilometers.convert(1.0) === 1.609344)
  }

  /**
   * Define an Origin object that extends java.awt.Point. Why is this not actually a
   * good idea? (Have a close look at the methods of the Point class.)
   */
  test("3") {
    object Origin extends java.awt.Point {}
    // Origin cannot be instantiated, some methods like "setX" will not make effect.
    // and "getX" will always return a 0.0
    assert(Origin.getX === 0.0)
  }

  /**
    * Define a Point class with a companion object so that you can construct Point
    * instances as Point(3, 4), without using new.
    */
  test("4") {
    class Point(x: Int = 0, y: Int = 0) {
      override def toString: String = getClass.getSimpleName + "[x=" + x + ",y=" + y + "]"
    }

    object Point {
      def apply(x: Int = 0, y: Int = 0): Point = new Point(x, y)
    }

    assert(Point(3, 4).toString === "Point$2[x=3,y=4]")
  }

  /**
    * Write a Scala application, using the App trait, that prints the command-line
    * arguments in reverse order, separated by spaces. For example, scala Reverse
    * Hello World should print World Hello.
    */
  test("5") {
    assert(PrintCmdLineArgsReverse.reverseArgs(Array("Hello", "World")) === "World Hello")
    assert(PrintCmdLineArgsReverse.reverseArgs(Array()) === "")
  }

  /**
    * Write an enumeration describing the four playing card suits so that the toString
    * method returns ♣, ♦, ♥, or ♠.
    */
  test("6") {
    object PokerSuits extends Enumeration {
      type PokerSuites = Value
      val Spades: Value = Value("♠")
      val Hearts: Value = Value("♥")
      val Clubs: Value = Value("♣")
      val Diamonds: Value = Value("♦")
    }

    assert(PokerSuits.values.mkString(", ") === "♠, ♥, ♣, ♦")
    println(PokerSuits.Spades.toString === "♠")
    println(PokerSuits.Hearts.toString === "♥")
    println(PokerSuits.Clubs.toString === "♣")
    println(PokerSuits.Diamonds.toString === "♦")
  }

  /**
    * Implement a function that checks whether a card suit value from the preceding
    * exercise is red.
    */
  test("7") {
    object PokerSuits extends Enumeration {
      type PokerSuites = Value
      val Spades: Value = Value("♠")
      val Hearts: Value = Value("♥")
      val Clubs: Value = Value("♣")
      val Diamonds: Value = Value("♦")
      def isRed(suit: Value): Boolean = suit == Hearts || suit == Diamonds
    }

    assert(PokerSuits.isRed(PokerSuits.Spades) === false)
    assert(PokerSuits.isRed(PokerSuits.Hearts) === true)
  }

  /**
    * Write an enumeration describing the eight corners of the RGB color cube. As
    * IDs, use the color values (for example, 0xff0000 for Red).
    */
  test("8") {
    object RGBCube extends Enumeration {
      val black: Value = Value(0x000000, "Black")
      val red: Value = Value(0xff0000, "Red")
      val green: Value = Value(0x00ff00, "Green")
      val blue: Value = Value(0x0000ff, "Blue")
      val yellow: Value = Value(0xffff00, "Yellow")
      val magenta: Value = Value(0xff00ff, "Magenta")
      val cyan: Value = Value(0x00ffff, "Cyan")
      val white: Value = Value(0xffffff, "White")
    }

    var s = ""
    for (c <- RGBCube.values) s += ("0x%06x: %s".format(c.id, c) + "\n")
    assert(s === "0x000000: Black\n0x0000ff: Blue\n0x00ff00: Green\n0x00ffff: Cyan\n0xff0000: Red\n0xff00ff: Magenta\n0xffff00: Yellow\n0xffffff: White\n")
  }
}
