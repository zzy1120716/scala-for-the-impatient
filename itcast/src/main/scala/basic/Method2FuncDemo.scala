package basic

/**
  * Created by zzy on 2019/9/6
  */
object Method2FuncDemo {

  def m1(x: Int, y: Int): Int = x + y

  def main(args: Array[String]): Unit = {
    // 下划线将方法转化为函数
    val f1 = m1 _
    println(f1)
    val r = f1(2, 3)
    println(r)
  }
}
