import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
import util.Sorting.quickSort
import java.awt.datatransfer._
import java.util.TimeZone
import collection.JavaConverters

class ExercisesTest extends FunSuite {
  /**
   * Write a code snippet that sets a to an array
   * of n random integers between 0 (inclusive) and n (exclusive).
   */
  test("1") {
    assert(RandomIntArray.getRandomIntArray(10).length === 10)
  }

  /**
    * Write a loop that swaps adjacent elements of an array of integers.
    * For example, Array(1, 2, 3, 4, 5) becomes Array(2, 1, 4, 3, 5).
    */
  test("2") {

    def swap(arr: Array[Int]): Array[Int] = {
      for (i <- 0 until (if (arr.length % 2 == 0) arr.length else arr.length - 1, 2)) {
        val tmp = arr(i)
        arr(i) = arr(i + 1)
        arr(i + 1) = tmp
      }
      arr
    }

    assert(swap(Array(1, 2, 3, 4, 5)) === Array(2, 1, 4, 3, 5))
    assert(swap(Array(1, 2, 3, 4)) === Array(2, 1, 4, 3))
    assert(swap(Array(1)) === Array(1))
    assert(swap(Array()) === Array())
  }

  /**
    * Repeat the preceding assignment,
    * but produce a new array with the swapped values. Use for/yield.
    */
  test("3") {

    def swap(arr: Array[Int]): Array[Int] = {
      val r = for (i <- 0 until arr.length) yield if (arr.length % 2 == 1 && arr(i) == arr.last) arr(i) else if (i % 2 == 0) arr(i + 1) else arr(i - 1)
      r.toArray
    }

    assert(swap(Array(1, 2, 3, 4, 5)) === Array(2, 1, 4, 3, 5))
    assert(swap(Array(1, 2, 3, 4)) === Array(2, 1, 4, 3))
    assert(swap(Array(1)) === Array(1))
    assert(swap(Array()) === Array())
  }

  /**
    * Given an array of integers, produce a new array
    * that contains all positive values of the original array, in their original order,
    * followed by all values that are zero or negative, in their original order.
    */
  test("4") {

    def partition(arr: Array[Int]): Array[Int] = {
      val result = new ArrayBuffer[Int]
      result ++= (for (elem <- arr if elem > 0) yield elem)
      result ++= (for (elem <- arr if elem <= 0) yield elem)
      result.toArray
    }

    def partition2(arr: Array[Int]): Array[Int] = {
      arr.filter { _ > 0 } ++ arr.filter { _ <= 0 }
    }

    assert(partition(Array(-1, 0, 1)) === Array(1, -1, 0))
    assert(partition(Array(2,6,-1,9,0,-4,-6)) === Array(2, 6, 9, -1, 0, -4, -6))
    assert(partition2(Array(-1, 0, 1)) === Array(1, -1, 0))
    assert(partition2(Array(2,6,-1,9,0,-4,-6)) === Array(2, 6, 9, -1, 0, -4, -6))
  }

  /**
    * How do you compute the average of an Array[Double]?
    */
  test("5") {

    def avg(arr: Array[Double]): Double = {
      arr.sum * 1.0 / arr.length  // multiply 1.0 to ensure a Double
    }

    assert(avg(Array(1, 2, 3)) === 2.0)
    assert(avg(Array(1, 2, 3, 4, 7, 12)) === 4.833333333333333)
  }

  /**
    * How do you rearrange the elements of an Array[Int] so that they appear in
    * reverse sorted order? How do you do the same with an ArrayBuffer[Int]?
    */
  test("6") {

    assert(Array(1, 2, 3, 4).reverse === Array(4, 3, 2, 1))
    assert(ArrayBuffer(1, 2, 3, 4).reverse === ArrayBuffer(4, 3, 2, 1))

    // Array
    // Using a quicksort in place
    var a = Array(1, 2, 4, 5, 3)
    quickSort[Int](a)(Ordering[Int].reverse)
    assert(a === Array(5, 4, 3, 2, 1))

    // not inplace
    a = Array(1, 2, 4, 5, 3)
    assert(a.sortWith(_>_) === Array(5, 4, 3, 2, 1))

    // ArrayBuffer
    val b = ArrayBuffer(1, 2, 4, 5, 3)
    //quickSort(b) // error
    assert(b.sortWith(_>_) === Array(5, 4, 3, 2, 1))
  }

  /**
    * Write a code snippet that produces all values from an array with duplicates
    * removed. (Hint: Look at Scaladoc.)
    */
  test("7") {
    assert(Array(1, 1, 1, 2, 2, 3, 4, 4, 4, 4, 5, 5, 6).distinct === Array(1, 2, 3, 4, 5, 6))
  }

  /**
    * Rewrite the example at the end of Section 3.4,
    * “Transforming Arrays,” on page 34 using the drop method
    * for dropping the index of the first match.
    * Look the method up in Scaladoc.
    */
  test("8") {

    def removeNegativeElemExceptFirstUsingFlag(arr: ArrayBuffer[Int]): ArrayBuffer[Int] = {
      var first = true
      var n = arr.length
      var i = 0
      while (i < n) {
        if (arr(i) >= 0) i += 1
        else {
          if (first) { first = false; i += 1 }
          else { arr.remove(i); n -= 1 }
        }
      }
      arr
    }

    def removeNegativeElemExceptFirstUsingGuard(arr: ArrayBuffer[Int]): ArrayBuffer[Int] = {
      var first = true
      val indexes = for (i <- 0 until arr.length if first || arr(i) >= 0) yield {
        if (arr(i) < 0) first = false; i
      }
      for (j <- 0 until indexes.length) arr(j) = arr(indexes(j))
      arr.trimEnd(arr.length - indexes.length)
      arr
    }

    def removeNegativeElemExceptFirstUsingDrop(arr: ArrayBuffer[Int]): ArrayBuffer[Int] = {
      var indexes = for (i <- 0 until arr.length if arr(i) < 0) yield i
      indexes = indexes.drop(1)
      for (i <- indexes.reverse) arr.remove(i)
      arr
    }

    assert(removeNegativeElemExceptFirstUsingFlag(ArrayBuffer(-1, 2, -3, 4, -5)) === ArrayBuffer(-1, 2, 4))
    assert(removeNegativeElemExceptFirstUsingGuard(ArrayBuffer(-1, 2, -3, 4, -5)) === ArrayBuffer(-1, 2, 4))
    assert(removeNegativeElemExceptFirstUsingDrop(ArrayBuffer(-1, 2, -3, 4, -5)) === ArrayBuffer(-1, 2, 4))

    /*val inputArrBuf = new ArrayBuffer[Int]
    for (i <- 1 to 1000) inputArrBuf.append(util.Random.nextInt)

    var beforeTime, afterTime = 0L
    beforeTime = System.currentTimeMillis()
    removeNegativeElemExceptFirstUsingFlag(inputArrBuf)
    afterTime = System.currentTimeMillis()
    println("removeNegativeElemExceptFirstUsingFlag consume: \t" + (afterTime - beforeTime))

    beforeTime = System.currentTimeMillis()
    removeNegativeElemExceptFirstUsingGuard(inputArrBuf)
    afterTime = System.currentTimeMillis()
    println("removeNegativeElemExceptFirstUsingGuard consume: \t" + (afterTime - beforeTime))

    beforeTime = System.currentTimeMillis()
    removeNegativeElemExceptFirstUsingDrop(inputArrBuf)
    afterTime = System.currentTimeMillis()
    println("removeNegativeElemExceptFirstUsingDrop consume: \t" + (afterTime - beforeTime))*/
  }

  /**
    * Make a collection of all time zones returned by
    * java.util.TimeZone.getAvailableIDs that are in America.
    * Strip off the "America/" prefix and sort the result.
    */
  test("9") {

    //println(TimeZone.getAvailableIDs.mkString(", "))

    val result = for (elem <- TimeZone.getAvailableIDs if elem.startsWith("America/")) yield elem.replaceAll("America/", "")

    //println(result.sorted.mkString(", "))

    assert(result.sorted.reverse.apply(3) === "Whitehorse")

    val result2 = TimeZone.getAvailableIDs filter { _ startsWith "America" } map { _ drop "America/".length } sortBy { x => x }

    assert(result2.sorted.reverse.apply(3) === "Whitehorse")
  }

  /**
    * Import java.awt.datatransfer._ and make an object of type SystemFlavorMap with the call
    * val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
    * Then call the getNativesForFlavor method with parameter DataFlavor.imageFlavor
    * and get the return value as a Scala buffer.
    * (Why this obscure class? It’s hard to find uses of java.util.List in the standard Java library.)
    */
  test("10") {
    val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
    val result = JavaConverters.asScalaBuffer(flavors.getNativesForFlavor(DataFlavor.imageFlavor))
    //println(result.mkString(", "))
    assert(result.mkString(", ") === "PNG, JFIF, TIFF")
  }
}
