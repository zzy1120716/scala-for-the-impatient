package ch02

/**
  * Created by zzy on 2019/9/9
  */
object TailRec {

  def fib(n: Int): Int = {
    def go(n: Int): Int =
      if (n == 1) 0
      else if (n == 2) 1
      else go(n - 1) + go(n - 2)

    go(n)
  }

  def main(args: Array[String]) : Unit = {
    println(fib(1))
    println(fib(2))
    println(fib(3))
    println(fib(4))
    println(fib(5))
    println(fib(6))
    println(fib(7))
    println(fib(8))
    println(fib(9))
    println(fib(10))
  }
}