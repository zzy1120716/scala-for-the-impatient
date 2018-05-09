import org.scalatest.FunSuite

import scala.collection.mutable

class InheritanceTest extends FunSuite {

  test("extending a class") {
    class Person {
      var age = 0
    }
    final class Employee extends Person {
      var salary = 0.0
    }
    // class Manager extends Employee {} // illegal
    val p = new Person
    val e = new Employee
    assert(p.age === 0)
    assert(e.age === 0 && e.salary == 0.0)
  }

  class Person {
    var name: String = _
    override def toString: String = s"${getClass.getName}[name=$name]"
  }
  class Employee extends Person {
    var salary: Double = _
    override def toString: String = s"${super.toString}[salary=$salary]"
  }
  class Manager extends Employee {}

  test("overriding methods") {
    val p = new Person
    p.name = "Fred"
    val e = new Employee
    e.name = "Fred"; e.salary = 2000.0
    assert(p.toString === "InheritanceTest$Person[name=Fred]")
    assert(e.toString === "InheritanceTest$Employee[name=Fred][salary=2000.0]")
  }

  test("type checks and casts") {
    val e = new Employee
    assert(e.isInstanceOf[Employee] === true)

    if (e.isInstanceOf[Employee]) {
      val s = e.asInstanceOf[Employee]  // s has type Employee
      assert(s.isInstanceOf[Employee] === true)
    }

    // test succeeds if
    // m refers to an object of class Employee or its subclass
    var m = new Manager
    assert(m.isInstanceOf[Employee] === true)

    // m is null
    m = null
    assert(m.isInstanceOf[Employee] === false)
    assert(m.asInstanceOf[Employee] === null)

    // p is not an Employee, throw ClassCastException
    val p = new Person
    intercept[ClassCastException] {
      p.asInstanceOf[Employee]
    }

    // test whether e refers to an Employee object, but not a subclass
    assert(e.getClass === classOf[Employee])
    m = new Manager
    assert((m.getClass == classOf[Employee]) === false)

    // pattern matching
    e match {
      case s: Employee => assert(s.isInstanceOf[Employee] === true) // process s as an Employee
      case _ => assert(e.isInstanceOf[Employee] === false) // e wasn’t an Employee
    }
  }

  test("protected fields and methods") {
    class Animal {
      protected val name: String = ""
      protected[this] def move(): String = "moving"

      def doWhat(): String = "It's " + move()
    }

    val a = new Animal
    //a.move  // inaccessible
    assert(a.doWhat === "It's moving")
  }

  test("superclass construction") {
    class Person(name: String, age: Int) {}
    class Employee(name: String, age: Int, val salary: Double) extends Person(name, age) {
      override def toString: String = s"${getClass.getName}[name=$name, age=$age, salary=$salary]"
    }
    val e = new Employee("Fred", 42, 2000.0)
    assert(e.toString === "InheritanceTest$Employee$2[name=Fred, age=42, salary=2000.0]")

    // extend a java class
    class Square(x: Int, y: Int, width: Int) extends java.awt.Rectangle(x, y, width, width) {}
    val sqr = new Square(0, 0, 3)
    assert(sqr.getWidth * sqr.getHeight === 9.0)
  }

  test("overriding fields") {
    class Person(val name: String) {
      override def toString = s"${getClass.getName}[name=$name]"
    }
    class SecretAgent(codename: String) extends Person(codename) {
      override val name = "secret"
      override val toString = "secret"
    }
    val sa = new SecretAgent("John")
    assert(sa.name === "secret" && sa.toString === "secret")

    abstract class Staff {
      def id: Int
    }
    class Student(override val id: Int) extends Staff {}
    val stu = new Student(1)
    assert(stu.id === 1)
  }

  test("anonymous subclasses") {
    class Person(val name: String) {
      override def toString = s"${getClass.getName}[name=$name]"
    }
    val alien: Person = new Person("Fred") {
      def greeting = "Greetings, Earthling! My name is Fred."
    }
    def meet(p: Person{def greeting: String}): String = {
      p.name + " says: " + p.greeting
    }
    assert(meet(new Person("Fred") {def greeting = "Greetings, Earthling! My name is Fred."}) === "Fred says: Greetings, Earthling! My name is Fred.")
  }

  test("abstract classes") {
    abstract class Person(val name: String) {
      def id: Int
    }
    class Employee(name: String) extends Person(name) {
      def id: Int = name.hashCode
    }
    //val fred = new Person("Fred") // abstract classes cannot be instantiated
    val fred = new Employee("Fred")
    assert(fred.id === 2198155)
  }

  test("abstract fields") {
    abstract class Person {
      val id: Int
      // no initializer—this is an abstract field with an abstract getter method
      var name: String
      // another abstract field, with abstract getter and setter methods
    }
    class Employee(val id: Int) extends Person {  // subclass has concrete id property
      var name = "" // and concrete name property
    }
    val fred = new Person {
      val id = 1729
      var name = "Fred"
    }
    assert(fred.id === 1729 && fred.name === "Fred")
  }

  test("construction order and early definition") {
    class Creature {
      val range: Int = 10
      val env: Array[Int] = new Array[Int](range)
    }
    class Ant extends Creature {
      override val range = 2
    }
    val ant = new Ant
    assert(ant.env.length === 0)  // it was supposed to be 2, and it does not what you want it to do

    // early definition
    class Bee extends {
      override val range = 2
    } with Creature
    val bee = new Bee
    assert(bee.env.length === 2)  // that's what we want
  }

  test("object equality") {
    class Item(val description: String, val price: Double) {
      final override def equals(other: Any): Boolean = {
        val that = other.asInstanceOf[Item]
        if (that == null) false
        else description == that.description && price == that.price
      }
      final override def hashCode: Int = 13 * description.hashCode + 17 * price.hashCode
    }
    class MobilePhone(override val description: String, override val price: Double) extends Item(description, price) {}
    val i1 = new Item("iPhone", 899)
    val i2 = new MobilePhone("iPhone", 899)
    assert(i1.equals(i2) === true)
  }

  /**
    * newly added content
    */
  test("value classes") {
    val lunch = MilTime(1230)
    assert(lunch.hours === 12)
    assert(lunch.minutes === 30)
    assert(lunch.toString === "1230")

    // use value classes for your own overhead-free “tiny types”
    class Book(val author: Author, val title: Title) {}

    val book = new Book(Author("J.K. Rowling"), Title("Harry Potter"))
    assert(book.author.toString === "J.K. Rowling")
    assert(book.title.toString === "Harry Potter")
  }
}
