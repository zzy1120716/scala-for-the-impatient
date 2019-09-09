package ch02

/**
  * Created by zzy on 2019/9/10
  */
object MyModule {

  // 计算绝对值
  def abs(n: Int): Int = {
    if (n < 0) -n
    else n
  }

  // 计算阶乘
  def factorial(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n - 1, n * acc)

    go(n, 1)
  }

  // 格式化结果
  private def formatResult(name: String, n: Int, f: Int => Int) = {
    val msg = "The %s of %d is %d."
    msg.format(name, n, f(n))
  }

  def main(args: Array[String]): Unit = {
    println(formatResult("absolute value", -42, abs))
    println(formatResult("factorial", 7, factorial))
  }
}
