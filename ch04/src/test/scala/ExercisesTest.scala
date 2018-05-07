import java.util

import org.scalatest.FunSuite

import scala.io.Source
import collection.JavaConverters._

class ExercisesTest extends FunSuite {

  /**
    * Set up a map of prices for a number of gizmos that you covet. Then produce
    * a second map with the same keys and the prices at a 10 percent discount.
    */
  test("1") {
    val myGizmos = Map[String, Double]("Converse One Star" -> 569, "Air Force 1" -> 799, "Air Jordan 3" -> 1399, "Yeezy 500" -> 1899)
    val myGizmosDiscount = for ((k, v) <- myGizmos) yield (k, BigDecimal(v * 0.9).setScale(2, BigDecimal.RoundingMode.HALF_EVEN))
    assert(myGizmos.mkString(", ") === "Converse One Star -> 569.0, Air Force 1 -> 799.0, Air Jordan 3 -> 1399.0, Yeezy 500 -> 1899.0")
    assert(myGizmosDiscount.mkString(", ") === "Converse One Star -> 512.10, Air Force 1 -> 719.10, Air Jordan 3 -> 1259.10, Yeezy 500 -> 1709.10")
  }

  /**
    * Write a program that reads words from a file. Use a mutable map to count
    * how often each word appears. To read the words, simply use a java.util.Scanner:
    * val in = new java.util.Scanner(java.io.File("myfile.txt"))
    * while (in.hasNext()) process in.next()
    * Or look at Chapter 9 for a Scalaesque way.
    * At the end, print out all words and their counts.
    */
  test("2") {
    val source = Source.fromResource("myfile.txt")
    val tokens = source.mkString.split("\\s+")

    val freq = new scala.collection.mutable.HashMap[String, Int]

    for (token <- tokens) freq(token) = freq.getOrElse(token, 0) + 1
    assert(freq.mkString(", ") === "To -> 1, is -> 1, of -> 1, not -> 1, or -> 1, arrows -> 1, mind -> 1, fortune -> 1, to -> 2, and -> 1, that -> 1, question: -> 1, Whether -> 1, in -> 1, The -> 1, suffer -> 1, the -> 2, slings -> 1, outrageous -> 1, 'tis -> 1, nobler -> 1, be, -> 2")
    source.close()
  }

  /**
    * Repeat the preceding exercise with an immutable map.
    */
  test("3") {
    var freq = Map[String, Int]()
    val source = Source.fromResource("myfile.txt")
    val tokens = source.mkString.split("\\s+")

    for (token <- tokens) freq += (token -> (freq.getOrElse(token, 0) + 1))
    assert(freq.mkString(", ") === "in -> 1, is -> 1, arrows -> 1, nobler -> 1, Whether -> 1, or -> 1, that -> 1, to -> 2, slings -> 1, The -> 1, mind -> 1, question: -> 1, outrageous -> 1, not -> 1, To -> 1, fortune -> 1, be, -> 2, suffer -> 1, 'tis -> 1, of -> 1, and -> 1, the -> 2")
    source.close()
  }

  /**
    * Repeat the preceding exercise with a sorted map, so that the words are
    * printed in sorted order.
    */
  test("4") {
    var freq = collection.mutable.SortedMap[String, Int]()
    val source = Source.fromResource("myfile.txt")
    val tokens = source.mkString.split("\\s+")

    for (token <- tokens) freq += (token -> (freq.getOrElse(token, 0) + 1))
    assert(freq.mkString(", ") === "'tis -> 1, The -> 1, To -> 1, Whether -> 1, and -> 1, arrows -> 1, be, -> 2, fortune -> 1, in -> 1, is -> 1, mind -> 1, nobler -> 1, not -> 1, of -> 1, or -> 1, outrageous -> 1, question: -> 1, slings -> 1, suffer -> 1, that -> 1, the -> 2, to -> 2")
    source.close()
  }

  /**
    * Repeat the preceding exercise with a java.util.TreeMap that you adapt to the
    * Scala API.
    */
  test("5") {
    var freq = new util.TreeMap[String, Int].asScala
    val source = Source.fromResource("myfile.txt")
    val tokens = source.mkString.split("\\s+")

    for (token <- tokens) freq += (token -> (freq.getOrElse(token, 0) + 1))
    assert(freq.mkString(", ") === "'tis -> 1, The -> 1, To -> 1, Whether -> 1, and -> 1, arrows -> 1, be, -> 2, fortune -> 1, in -> 1, is -> 1, mind -> 1, nobler -> 1, not -> 1, of -> 1, or -> 1, outrageous -> 1, question: -> 1, slings -> 1, suffer -> 1, that -> 1, the -> 2, to -> 2")
    source.close()
  }

  /**
    * Define a linked hash map that maps "Monday" to java.util.Calendar.MONDAY, and
    * similarly for the other weekdays. Demonstrate that the elements are visited
    * in insertion order.
    */
  test("6") {
    val weekdays = scala.collection.mutable.LinkedHashMap[String, Int]()
    val list = classOf[java.util.Calendar].getFields.filter(x => x.getName.endsWith("DAY") && x.getInt(null) != 11)
    list foreach {
      day => weekdays += (day.getName -> day.getInt(null))
    }
    assert(weekdays.mkString(", ") === "SUNDAY -> 1, MONDAY -> 2, TUESDAY -> 3, WEDNESDAY -> 4, THURSDAY -> 5, FRIDAY -> 6, SATURDAY -> 7")
  }

  /**
    * Print a table of all Java properties, like this:
    * java.runtime.name           | Java(TM) SE Runtime Environment
    * sun.boot.library.path       | /home/apps/jdk1.6.0_21/jre/lib/i386
    * java.vm.version             | 17.0-b16
    * java.vm.vendor              | Sun Microsystems Inc.
    * java.vendor.url             | http://java.sun.com/
    * path.separator              | :
    * java.vm.name                | Java HotSpot(TM) Server VM
    * You need to find the length of the longest key before you can print the table.
    */
  test("7") {
    assert(PrintAllJavaPropsAsTable.getLongestKeyLen(System.getProperties.asScala) === 29)
  }

  /**
    * Write a function minmax(values: Array[Int]) that returns a pair containing the
    * smallest and largest values in the array.
    */
  test("8") {
    def minmax(values: Array[Int]): Tuple2[Int, Int] = {
      (values.min, values.max)
    }
    assert(minmax(Array(1, 2, 3, 4, 5)) === (1, 5))
  }

  /**
    * Write a function lteqgt(values: Array[Int], v: Int) that returns a triple containing
    * the counts of values less than v, equal to v, and greater than v.
    */
  test("9") {
    def lteqgt(values: Array[Int], v: Int): Tuple3[Int, Int, Int] = {
      (values.count(_ < v), values.count(_ == v), values.count(_ > v))
    }
    assert(lteqgt(Array(1, 2, 3, 4, 5), 3) === (2, 1, 2))
  }

  /**
    * What happens when you zip together two strings, such as "Hello".zip("World")?
    * Come up with a plausible use case.
    */
  test("10") {
    assert("Hello".zip("World").mkString(", ") === "(H,W), (e,o), (l,r), (l,l), (o,d)")

    val caseConverter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".zip("abcdefghijklmnopqrstuvwxyz").toMap
    assert(caseConverter('Z') === 'z')
  }
}
