import org.scalatest.FunSuite

class ExercisesTest extends FunSuite {

  /**
    * Extend the following BankAccount class to a CheckingAccount class that charges $1
    * for every deposit and withdrawal.
    * class BankAccount(initialBalance: Double) {
    *   private var balance = initialBalance
    *   def currentBalance = balance
    *   def deposit(amount: Double) = { balance += amount; balance }
    *   def withdraw(amount: Double) = { balance -= amount; balance }
    * }
    */
  test("1") {
    class BankAccount(initialBalance: Double) {
      private var balance = initialBalance
      def currentBalance: Double = balance
      def deposit(amount: Double): Double = { balance += amount; balance }
      def withdraw(amount: Double): Double = { balance -= amount; balance }
      override def toString: String = s"Balance = $balance"
    }

    class CheckingAccount(initialBalance: Double, val charge: Double = 1.0) extends BankAccount(initialBalance) {
      override def deposit(amount: Double): Double = super.deposit(amount - charge)
      override def withdraw(amount: Double): Double = super.withdraw(amount + charge)
    }

    val myAccount = new BankAccount(100.0)
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 200.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 100.0)

    val myAccountChecking = new CheckingAccount(100.0, 2.0)
    myAccountChecking.deposit(100.0)
    assert(myAccountChecking.currentBalance === 198.0)
    myAccountChecking.withdraw(100.0)
    assert(myAccountChecking.currentBalance === 96.0)
  }

  /**
    * Extend the BankAccount class of the preceding exercise into a class SavingsAccount
    * that earns interest every month (when a method earnMonthlyInterest is called) and
    * has three free deposits or withdrawals every month. Reset the transaction count in
    * the earnMonthlyInterest method.
    */
  test("2") {
    class BankAccount(initialBalance: Double) {
      private var balance = initialBalance
      def currentBalance: Double = balance
      def deposit(amount: Double): Double = { balance += amount; balance }
      def withdraw(amount: Double): Double = { balance -= amount; balance }
      override def toString: String = s"Balance = $balance"
    }

    class SavingsAccount(initialBalance: Double, val interestRateYear: Double = 0.1, val freeTransactions: Int = 3, val charge: Double = 1.0) extends BankAccount(initialBalance) {

      var transactionInMonth = 0

      def isFreeTransaction: Boolean = transactionInMonth <= freeTransactions

      override def deposit(amount: Double): Double = {
        transactionInMonth += 1
        super.deposit(amount - (if (isFreeTransaction) 0 else charge))
      }

      override def withdraw(amount: Double): Double = {
        transactionInMonth += 1
        super.withdraw(amount + (if (isFreeTransaction) 0 else charge))
      }

      def earnMonthlyInterest: Double = {
        transactionInMonth = 0
        super.deposit(currentBalance * interestRateYear / 12)
      }
    }

    val myAccount = new SavingsAccount(1000.0, 0.10, 3, 2.0)
    // free transactions
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1100.0)
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1200.0)
    myAccount.withdraw(200.0)
    assert(myAccount.currentBalance === 1000.0)
    // charges $2 per transaction
    myAccount.deposit(100.0)
    assert(myAccount.currentBalance === 1098.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 996.0)
    // earn interest at the end of the month
    myAccount.earnMonthlyInterest
    assert(myAccount.currentBalance === 1004.3)
    // free again
    myAccount.deposit(100.0)
    myAccount.withdraw(100.0)
    assert(myAccount.currentBalance === 1004.3)
  }

  /**
    * Consult your favorite Java or C++ textbook which is sure to have an example of
    * a toy inheritance hierarchy, perhaps involving employees, pets, graphical shapes,
    * or the like. Implement the example in Scala.
    */
  test("3") {

  }
}
