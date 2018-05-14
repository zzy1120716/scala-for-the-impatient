import java.io.PrintWriter

import org.scalatest.FunSuite

import scala.io.Source
import scala.util.matching.Regex
import java.io.File

class ExercisesTest extends FunSuite {

  /**
    * Write a Scala code snippet that reverses the lines in a file (making the last
    * line the first one, and so on).
    */
  test("1") {
    //val out = new PrintWriter("/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/numbers.txt")
    val out = new PrintWriter("E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\src\\test\\resources\\myfile_r.txt")
    val source = Source.fromResource("myfile.txt")
    source.getLines.toArray[String].reverse.foreach(out.println(_))
    source.close()
    out.close()
  }

  /**
    * Write a Scala program that reads a file with tabs, replaces each tab with spaces
    * so that tab stops are at n-column boundaries, and writes the result to the
    * same file.
    */
  test("2") {
    val source = Source.fromResource("02.txt")
    //val out = new PrintWriter("/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/02.txt")
    val out = new PrintWriter("E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\src\\test\\resources\\02.txt")

    val column = 8
    var count = 0

    for (c <- source) c match {
      case '\t' =>
        out.print(" " * (column - count % column))
        count = 0
      case '\n' =>
        out.print(c)
        count = 0
      case _ =>
        out.print(c)
        count += 1
    }
    source.close()
    out.close()
  }

  /**
    * Write a Scala code snippet that reads a file and prints all words with more
    * than 12 characters to the console. Extra credit if you can do this in a single line.
    */
  test("3") {
    Source.fromResource("03.txt").mkString.split("""[\s\.,"'\(\)/]+""").filter(_.length > 12).distinct.foreach(println(_))
  }

  /**
    * Write a Scala program that reads a text file containing only floating-point
    * numbers. Print the sum, average, maximum, and minimum of the numbers
    * in the file.
    */
  test("4") {
    val source = Source.fromResource("04.txt")
    val floatPattern = """(-?\d+)(\.\d+)?""".r
    val numbers = floatPattern.findAllIn(source.mkString).toArray.map(_.toDouble)
    assert(numbers.mkString(", ") === "16.407, -0.005, -3.0E-4, 16.401, 16.412, 16.416, 16.401, 0.187, 0.187, 0.187, 0.188, 0.187, 0.1459, 0.1459, 0.1459, 0.1462, 0.1458, 0.1925, 0.1925, 0.1925, 0.193, 0.1923, 0.1164, 0.1164, 0.1164, 0.1168, 0.1163, 0.145, 1.0E-4, 7.0E-4, 0.1449, 0.1449, 0.1455, 0.1449, 0.1356, 1.0E-4, 9.0E-4, 0.1354, 0.1355, 0.136, 0.1354")
    assert(s"Sum: ${numbers.sum}" === "Sum: 86.64739999999996")
    assert(s"Avg: ${numbers.sum / numbers.length}" === "Avg: 2.1133512195121944")
    assert(s"Max: ${numbers.max}" === "Max: 16.416")
    assert(s"Min: ${numbers.min}" === "Min: -0.005")
    source.close()
  }

  /**
    * Write a Scala program that writes the powers of 2 and their reciprocals to a
    * file, with the exponent ranging from 0 to 20. Line up the columns:
    * 1 1
    * 2 0.5
    * 4 0.25
    * ... ...
    */
  test("5") {
    val zeroPattern = ".?0+$"
    //val out = new PrintWriter("/Users/zzy/Docs/scala-for-the-impatient/ch09/src/test/resources/05.txt")
    val out = new PrintWriter("E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\src\\test\\resources\\05.txt")
    for (i <- 0 to 20) out.println(f"${math.pow(2, i)}%7.0f\t\t\t${BigDecimal(math.pow(2, -i)).toString.replaceAll(zeroPattern, "")}%s")
    out.close()
  }

  /**
    * Make a regular expression searching for quoted strings
    * "like this, maybe with \" or \\"
    * in a Java or C++ program. Write a Scala program that prints out all
    * such strings in a source file.
    */
  test("6") {
    // ?: 只匹配不捕获
    val quotePattern = """^"(?:([^\"]|[^\\]))*"$""".r
    //val quotePattern = """"([^"\\]*([\\]+"[^"\\]*)*)"""".r
    val source = Source.fromResource("06.txt")
    val s = source.mkString
    quotePattern.findAllIn(s).foreach { x => println(x) }
    source.close()
  }

  /**
    * Write a Scala program that reads a text file and prints all tokens in the file
    * that are not floating-point numbers. Use a regular expression.
    */
  test("7") {
    val pattern = "^[0-9]+(\\.[0-9]+)?$".r
    val source = Source.fromResource("07.txt")
    val tokens = source.mkString.split("""\s+""").filter(
      pattern findFirstIn _ match {
        case Some(_) => false
        case None => true
      }
    )
    assert(tokens.mkString(", ") === "lkjdlkjln, dkhkjhd, dlkj, djjd, \"\"d, djdh7883, 3., .33")
    source.close()
  }

  /**
    * Write a Scala program that prints the src attributes of all img tags of a web
    * page. Use regular expressions and groups.
    */
  test("8") {
    // <img src="http://xxx.com" />
    val html = io.Source.fromURL("http://horstmann.com", "UTF-8").mkString
    val srcPattern = """(?is)<\s*img[^>]*src\s*=\s*['"\s]*([^'"]+)['"\s]*[^>]*>""".r
    for (srcPattern(s) <- srcPattern findAllIn html) println(s)
  }

  /**
    * Write a Scala program that counts how many files with .class extension are
    * in a given directory and its subdirectories.
    */
  test("9") {
    def countMatchFiles(dir: File, pattern: Regex): Int = {
      val subdirs = dir.listFiles.filter(_.isDirectory)
      val fileNames = dir.listFiles.filter(!_.isDirectory).map(_.getName)

      subdirs.map(countMatchFiles(_, pattern)).sum +
        (for(f <- fileNames; s <- pattern findFirstIn f) yield s).size
    }

    println("Count of *.class files: %d".format(countMatchFiles(new File("E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\target"), "\\.class".r)))

    // more elegant solution
    def getFileTree(f: File): Stream[File] =
      f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFileTree) else Stream.empty)

    println("More elegant solution: %d".format(getFileTree(new File("E:\\IdeaProjects\\scala-for-the-impatient\\ch09\\target")).filter(_.getName.endsWith(".class")).size))
  }

  /**
    * Expand the example in Section 9.8, “Serialization,” on page 113. Construct a
    * few Person objects, make some of them friends of others, and save an
    * Array[Person] to a file. Read the array back in and verify that the friend relations
    * are intact.
    */
  /*test("10") {
    @SerialVersionUID(42L) class Person(val name: String) extends Serializable {
      private val friends = new ArrayBuffer[Person] // OK —— ArrayBuffer is serializable
      override def toString: String = s"Person[name=$name]"
    }
    val fred = new Person("Fred")
  }*/
}
