import java.awt.Color

import org.scalatest.FunSuite

import scala.collection.SortedSet
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class CollectionTest extends FunSuite {

  test("the main collections traits") {
    val col1 = Array(1, 2, 3)
    val iter = col1.iterator
    while (iter.hasNext)
      println(iter.next)

    // uniform creation principle
    val colorIter = Iterable(0xff, 0xff00, 0xff0000)
    val colorSet = Set(Color.RED, Color.GREEN, Color.BLUE)
    val colorMap = Map(Color.RED -> 0xff0000, Color.GREEN -> 0xff00, Color.BLUE -> 0xff)
    val colorSortedSet = SortedSet("Hello", "World")
    assert(colorIter.mkString(", ") === "255, 65280, 16711680")
    assert(colorSet.mkString(", ") === "java.awt.Color[r=255,g=0,b=0], java.awt.Color[r=0,g=255,b=0], java.awt.Color[r=0,g=0,b=255]")
    assert(colorMap.mkString(", ") === "java.awt.Color[r=255,g=0,b=0] -> 16711680, java.awt.Color[r=0,g=255,b=0] -> 65280, java.awt.Color[r=0,g=0,b=255] -> 255")
    assert(colorSortedSet.mkString(", ") === "Hello, World")

    // translate between collection types
    val col2 = Seq(1, 1, 2, 3, 5, 8, 13)
    val set = col2.toSet
    val buffer = col2.to[ArrayBuffer]
    assert(col2 === Seq(1, 1, 2, 3, 5, 8, 13))
    assert(set === Set(1, 1, 2, 3, 5, 8, 13))
    assert(buffer === ArrayBuffer(1, 1, 2, 3, 5, 8, 13))

    // compare collection types with each other
    assert(Seq(1, 2, 3) === (1 to 3))
    assert((Seq(1, 2, 3) === Set(1, 2, 3)) === false)
    assert(Seq(1, 2, 3) sameElements Set(1, 2, 3))
  }

  test("mutable and immutable collections") {
    import scala.collection.mutable
    // get immutable map and mutable map
    val m1 = mutable.Map[String, String]()
    assert(m1.getClass.toString === "class scala.collection.mutable.HashMap")
    val m2 = Map[String, String]("Fred" -> "M")
    assert(m2.getClass.toString === "class scala.collection.immutable.Map$Map1")
    // create new collections out of old ones
    var numbers = Set(5)
    numbers = numbers + 9
    assert(numbers === Set(5, 9))
    // compute the set of all digits of an integer
    def digits(n: Int): Set[Int] =
      if (n < 0) digits(-n)
      else if (n < 10) Set(n)
      else digits(n / 10) + (n % 10)
    assert(digits(59365) === Set(3, 5, 6, 9))
  }

  test("sequences") {
    val v = Vector(1, 2, 3, 4)
    assert(v(0) === 1)
    assert(Range(0, 11) === (0 to 10))
    assert(Range(0, 10) === (0 until 10))
  }

  test("lists") {
    val digits = List(4, 2)
    assert(digits.head === 4)
    assert(digits.tail === List(2))
    assert(digits.tail.head === 2)
    assert(digits.tail.tail === Nil)

    assert(9 :: digits === List(9, 4, 2))
    assert(9 :: 4 :: 2 :: Nil === List(9, 4, 2))
    assert(9 :: (4 :: (2 :: Nil)) === List(9, 4, 2))

    def mySum(lst: List[Int]): Int = {
      if (lst == Nil) 0 else lst.head + mySum(lst.tail)
    }
    assert(mySum(List(9, 4, 2)) === 15)

    def mySumPatternMatching(lst: List[Int]): Int = {
      lst match {
        case Nil => 0
        case h :: t => h + mySumPatternMatching(t)  // h is lst.head, t is lst.tail
      }
    }
    assert(mySumPatternMatching(List(9, 4, 2)) === 15)

    assert(List(9, 4, 2).sum === 15)

    val digitBuffer = ListBuffer(4, 2)
    digitBuffer.append(9)
    assert(digitBuffer === ListBuffer(4, 2, 9))
    digitBuffer.remove(2)
    assert(digitBuffer === ListBuffer(4, 2))
  }

  test("sets") {
    assert((Set(2, 0, 1) + 1) === Set(2, 0, 1))
    assert(Set(1, 2, 3, 4, 5, 6).mkString(", ") === "5, 1, 6, 2, 3, 4")

    val weekdays = scala.collection.mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")
    assert(weekdays.mkString(", ") === "Mo, Tu, We, Th, Fr")

    val digitsSorted = scala.collection.immutable.SortedSet(2, 3, 5, 6, 1, 4)
    assert(digitsSorted.mkString(", ") === "1, 2, 3, 4, 5, 6")

    val alphabet = new java.util.TreeSet[Char]()
    alphabet.add('b')
    alphabet.add('c')
    alphabet.add('a')
    import scala.collection.JavaConverters.asScalaSet
    assert(asScalaSet(alphabet).mkString(", ") === "a, b, c")

    val digits = Set(1, 7, 2, 9)
    assert((digits contains 0) === false)
    assert(Set(1, 2) subsetOf digits)

    val primes = Set(2, 3, 5, 7)
    // union
    assert((digits union primes) === Set(1, 2, 3, 5, 7, 9))
    assert((digits | primes) === Set(1, 2, 3, 5, 7, 9))
    assert((digits ++ primes) === Set(1, 2, 3, 5, 7, 9))
    // intersect
    assert((digits intersect  primes) === Set(2, 7))
    assert((digits & primes) === Set(2, 7))
    // diff
    assert((digits diff primes) === Set(1, 9))
    assert((digits &~ primes) === Set(1, 9))
    assert((digits -- primes) === Set(1, 9))
  }

  test("operators for adding or removing elements") {
    assert((Vector(1, 2, 3) :+ 5) === Vector(1, 2, 3, 5))
    assert((1 +: Vector(1, 2, 3)) === Vector(1, 1, 2, 3))

    val numberBuf = ArrayBuffer(1, 2, 3)
    numberBuf += 5
    assert(numberBuf === ArrayBuffer(1, 2, 3, 5))
    numberBuf -= 5

    var numberSet = Set(1, 2, 3)
    numberSet += 5
    assert(numberSet === Set(1, 2, 3, 5))

    var numberVector = Vector(1, 2, 3)
    numberVector :+= 5
    assert(numberVector === Vector(1, 2, 3, 5))

    assert((Set(1, 2, 3) - 2) === Set(1, 3))

    val numberBuf2 = ArrayBuffer(4, 5, 6)
    assert((numberBuf ++ numberBuf2) === ArrayBuffer(1, 2, 3, 4, 5, 6))
  }

  test("common methods") {
    val str = "Hello, World!"
    assert(str.contains("ll"))
    assert(str.containsSlice("Hello"))
    assert(str.startsWith("He"))
    assert(str.endsWith("d!"))
    assert(str.indexOf(",") === 5)
    assert(str.lastIndexOf("l") === 10)
    assert(str.indexOfSlice("World") === 7)
    assert(str.lastIndexOfSlice("l") === 10)
    assert(str.indexWhere(_.isUpper) === 0)
    assert(str.prefixLength(_.toInt >= 65) === 5)
    assert(str.segmentLength(_.toInt >= 65, 2) === 3)
    assert(str.padTo(20, '!') === "Hello, World!!!!!!!!")
    assert(str.intersect("Woo Hi") === "Ho Wo")
    assert(str.diff("Woo Hi") === "ell,rld!")
    assert(str.reverse === "!dlroW ,olleH")
    assert(str.sorted === " !,HWdellloor")
    assert(str.sortWith(_ > _) === "roollledWH,! ")
    assert(str.sortBy(_.toInt % 5) === "deooH Wlll!,r")
    assert(str.permutations.next === "Hellloo, Wrd!")
    assert(str.combinations(3).next === "Hel")
  }

  test("mapping a function") {
    val names = List("Peter", "Paul", "Mary")
    assert(names.map(_.toUpperCase).mkString(", ") === "PETER, PAUL, MARY")
    val namesUpper = for (n <- names) yield n.toUpperCase
    assert(namesUpper.mkString(", ") === "PETER, PAUL, MARY")

    def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())
    assert(names.map(ulcase) === List(Vector("PETER", "peter"), Vector("PAUL", "paul"), Vector("MARY", "mary")))
    assert(names.flatMap(ulcase) === List("PETER", "peter", "PAUL", "paul", "MARY", "mary"))

    // why are the map and flatMap methods important?
    val m1 = for (i <- 1 to 10) yield i * i
    val m1_ = (1 to 10).map(i => i * i)
    val m2 = for (i <- 1 to 10; j <- 1 to i) yield i * j
    val m2_ = (1 to 10).flatMap(i => (1 to i).map(j => i * j))
    assert(m1 === m1_)
    assert(m2 === m2_)

    // in-place transform
    val buffer = ArrayBuffer("Peter", "Paul", "Mary")
    buffer.transform(_.toUpperCase)
    assert(buffer.mkString(", ") === "PETER, PAUL, MARY")

    names.foreach(println)

    // collect method works with partial functions
    val col = "-3+4".collect { case '+' => 1; case '-' => -1 }
    assert(col === Vector(-1, 1))

    // groupBy
    val words = List("apple", "Asia", "ASCII", "Banana", "bad")
    val map = words.groupBy(_.substring(0, 1).toUpperCase)
    assert(map.mkString(", ") === "A -> List(apple, Asia, ASCII), B -> List(Banana, bad)")
  }

  test("reducing, folding, and scanning") {
    val digits = List(1, 7, 2, 9)
    assert(digits.reduceLeft(_ - _) === -17)
    assert(digits.reduceRight(_ - _) === -13)
    assert(digits.foldLeft(0)(_ - _) === -19)
    assert((0 /: digits)(_ - _) === -19)
    assert(digits.foldRight(0)(_ - _) === -13)
    assert((digits :\ 0)(_ - _) === -13)

    // count the frequencies of the letters in a string
    val freq = scala.collection.mutable.Map[Char, Int]()
    for (c <- "Mississippi") freq(c) = freq.getOrElse(c, 0) + 1
    assert(freq.mkString(", ") === "M -> 1, s -> 4, p -> 2, i -> 4")

    // eliminated loops and mutations
    val freqMap = (Map[Char, Int]() /: "Mississippi") {
      (m, c) => m + (c -> (m.getOrElse(c, 0) + 1))
    }
    assert(freqMap.mkString(", ") === "M -> 1, i -> 4, s -> 4, p -> 2")

    // scanLeft
    assert((1 to 10).scanLeft(0)(_ + _) === Vector(0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55))
  }

  test("zipping") {
    val prices = List(5.0, 20.0, 9.95)
    val quantities = List(10, 2, 1)
    val priceQuantityList = prices zip quantities
    val priceTotalEachProduct = priceQuantityList map { p => p._1 * p._2 }
    assert(priceTotalEachProduct.mkString(", ") === "50.0, 40.0, 9.95")
    val totalPrice = ((prices zip quantities) map { p => p._1 * p._2 }).sum
    assert(totalPrice === 99.95)
    // if one is shorter than another
    assert((List(5.0, 20.0, 9.95) zip List(10, 2)) === List((5.0, 10), (20.0, 2)))
    // zipAll
    assert(List(5.0, 20.0, 9.95).zipAll(List(10, 2), 0.0, 1) === List((5.0, 10), (20.0, 2), (9.95, 1)))
    // zipWithIndex
    assert("Scala".zipWithIndex === Vector(('S', 0), ('c', 1), ('a', 2), ('l', 3), ('a', 4)))
    assert("Scala".zipWithIndex.max === ('l', 3))
    assert("Scala".zipWithIndex.max._2 === 3)
  }

  test("iterators") {
    val iter = scala.io.Source.fromResource("myfile.txt").buffered
    while (iter.hasNext && iter.head.isWhitespace) iter.next
    assert(iter.next === 'h')
  }

  test("streams") {
    def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)
    val tenOrMore = numsFrom(10)
    assert(tenOrMore.toString === "Stream(10, ?)")
    assert(tenOrMore.tail.tail.tail.toString === "Stream(13, ?)")

    val squares = numsFrom(1).map(x => x * x)
    assert(squares.toString === "Stream(1, ?)")

    assert(squares.take(5).force.toString === "Stream(1, 4, 9, 16, 25)")

    // stream caches the visited lines
    val words = scala.io.Source.fromResource("words").getLines.toStream
    assert(words.toString === "Stream(A, ?)")
    assert(words(5) === "Abandon")
    assert(words.toString === "Stream(A, A's, AOL, AOL's, Aachen, Abandon, ?)")
  }

  test("lazy views") {
    import scala.math._
    val powers = (0 until 1000).view.map(pow(10, _))
    assert(powers(100) === 1.0E100)
    //val powerReciprocal = (0 to 1000).map(pow(10, _)).map(1 / _)
    val powerReciprocal = (0 to 1000).view.map(pow(10, _)).map(1 / _).force
    assert(powerReciprocal(100) === 1.0E-100)

    val palindromicSquares = (1 to 1000000).view.map(x => x * x).filter(x => x.toString == x.toString.reverse)
    assert(palindromicSquares.take(10).mkString(", ") === "1, 4, 9, 121, 484, 676, 10201, 12321, 14641, 40804")

    val buffer = ArrayBuffer(1, 2, 3, 4, 5)
    buffer.view(2, 4).transform(_ => 0)
    assert(buffer === ArrayBuffer(1, 2, 0, 0, 5))
  }

  test("interoperability with java collections") {
    //import scala.collection.JavaConversions._
    import scala.collection.JavaConversions.propertiesAsScalaMap
    val props: scala.collection.mutable.Map[String, String] = System.getProperties
    props("com.horstmann.scala") = "impatient"
    assert(props.get("com.horstmann.scala") === Some("impatient"))
  }

  test("parallel collections") {
    val coll = for (i <- 1 to 100000) yield i
    assert(coll.par.sum === 705082704)
    assert(coll.par.count(_ % 2 == 0) === 50000)
    for (i <- (0 until 100000).par) print(s" $i")
    println()
    assert((for (i <- (0 until 100000).par) yield i) == (0 until 100000)) // assembled in order

    // convert a parallel collection back to a sequential one with the seq method
    val result = coll.par.filter(_ % 5 == 0).seq
    println(result)

    // parallelized methods
    assert(coll.par.fold(0)(_ + _) === 705082704)

    // forming a set of all distinct characters in str
    val str = "Mississippi"
    assert(str.par.aggregate(Set[Char]())(_ + _, _ ++ _) === Set('M', 'i', 's', 'p'))
  }
}
