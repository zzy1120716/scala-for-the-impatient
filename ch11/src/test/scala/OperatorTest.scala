import org.scalatest.FunSuite

class OperatorTest extends FunSuite {

  test("identifiers") {
    val √ = scala.math.sqrt _
    assert(√(2) === 1.4142135623730951)

    val happy_birthday_!!! = "Bonne anniversaire!!!"
    assert(happy_birthday_!!! === "Bonne anniversaire!!!")

    val `val` = 42
    assert(`val` === 42)

    Thread.`yield`()
  }

  test("infix operators") {
    println(1 to 10)
    println(1.to(10))
    println(1 -> 10)
    println(1.->(10))

    class Fraction(n: Int, d: Int) {
      private val num = n
      private val den = d

      def *(other: Fraction) = new Fraction(num * other.num, den * other.den)

      override def toString: String = s"$num / $den"
    }
    assert(new Fraction(2, 3).toString === "2 / 3")
    assert((new Fraction(2, 3) * new Fraction(3, 4)).toString === new Fraction(6, 12).toString)
  }

  test("unary operators") {
    assert(-21 === 21.unary_-)
    assert(42.toString === (42 toString))
    /*val result = 42 toString
    println(result)   // too many arguments*/
    //import scala.language.postfixOps
  }

  test("associativity") {
    assert(17 - 2 - 9 === (17 - 2) - 9) // left-associative
    val list: List[Int] = 1 :: 2 :: Nil // right-associative
    assert(list === List(1, 2))
    assert(2 :: Nil === Nil.::(2))
  }

  test("apply and update methods") {
    val scores = new scala.collection.mutable.HashMap[String, Int]
    scores("Bob") = 100 // calls scores.update("Bob", 100)
    assert(scores.mkString(",") === "Bob -> 100")
    val bobsScore = scores("Bob") // calls scores.apply("Bob")
    assert(bobsScore === 100)

    class Fraction(n: Int, d: Int) {
      private val num: Int = n
      private val den: Int = d
      def *(other: Fraction): Fraction = { Fraction(num * other.num, den * other.den) }

      override def toString: String = s"$num / $den"
    }

    object Fraction {
      def apply(n: Int, d: Int) = new Fraction(n, d)
    }

    val result = Fraction(3, 4) * Fraction(2, 5)
    assert(result.toString === "6 / 20")
  }

  test("extractors") {
    class Fraction(n: Int, d: Int) {
      private val num: Int = n
      private val den: Int = d
      def *(other: Fraction): Fraction = { Fraction(num * other.num, den * other.den) }

      override def toString: String = s"$num / $den"
    }

    object Fraction {
      def apply(n: Int, d: Int) = new Fraction(n, d)
      def unapply(input: Fraction): Option[(Int, Int)] =
        if (input.den == 0) None else Some((input.num, input.den))
    }

    val Fraction(a, b) = Fraction(3, 4) * Fraction(2, 5)
    /*val Fraction(a, b) = f
    val tupleOption = Fraction.unapply(f)
    if (tupleOption.isEmpty) throw new MatchError*/
    // tupleOption is Some((t1, t2))
    assert(a === 6)
    assert(b === 20)

    object Name {
      def unapply(input: String): Option[(String, String)] = {
        val pos = input.indexOf(" ")
        if (pos == -1) None
        else Some((input.substring(0, pos), input.substring(pos + 1)))
      }
    }

    val author = "Cay Horstmann"
    val Name(first, last) = author  // calls Name.unapply(author)
    assert(first === "Cay")
    assert(last === "Horstmann")

    case class Currency(value: Double, unit: String)
    val c = Currency(29.95, "USD")  // calls Currency.apply
    c match {
      case Currency(amount, "EUR") => println(s"€$amount")
      case Currency(amount, "USD") => println(s"$$$amount")
    } // calls Currency.unapply
  }

  test("extractors with one or no arguments") {
    object Number {
      def unapply(input: String): Option[Int] =
        try {
          Some(input.trim.toInt)
        } catch {
          case ex: NumberFormatException => None
        }
    }
    val Number(n) = "1729"
    assert(n === 1729)

    object Name {
      def unapply(input: String): Option[(String, String)] = {
        val pos = input.indexOf(" ")
        if (pos == -1) None
        else Some((input.substring(0, pos), input.substring(pos + 1)))
      }
    }

    object IsCompound {
      def unapply(input: String): Boolean = input.contains(" ")
    }

    val author = "Cay van der Linden"
    author match {
      case Name(first, IsCompound()) => println(s"First Name: $first"); println(s"Last Name: ${author.takeRight(author.length - first.length - 1)}")
      case Name(first, last) => println(s"First Name: $first"); println(s"Last Name: $last")
    }
  }

  test("the unapplySeq method") {
    object Name {
      def unapplySeq(input: String): Option[Seq[String]] =
        if (input.trim == "") None else Some(input.trim.split("""\s+"""))
    }
    val author = "Cay van der Linden"
    author match {
      case Name(first, last) => println(s"$first $last")
      case Name(first, middle, last) => println(s"$first $middle $last")
      case Name(first, "van", "der", last) => println(s"Aha~ $first van der $last")
    }
  }

  test("dynamic invocation") {
    import scala.language.dynamics
    //obj.applyDynamic("name")(arg1, arg2, ...)
    //obj.applyDynamicNamed("name")((name1, arg1), (name2, arg2), ...)
    //obj.updateDynamic("name")(rightHandSide)
    //obj.selectDynamic("sel")
    class Person extends Dynamic {
      var lastName = ""
      def updateDynamic(field: String)(newValue: String) {}
      //def selectDynamic(field: String) = {}
    }
    val person = new Person
    //person.lastName = "Doe"
    person.updateDynamic("lastName")("Doe")
    //val name = person.lastName
    //val name = person.selectDynamic("lastName")
    //val does = people.findByLastName("Doe")
    //val does = people.applyDynamic("findByLastName")("Doe")
    //val johnDoes = people.find(lastName = "Doe", firstName = "John")
    //val johnDoes = people.applyDynamicNamed("find")(("lastName", "Doe"), ("firstName", "John"))

    class DynamicProps(val props: java.util.Properties) extends Dynamic {
      def updateDynamic(name: String)(value: String) {
        props.setProperty(name.replaceAll("_", "."), value)
      }
      def selectDynamic(name: String): String =
        props.getProperty(name.replaceAll("_", "."))
      def applyDynamicNamed(name: String)(args: (String, String)*) {
        if (name != "add") throw new IllegalArgumentException
        for ((k, v) <- args)
          props.setProperty(k.replaceAll("_", "."), v)
      }
    }

    val sysProps = new DynamicProps(System.getProperties)
    sysProps.username = "Fred" // sets the "username" property to "Fred"
    val home = sysProps.java_home // gets the "java.home" property
    println(home)
    sysProps.add(username="Fred", password="Secret")
  }
}
