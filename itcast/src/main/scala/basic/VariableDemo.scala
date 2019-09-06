package basic

/**
  * Created by zzy on 2019/9/5
  */
object VariableDemo {
  def main(args: Array[String]): Unit = {
    // 使用 val 定义的变量是不可变的，相当于 java 里用 final 修饰的变量
    val i = 1
    // 使用 var 定义的变量是可变的，在 Scala 中鼓励使用 val
    var s = "hello"
    //Scala编译器会自动推断变量的类型，必要的时候可以指定类型
    //变量名在前，类型在后
    val str: String = "itcast"

    println(s"i=$i")
    println(s"s=$s")
    println(s"str=$str")
  }
}
