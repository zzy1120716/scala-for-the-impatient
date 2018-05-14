import java.io.{IOException, PrintStream, PrintWriter}

import org.scalatest.FunSuite

class TraitTest extends FunSuite {

  test("traits as interfaces") {
    trait Logger {
      def log(msg: String)  // an abstract method
    }

    class ConsoleLogger extends Logger {  // use extends, not implements
      def log(msg: String) { println(msg) } // no override needed
    }

    // more than one trait
    class ErrorLogger extends Logger with Cloneable with Serializable {
      def log(msg: String) { System.err.println(msg) }
    }
  }

  test("traits with concrete implementations") {

    class Account {
      var balance: Double = _
    }

    trait ConsoleLogger {
      def log(msg: String) { println(msg) }
    }

    class SavingsAccount extends Account with ConsoleLogger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val sa = new SavingsAccount
    sa.balance = 100
    sa.withdraw(200)
  }

  test("objects with traits") {

    trait Logger {
      def log(msg: String)  // an abstract method
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait FileLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    class Account {
      var balance: Double = _
    }

    abstract class SavingsAccount extends Account with Logger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val acct = new SavingsAccount with ConsoleLogger
    val acct2 = new SavingsAccount with FileLogger
    acct.log("console log")
    acct2.log("file log")
  }

  test("layered traits") {

    trait Logger {
      def log(msg: String)  // an abstract method
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait TimestampLogger extends ConsoleLogger {
      override def log(msg: String): Unit = {
        super.log(s"${java.time.Instant.now()} $msg") // add timestamp to all logging messages
      }
    }

    trait ShortLogger extends ConsoleLogger {
      val maxLength = 15
      override def log(msg: String): Unit = {
        super.log(
          if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...") // truncate overly chatty log messages
      }
    }

    class Account {
      var balance: Double = _
    }

    class SavingsAccount extends Account with ConsoleLogger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val acct1 = new SavingsAccount with ConsoleLogger with TimestampLogger with ShortLogger
    val acct2 = new SavingsAccount with ConsoleLogger with ShortLogger with TimestampLogger
    acct1.balance = 100
    acct2.balance = 100
    acct1.withdraw(200)
    acct2.withdraw(200)

    trait TimestampLogger2 extends ConsoleLogger {
      override def log(msg: String): Unit = {
        super[ConsoleLogger].log(s"${java.time.Instant.now()} $msg") // add timestamp to all logging messages
      }
    }

    trait ShortLogger2 extends ConsoleLogger {
      val maxLength = 15
      override def log(msg: String): Unit = {
        super[ConsoleLogger].log(
          if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...") // truncate overly chatty log messages
      }
    }

    val acct3 = new SavingsAccount with ConsoleLogger with TimestampLogger2 with ShortLogger2
    acct3.balance = 100
    acct3.withdraw(200)
  }

  test("overriding abstract methods in traits") {

    trait Logger {
      def log(msg: String) // this method is abstract
    }

    trait TimestampLogger extends Logger {
      abstract override def log(msg: String): Unit = { // override an abstract method
        super.log(s"${java.time.Instant.now()} $msg") // it depends on the order in which traits are mixed in
      }
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }  // implements method log
    }

    class Account {
      var balance: Double = _
    }

    abstract class SavingsAccount extends Account with Logger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val acct = new SavingsAccount with ConsoleLogger with TimestampLogger
    acct.balance = 100
    acct.withdraw(200)
    // Error:(141, 15) method log in trait Logger is accessed from super. It may not be abstract unless it is overridden by a member declared `abstract' and `override'
    //        super.log(s"${java.time.Instant.now()} $msg")
  }

  test("traits for rich interfaces") {

    trait Logger {
      def log(msg: String)
      def info(msg: String) { log(s"INFO: $msg") }
      def warn(msg: String) { log(s"WARN: $msg") }
      def severe(msg: String) { log(s"SEVERE: $msg") }
    }

    class Account {
      var balance: Double = _
    }

    class SavingsAccount extends Account with Logger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) severe("Insufficient funds")
        else balance -= amount
      }

      override def log(msg: String): Unit = { println(msg) }
    }

    val acct = new SavingsAccount
    acct.balance = 100
    acct.withdraw(200)
  }

  test("concrete fields in traits") {

    class Account {
      var balance = 0.0
    }

    trait Logger {
      def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait ShortLogger extends ConsoleLogger {
      val maxLength = 15    // a concrete field
      override def log(msg: String): Unit = {
        super.log(
          if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...") // truncate overly chatty log messages
      }
    }

    class SavingsAccount extends Account with ConsoleLogger with ShortLogger {
      var interest = 0.0
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val acct = new SavingsAccount
    assert(acct.interest === 0.0)
    assert(acct.maxLength === 15)
  }

  test("abstract fields in traits") {

    class Account {
      var balance = 0.0
    }

    trait Logger {
      def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait ShortLogger extends Logger {
      val maxLength: Int    // an abstract field
      abstract override def log(msg: String): Unit = {
        super.log(
          if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...")
          // the maxLength field is used in the implementation
      }
    }

    class SavingsAccount extends Account with ConsoleLogger with ShortLogger {
      val maxLength = 20
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    val acct1 = new SavingsAccount
    acct1.balance = 100
    acct1.withdraw(200)

    val acct2 = new SavingsAccount with ConsoleLogger with ShortLogger {
      override val maxLength = 10
    }
    acct2.balance = 100
    acct2.withdraw(200)
  }

  test("trait construction order") {

    class Account {
      println("Account constructed")
      var balance = 0.0
    }

    class SavingsAccount extends Account with FileLogger with ShortLogger {
      println("SavingsAccount constructed")
      val maxLength = 20
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    trait Logger {
      println("Logger constructed")
      def log(msg: String)
    }

    trait FileLogger extends Logger {
      println("FileLogger constructed")
      val out = new PrintWriter("app.log")  // part of the trait's constructor
      out.println(s"# ${java.time.Instant.now()}")  // also part of the constructor

      def log(msg: String) { out.println(msg); out.flush() }
    }

    trait ShortLogger extends Logger {
      println("ShortLogger constructed")
      val maxLength: Int    // an abstract field
      abstract override def log(msg: String): Unit = {
        super.log(
          if (msg.length <= maxLength) msg else s"${msg.substring(0, maxLength - 3)}...")
      }
    }

    val acct = new SavingsAccount
    acct.balance = 100
    acct.withdraw(200)
  }

  test("initializing trait fields") {

    class Account {
      var balance = 0.0
    }

    class SavingsAccount extends {  // early definition block after extends
      val filename = "savings.log"
    } with Account with FileLogger {
      def withdraw(amount: Double): Unit = {
        if (amount > balance) log("Insufficient funds")
        else balance -= amount
      }
    }

    trait Logger {
      def log(msg: String)
    }

    trait FileLogger extends Logger {
      val filename: String
      //lazy val out = new PrintStream(filename)
      val out = new PrintStream(filename)
      def log(msg: String) { out.println(msg); out.flush() }
    }

    val acct = new {  // early definition block after new
      override val filename = "myapp.log"
    } with SavingsAccount with FileLogger
    acct.balance = 100
    acct.withdraw(200)
  }

  test("traits extending classes") {

    trait Logger {
      def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait LoggedException extends Exception with ConsoleLogger {
      def log() { log(getMessage) }
    }

    class UnhappyException extends IOException with LoggedException {  // this class extends a trait
      override def getMessage = "arggh!"
    }
  }

  test("self types") {

    trait Logger {
      def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
      override def log(msg: String) { println(msg) }
    }

    trait LoggedException extends ConsoleLogger {
      this: Exception =>
      def log() { log(getMessage) }
    }

    trait LoggedMsgException extends ConsoleLogger {
      this: { def getMessage: String } =>
      def log() { log(getMessage) }
    }
  }
}
