package basic

/**
  * Created by zzy on 2019/9/6
  */
object FuncDemo {

  val func = (x: Int) => x * 10

  def m1(f: Int => Int): Int = {
    // 在方法体里面调用函数
    f(3)
  }

  def m2(name: String): Unit = {
    println(name)
  }

  def main(args: Array[String]): Unit = {
    val r = m1(func)
    println(r)

    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val r1 = arr.map(x => x * 5)
    val r2 = arr.map(x => x - 1)
    println(r1.toBuffer)
    println(r2.toBuffer)
    println(arr.toBuffer)

    val a = m2("hello")
    print(a)
  }
}
