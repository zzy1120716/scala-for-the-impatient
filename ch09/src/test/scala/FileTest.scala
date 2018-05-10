import java.io.{File, FileInputStream, PrintWriter}

import org.scalatest.FunSuite

import scala.collection.mutable
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

class FileTest extends FunSuite {

  test("reading lines") {
    //val source = Source.fromFile("myfile.txt", "UTF-8")
    val source1 = Source.fromResource("myfile.txt")
    val lineIterator = source1.getLines
    val words = new ArrayBuffer[String]

    // iterator
    for (l <- lineIterator) words.append(l.toUpperCase)
    assert(words.mkString(", ") === "APPLE, BANANA, CHERRY")
    source1.close()

    // put the lines into an array or array buffer
    val source2 = Source.fromResource("myfile.txt")
    val lines = source2.getLines.toArray
    assert(lines.mkString(", ") === "apple, banana, cherry")
    source2.close()

    // read an entire file into a string
    val source3 = Source.fromResource("myfile.txt")
    val contents = source3.mkString
    assert(contents.take(5) === "apple")
    source3.close()
  }

  test("reading characters") {
    // process every characters
    val source1 = Source.fromResource("myfile.txt")
    var unicodeSum: Int = 0
    for (c <- source1) unicodeSum += c
    assert(unicodeSum === 1838)
    source1.close()

    // peek at the next input character without consuming it
    val source2 = Source.fromResource("myfile.txt")
    val iter = source2.buffered
    var sum: Int = 0
    while (iter.hasNext) {
      sum += iter.head.toInt
      iter.next
    }
    source2.close()
    assert(sum === 1838)
  }

  test("reading tokens and numbers") {
    val source1 = Source.fromResource("myfile2.txt")
    val tokens1 = source1.mkString.split("\\s+")
    assert(tokens1 === Array("1", "2", "3", "4", "5"))
    source1.close()

    val source2 = Source.fromResource("myfile2.txt")
    val tokens2 = source2.mkString.split("\\s+")
    /*--->*/val numbers1 = for (w <- tokens2) yield w.toDouble/*<---*/
    assert(numbers1 === Array(1.0, 2.0, 3.0, 4.0, 5.0))
    source2.close()

    val source3 = Source.fromResource("myfile2.txt")
    val tokens3 = source3.mkString.split("\\s+")
    /*--->*/val numbers2 = tokens3.map(_.toDouble)/*<---*/
    assert(numbers2 === Array(1.0, 2.0, 3.0, 4.0, 5.0))
    source3.close()
  }

  test("reading from URLs and other sources") {
    val source1 = Source.fromURL("http://www.baidu.com", "UTF-8")
    val source2 = Source.fromString("Hello, World!")
    val source3 = Source.stdin
    assert(source2.mkString === "Hello, World!")
    source1.close()
    source2.close()
    source3.close()
  }

  test("reading binary files") {
    val file = new File("/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/myfile.txt")
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length.toInt)
    in.read(bytes)
    in.close()
  }

  test("writing text file") {
    val out = new PrintWriter("/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/numbers.txt")
    for (i <- 1 to 100) out.println(i)

    val quantity = 1000000
    val price = 199.99
    out.printf("%6d %10.2f", quantity.asInstanceOf[AnyRef], price.asInstanceOf[AnyRef]) // ugh
    out.printf("%6d %10.2f".format(quantity, price)) // better solution
    out.close()
  }
}
