package basic

/**
  * Created by zzy on 2019/9/5
  */
object BlockExpressionDemo {
  def main(args: Array[String]): Unit = {
    val x = 0
    // 在scala中，{}中可包含一系列表达式，块中最后一个表达式的值就是块的值
    // 下面就是一个块表达式
    val result = {
      if (x < 0) {
        -1
      } else if (x >= 1) {
        1
      } else {
        "error"
      }
    }
    // result的值就是块表达式的结果
    println(result)
  }
}
