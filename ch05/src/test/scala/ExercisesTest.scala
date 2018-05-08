import javax.print.attribute.standard.PrinterMoreInfoManufacturer
import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * Improve the Counter class in Section 5.1, “Simple Classes and Parameterless
    * Methods,” on page 51 so that it doesn’t turn negative at Int.MaxValue.
    */
  test("1") {
    class Counter(private var value: Int = 0) {
      def increment() {
        if (value < Int.MaxValue) value += 1
      }
      def current = value
    }

    val myCounter = new Counter(Int.MaxValue - 1)
    assert(myCounter.current === Int.MaxValue - 1)
    myCounter.increment()
    assert(myCounter.current === Int.MaxValue)
    myCounter.increment()
    assert(myCounter.current === Int.MaxValue)
  }

  /**
    * Write a class BankAccount with methods deposit and withdraw, and a read-only
    * property balance.
    */
  test("2") {
    class BankAccount(private[this] var _balance: Double = 0) {

      def deposit(value: Double): Unit = {
        _balance += value
      }

      def withdraw(value: Double): Unit = {
        if (value <= _balance) {
          _balance -= value
        } else throw new Exception("Your balance " + _balance + " is too small for withdraw " + value)
      }

      def balance: Double = _balance
    }

    val myAccount = new BankAccount
    myAccount.deposit(200)
    assert(myAccount.balance === 200)
    myAccount.withdraw(100)
    assert(myAccount.balance === 100)
    try {
      myAccount.withdraw(200)
    } catch {
      case e: Exception => println(e.getMessage)
    }
  }

  /**
    * Write a class Time with read-only properties hours and minutes and a method
    * before(other: Time): Boolean that checks whether this time comes before the
    * other. A Time object should be constructed as new Time(hrs, min), where hrs is in
    * military time format (between 0 and 23).
    */
  test("3") {
    class Time(private var h: Int = 0, private var m: Int = 0) {

      if (h < 0 || h > 23) throw new Exception("Invalid hour " + h)

      if (m < 0 || m > 59) throw new Exception("Invalid minute " + m)

      def hours: Int = h

      def minutes: Int = m

      def before(other: Time): Boolean = {
        h < other.h || (h == other.h && m < other.m)
      }
    }
    intercept[Exception] {
      new Time(24, 0)
    }
    val myTime = new Time(9, 30)
    assert(myTime.hours === 9)
    assert(myTime.minutes === 30)
    assert(myTime.before(new Time(9, 50)) === true)
    assert(myTime.before(new Time(10, 30)) === true)
    assert(myTime.before(new Time(8, 0)) === false)
  }

  /**
    * Reimplement the Time class from the preceding exercise so that the internal
    * representation is the number of minutes since midnight (between 0 and
    * 24 × 60 – 1). Do not change the public interface. That is, client code should be
    * unaffected by your change.
    */
  test("4") {
    class Time(private val h: Int = 0, private val m: Int = 0) {

      private val minSum = h * 60 + m

      if (h < 0 || h > 23) throw new Exception("Invalid hour " + h)

      if (m < 0 || m > 59) throw new Exception("Invalid minute " + m)

      def hours: Int = minSum / 60

      def minutes: Int = minSum % 60

      def before(other: Time): Boolean = minSum < other.minSum

      def <(other: Time): Boolean = before(other)
    }
    intercept[Exception] {
      new Time(24, 0)
    }
    val myTime = new Time(9, 30)
    assert(myTime.hours === 9)
    assert(myTime.minutes === 30)
    assert(myTime.before(new Time(9, 50)) === true)
    assert(myTime.before(new Time(10, 30)) === true)
    assert(myTime.before(new Time(8, 0)) === false)
    assert((myTime < new Time(8, 0)) === false)
  }

  /**
    * Make a class Student with read-write JavaBeans properties name (of type String)
    * and id (of type Long). What methods are generated? (Use javap to check.) Can
    * you call the JavaBeans getters and setters in Scala? Should you?
    */
  test("5") {
    // Student.scala
    val ming = new Student
    ming.setName("Xiao Ming")
    ming.setId(1)
    assert(ming.getName === "Xiao Ming")
    assert(ming.getId === 1)
  }

  /**
    * In the Person class of Section 5.1, “Simple Classes and Parameterless Methods,”
    * on page 51, provide a primary constructor that turns negative ages to 0.
    */
  test("6") {
    class Person(var age: Int = 0) {
      if (age < 0) age = 0
    }
    val p1 = new Person(5)
    val p2 = new Person(-5)
    assert(p1.age === 5)
    assert(p2.age === 0)
  }

  /**
    * Write a class Person with a primary constructor that accepts a string containing
    * a first name, a space, and a last name, such as new Person("Fred Smith"). Supply
    * read-only properties firstName and lastName. Should the primary constructor
    * parameter be a var, a val, or a plain parameter? Why?
    */
  test("7") {
    class Person(val name: String = "") {
      def firstName: String = name.split("\\s+")(0)
      def lastName: String = name.split("\\s+")(1)
    }

    val fred = new Person("Fred Smith")
    assert(fred.firstName === "Fred")
    assert(fred.lastName === "Smith")
  }

  /**
    * Make a class Car with read-only properties for manufacturer, model name,
    * and model year, and a read-write property for the license plate. Supply four
    * constructors. All require the manufacturer and model name. Optionally,
    * model year and license plate can also be specified in the constructor. If not,
    * the model year is set to -1 and the license plate to the empty string. Which
    * constructor are you choosing as the primary constructor? Why?
    */
  test("8") {
    class Car(val manufacturer: String = "", val modelName: String = "", var modelYear: Int = -1, var licensePlate: String = "") {

      def this(manufacturer: String, modelName: String, licensePlate: String) {
        this(manufacturer, modelName, -1, licensePlate)
      }

      def description: String = manufacturer + " manufactured " + modelName + " in " + modelYear + ", license plate is " + licensePlate
    }

    val myCar1 = new Car("Audi", "A8", 2018, "N88888")
    val myCar2 = new Car("Benz", "GLC")
    val myCar3 = new Car("Benz", "GLC", "M66666")
    assert(myCar1.description === "Audi manufactured A8 in 2018, license plate is N88888")
    assert(myCar2.description === "Benz manufactured GLC in -1, license plate is ")
    assert(myCar3.description === "Benz manufactured GLC in -1, license plate is M66666")
  }

  /**
    * Reimplement the class of the preceding exercise in Java, C#, or C++ (your
    * choice). How much shorter is the Scala class?
    */
  test("9") {
    val myCar = new Car("Audi", "A8", 2018, "N88888")
    assert(myCar.toString === "Audi manufactured A8 in 2018, license plate is N88888")
  }

  /**
    * Consider the class
    * class Employee(val name: String, var salary: Double) {
    *   def this() { this("John Q. Public", 0.0) }
    * }
    * Rewrite it to use explicit fields and a default primary constructor. Which form
    * do you prefer? Why?
    */
  test("10") {

    class Employee(val name: String, var salary: Double) {
      def this() { this("John Q. Public", 0.0) }
      override def toString: String = "Employee(%s, %f)".format(name, salary)
    }

    class EmployeeExplicit {

      private var _name: String = "John Q. Public"
      var salary: Double = 0.0

      def this(name: String, salary: Double) {
        this()
        this._name = name
        this.salary = salary
      }

      def name: String = _name

      override def toString: String = "EmployeeExplicit(%s, %f)".format(name, salary)
    }

    val e1 = new Employee
    val e2 = new EmployeeExplicit
    assert(e1.toString === "Employee(John Q. Public, 0.000000)")
    assert(e2.toString === "EmployeeExplicit(John Q. Public, 0.000000)")
  }
}
