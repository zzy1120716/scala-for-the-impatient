import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class ExercisesTest extends FunSuite {

  /**
    * According to the precedence rules, how are 3 + 4 -> 5 and 3 -> 4 + 5 evaluated?
    */
  test("1") {
    assert(3 + 4 -> 5 === (7 -> 5))
    assert(3 -> (4 + 5) === (3 -> 9))
  }

  /**
    * Implement the Fraction class with operations + - * /. Normalize fractions, for
    * example, turning 15/–6 into –5/2. Divide by the greatest common divisor,
    * like this:
    * class Fraction(n: Int, d: Int) {
    *   private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    *   private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    *   override def toString = s"$num/$den"
    *   def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0
    *   def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)
    *   ...
    * }
    */
  test("3") {
    import math._

    class Fraction(n: Int, d: Int) {
      private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d)
      private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d)
      override def toString = s"$num/$den"
      def sign(a: Int): Int = if (a > 0) 1 else if (a < 0) -1 else 0
      def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

      def +(that: Fraction): Fraction = {
        new Fraction(this.num * that.den + that.num * this.den, this.den * that.den)
      }
      def -(that: Fraction): Fraction = {
        new Fraction(this.num * that.den - that.num * this.den, this.den * that.den)
      }
      def *(that: Fraction): Fraction = {
        new Fraction(this.num * that.num, this.den * that.den)
      }
      def /(that: Fraction): Fraction = {
        new Fraction(this.num * that.den, this.den * that.num)
      }
    }

    object Fraction {
      def apply(n: Int, d: Int) = new Fraction(n, d)
    }

    val x = Fraction(2, 3)
    val y = Fraction(-5, 6)

    assert((x + y).toString === "-1/6")
    assert((x - y).toString === "3/2")
    assert((x * y).toString === "-5/9")
    assert((x / y).toString === "-4/5")
  }

  /**
    * Implement a class Money with fields for dollars and cents. Supply +, - operators
    * as well as comparison operators == and <. For example, Money(1, 75) + Money(0,
    * 50) == Money(2, 25) should be true. Should you also supply * and / operators?
    * Why or why not?
    */
  test("4") {
    class Money(d: BigInt, c: BigInt) {
      private val dollars = d + c / 100
      private val cents = c % 100
      override def toString: String = s"$dollars.$cents"
      def toCent: BigInt = dollars * 100 + cents
      def fromCent(cents: BigInt) = new Money(cents / 100, cents % 100)
      def +(that: Money): Money = fromCent(this.toCent + that.toCent)
      def -(that: Money): Money = fromCent(this.toCent - that.toCent)
      def *(a: Int): Money = fromCent(this.toCent * a)
      def /(a: Int): Money = fromCent(this.toCent / a)
      def ==(that: Money): Boolean = this.toCent == that.toCent
      def <(that: Money): Boolean = this.toCent < that.toCent
    }

    object Money {
      def apply(d: Int, c: Int) = new Money(d, c)
    }

    val m1 = Money(1, 75)
    val m2 = Money(0, 50)
    assert(m1.toString === "1.75")
    assert(m2.toString === "0.50")
    assert(m2 < m1)
    assert(m1 + m2 == Money(2, 25))
    assert(m1 - m2 == Money(1, 25))
    assert(m1 * 3 == Money(5, 25))
    assert(m2 / 2 == Money(0, 25))
  }

  /**
    * Provide operators that construct an HTML table. For example,
    *   Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET"
    * should produce
    * <table><tr><td>Java</td><td>Scala</td></tr><tr><td>Gosling</td><td>Odersky</td></tr><tr><td>JVM</td><td>JVM, .NET</td></tr></table>
    */
  test("5") {

    class Table {
      private val trs: ArrayBuffer[Tr] = new ArrayBuffer[Tr]
      private def append(content: String): Unit = {
        val tr = Tr(); tr.add(content); trs.append(tr)
      }
      def |(content: String): Table = {
        if (trs.isEmpty) {
          append(content)
        } else {
          trs(trs.length - 1).add(content)
        }
        this
      }
      def ||(content: String): Table = {
        append(content)
        this
      }
      override def toString: String = s"<table>${trs.mkString}</table>"
    }

    class Tr {
      private val tds: ArrayBuffer[Td] = new ArrayBuffer[Td]
      def add(content: String): Unit = this.tds.append(Td(content))
      override def toString: String = s"<tr>${tds.mkString}</tr>"
    }

    class Td(c: String) {
      private val contents = c
      override def toString: String = s"<td>$contents</td>"
    }

    object Table {
      def apply(): Table = new Table()
    }

    object Tr {
      def apply(): Tr = new Tr()
    }

    object Td {
      def apply(c: String): Td = new Td(c)
    }

    println(Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET")
    assert((Table() | "a" | "b" || "c" | "d").toString === "<table><tr><td>a</td><td>b</td></tr><tr><td>c</td><td>d</td></tr></table>")
    assert((Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET").toString === "<table><tr><td>Java</td><td>Scala</td></tr><tr><td>Gosling</td><td>Odersky</td></tr><tr><td>JVM</td><td>JVM, .NET</td></tr></table>")
  }

  /**
    * Provide a class ASCIIArt whose objects contain figures such as
    *  /\_/\
    * ( ' ' )
    * (  -  )
    *  | | |
    * (__|__)
    * Supply operators for combining two ASCIIArt figures horizontally
    *  /\_/\     -----
    * ( ' ' )  / Hello \
    * (  -  ) <  Scala |
    *  | | |   \ Coder /
    * (__|__)    -----
    * or vertically. Choose operators with appropriate precedence.
    */
  test("6") {
    class ASCIIArt(val _lines: Array[String]) {

      def lines: Array[String] = _lines

      def this(s: String) {
        this(s.split("\r\n"))
      }

      def +(other: ASCIIArt): ASCIIArt = {
        //art.split("\n").zip(other.art.split("\n")).map(x => x._1 + x._2).mkString("\n")
        new ASCIIArt(
          (for (k <- _lines.indices) yield if (other.lines.isDefinedAt(k)) _lines(k) + other.lines(k) else _lines(k)).toArray
        )
      }

      def /(other: ASCIIArt): ASCIIArt = {
        new ASCIIArt(
          _lines ++ other.lines
        )
      }

      override def toString: String = _lines.mkString("\n")
    }

    val x = new ASCIIArt(
      """ /\_/\
( ' ' )
(  -  )
 | | |
(__|__)""")

    val y = new ASCIIArt(
      """   -----
 / Hello \
<  Scala |
 \ Coder /
   -----""")

    println(x / y + y)
  }

  /**
    * Implement a class BitSequence that stores a sequence of 64 bits packed in a Long
    * value. Supply apply and update operators to get and set an individual bit.
    */
  test("7") {
    class BitSequence(private var value: Long = 0) {
      implicit def bool2int(b: Boolean): Int = if (b) 1 else 0
      def update(bit: Int, state: Int): Unit = value |= (state & 1L) << bit % 64
      def apply(bit: Int): Int = if ((value & 1L << bit % 64) > 0) 1 else 0
      override def toString: String = f"${value.toBinaryString.replace(" ", "0")}%64s"
    }

    val x = new BitSequence()
    assert(x.toString === "%64s".format("0"))
    x(5) = 1
    assert(x.toString === "%64s".format("100000"))
    x(63) = 1
    x(64) = 1
    assert(x(5) === 1)
    assert(x.toString === "1000000000000000000000000000000000000000000000000000000000100001")
  }

  /**
    * Provide a class Matrix. Choose whether you want to implement 2 × 2 matrices,
    * square matrices of any size, or m × n matrices. Supply operations + and *. The
    * latter should also work with scalars, for example, mat * 2. A single element
    * should be accessible as mat(row, col).
    */
  test("8") {

    class Matrix(val m: Int, val n: Int) {

      def this(value: Array[Array[Double]]) {
        this(value.length, value(0).length)
        _value = value
      }

      private var _value = Array.ofDim[Double](m, n)
      def value: Array[Array[Double]] = _value
      def apply(x: Int, y: Int): Double = value(x)(y)
      def update(x: Int, y: Int, v: Double): Unit = value(x)(y) = v

      def +(other: Matrix): Matrix = {
        require (m == other.m)
        require (n == other.n)
        val res = new Matrix(m, n)
        for (i <- 0 until m; j <- 0 until n) res(i, j) = this.value(i)(j) + other.value(i)(j)
        res
      }

      def -(other: Matrix): Matrix = this + other * -1

      def *(factor: Double): Matrix = {
        val res = new Matrix(m, n)
        for (i <- 0 until m; j <- 0 until n) {
          res(i, j) = this.value(i)(j) * factor
        }
        res
      }

      def *(other: Matrix): Matrix = {
        val s = other.n
        require (n == other.m)
        val res = new Matrix(m, s)
        for (i <- 0 until m; j <- 0 until s) {
          res(i, j) = 0
          for (k <- 0 until n) {
            res(i, j) += this.value(i)(k) * other.value(k)(j)
          }
        }
        res
      }

      override def toString: String = value.map(_.mkString(" ")).mkString("\n")
    }

    val x = new Matrix(Array(Array(5, 2, 4), Array(3, 8, 2), Array(6, 0, 4), Array(0, 1, 6)))
    val y = new Matrix(Array(Array(2, 4), Array(1, 3), Array(3, 2)))
    val z = new Matrix(Array(Array(1, 5), Array(7, 4), Array(9, 0)))

    assert((y * 2).toString === "4.0 8.0\n2.0 6.0\n6.0 4.0")
    assert((x * y).toString === "24.0 34.0\n20.0 40.0\n24.0 32.0\n19.0 15.0")
    assert((y + z).toString === "3.0 9.0\n8.0 7.0\n12.0 2.0")
    assert((y - z).toString === "1.0 -1.0\n-6.0 -1.0\n-6.0 2.0")
  }

  /**
    * Define an object PathComponents with an unapply operation class that extracts
    * the directory path and file name from an java.nio.file.Path. For example, the
    * file /home/cay/readme.txt has directory path /home/cay and file name readme.txt.
    */
  test("9") {
    object PathComponents {
      def unapply(filePath: String): Option[(String, String, String)] = {
        val posFile = filePath.lastIndexOf("/")
        val posExt = filePath.indexOf(".")
        if (posFile == -1 || posExt == -1) None
        else Some((filePath.substring(0, posFile), filePath.substring(posFile + 1, posExt), filePath.substring(posExt + 1)))
      }
    }
    val PathComponents(path, fileName, extension) = "/home/cay/readme.txt"
    assert(path === "/home/cay")
    assert(fileName === "readme")
    assert(extension === "txt")
  }

  /**
    * Modify the PathComponents object of the preceding exercise to instead define an
    * unapplySeq operation that extracts all path segments. For example, for the file
    * /home/cay/readme.txt, you should produce a sequence of three segments: home,
    * cay, and readme.txt.
    */
  test("10") {
    object PathComponents {
      def unapplySeq(filePath: String): Option[Seq[String]] = {
        if (filePath.trim == "") None else Some(filePath.trim.split("/"))
      }
    }

    val filePath = "/home/cay/readme.txt"
    val paths = new ArrayBuffer[String]
    filePath match {
      case PathComponents("", root, user, fileName) => paths.append(root); paths.append(user); paths.append(fileName)
      case _ => throw new MatchError
    }

    assert(paths.mkString(" ") === "home cay readme.txt")
  }
}
