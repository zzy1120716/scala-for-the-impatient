import org.scalatest.FunSuite

import collection.mutable.{Buffer, ArrayBuffer}
import collection.JavaConverters._

class ArrayTest extends FunSuite {

  test("fixed-length array") {
    val nums = new Array[Int](10)
    for (i <- 0 until 10) assert(nums(i) === 0)

    val a = new Array[String](10)
    for (i <- 0 until 10) assert(a(i) === null)

    val s = Array("Hello", "World") // no "new" when you supply initial values
    assert(s.length === 2)
    s(0) = "Goodbye"
    assert(s === Array("Goodbye", "World"))
  }

  test("array buffer") {
    val b = ArrayBuffer[Int]()
    b += 1
    assert(b === ArrayBuffer(1))

    b += (1, 2, 3, 5)
    assert(b === ArrayBuffer(1, 1, 2, 3, 5))

    b ++= Array(8, 13, 21)
    assert(b === ArrayBuffer(1, 1, 2, 3, 5, 8, 13, 21))

    b.trimEnd(5)
    assert(b === ArrayBuffer(1, 1, 2))  // amortized constant time

    b.insert(2, 6)
    assert(b === ArrayBuffer(1, 1, 6, 2))

    b.insert(2, 7, 8, 9)
    assert(b === ArrayBuffer(1, 1, 7, 8, 9, 6, 2))

    b.remove(2)
    assert(b === ArrayBuffer(1, 1, 8, 9, 6, 2))

    b.remove(2, 3)
    assert(b === ArrayBuffer(1, 1, 2))

    // Array and ArrayBuffer convert to each other
    val a = b.toArray
    assert(a === Array(1, 1, 2))

    assert(a.toBuffer === ArrayBuffer(1, 1, 2))
  }

  test("traversing") {
    // use until to traverse array
    val a = Array(123, 132, 213, 231, 312, 321)
    var s = ""
    for (i <- 0 until a.length)
      if (i == a.length - 1) s += i + ": " + a(i) else s += i + ": " + a(i) + ", "
    assert(s === "0: 123, 1: 132, 2: 213, 3: 231, 4: 312, 5: 321")

    assert((0 until 10) === Range(0, 10))

    // step 2
    val b = Array(0, 1, 2, 3, 4, 5, 6, 7, 8)
    var r = 0
    for (i <- 0 until (b.length, 2)) r += b(i)
    assert(r === 20)

    // reverse traverse
    var str = ""
    for (i <- (0 until b.length).reverse) str += b(i) + " "
    assert(str === "8 7 6 5 4 3 2 1 0 ")

    // enhanced for loop
    r = 0
    for (elem <- b) r += elem
    assert(r === 36)
  }

  test("transforming array") {
    // for...yield
    val a = Array(2, 3, 5, 7, 11)
    var result = for (elem <- a) yield 2 * elem
    assert(result === Array(4, 6, 10, 14, 22))

    // use guard as a filter
    result = for (elem <- a if elem % 2 == 0) yield 2 * elem
    assert(result === Array(4))

    // filter...map
    result = a.filter(_ % 2 == 0).map(2 * _)
    assert(result === Array(4))
    result = a.filter { _ % 2 == 0 } map { 2 * _ }
    assert(result === Array(4))

    // remove all but the first negative number
    // solution #1 -- use a flag
    val b = ArrayBuffer(-1, 2, -3, 4, -5)
    var first = true
    var n = b.length
    var i = 0
    while (i < n) {
      if (b(i) >= 0) i += 1
      else {
        if (first) { first = false; i += 1 }
        else { b.remove(i); n -= 1 }
      }
    }
    assert(b === ArrayBuffer(-1, 2, 4))

    // solution #2 -- start from the back after finding the first match
    first = true
    val c = ArrayBuffer(-1, 2, -3, 4, -5)
    val indexes = for (i <- 0 until c.length if first || c(i) >= 0) yield {
      if (c(i) < 0) first = false; i
    }
    for (j <- 0 until indexes.length) c(j) = c(indexes(j))
    c.trimEnd(c.length - indexes.length)
    assert(c === ArrayBuffer(-1, 2, 4))
  }

  test("common algorithm") {
    // sum
    assert(Array(1, 7, 2, 9).sum === 19)

    // max & min
    assert(ArrayBuffer("Mary", "had", "a", "little", "lamb").max === "little")
    assert(ArrayBuffer("Mary", "had", "a", "little", "lamb").min === "Mary")

    // sorted
    val b = ArrayBuffer(1, 7, 2, 9)
    val bSorted = b.sorted
    assert(b === ArrayBuffer(1, 7, 2, 9))
    assert(bSorted === ArrayBuffer(1, 2, 7, 9))

    // sortWith
    val bDescending = b.sortWith(_>_)
    assert(bDescending === ArrayBuffer(9, 7, 2, 1))

    // quickSort
    val a = Array(1, 7, 2, 9)
    util.Sorting.quickSort(a)
    assert(a === Array(1, 2, 7, 9))

    // mkString
    assert(a.mkString(" and ") === "1 and 2 and 7 and 9")
    assert(a.mkString("<", ",", ">") === "<1,2,7,9>")

    // toString (a is Array, b is ArrayBuffer)
    assert(a.toString === "[I@25a65b77")
    assert(b.toString === "ArrayBuffer(1, 7, 2, 9)")
  }

  test("deciphering scaladoc") {
    // append
    val b = new ArrayBuffer[Int]()
    b.append(1, 7, 2, 9)
    assert(b === ArrayBuffer(1, 7, 2, 9))

    // appendAll
    b.appendAll(Seq(8, 5, 6))
    b.appendAll(Array(3))
    assert(b === ArrayBuffer(1, 7, 2, 9, 8, 5, 6, 3))

    // count
    assert(b.count(_>7) === 2)

    // +=
    assert((b += 4 -= 5) === ArrayBuffer(1, 7, 2, 9, 8, 6, 3, 4))

    // copyToArray
    val a = new Array[Int](b.length)
    b.copyToArray(a)
    assert(a === Array(1, 7, 2, 9, 8, 6, 3, 4))

    // max
    assert(a.max === 9)

    // padTo
    val c = b.padTo(b.length + 5, 0)
    assert(c === ArrayBuffer(1, 7, 2, 9, 8, 6, 3, 4, 0, 0, 0, 0, 0))
  }

  test("multidimensional array") {
    // construct a 2d array
    val matrix = Array.ofDim[Double](3, 4)  // three rows, four columns
    assert(matrix(0)(0) == 0.0)
    assert(matrix(2)(3) == 0.0)
    intercept[ArrayIndexOutOfBoundsException] {
      println(matrix(3)(3))
    }

    // ragged array
    val triangle = new Array[Array[Int]](10)  // 10 rows in total
    for (i <- 0 until triangle.length) {
      triangle(i) = new Array[Int](i + 1)
    }
    assert(triangle(0).length === 1)
    assert(triangle(1).length === 2)
    assert(triangle(2).length === 3)
    assert(triangle(3).length === 4)
    assert(triangle(4).length === 5)
    assert(triangle(5).length === 6)
    assert(triangle(6).length === 7)
    assert(triangle(7).length === 8)
    assert(triangle(8).length === 9)
    assert(triangle(9).length === 10)
    intercept[ArrayIndexOutOfBoundsException] {
      println(triangle(10).length)
    }
  }

  test("interoperating with java") {
    val command = ArrayBuffer("ls", "-al", "/home/zzy")
    val pb = new ProcessBuilder(command.asJava)  // scala to java

    val cmd: Buffer[String] = pb.command().asScala  // java to scala

    assert(cmd === command) // the implicit conversion unwraps the original object
  }
}
