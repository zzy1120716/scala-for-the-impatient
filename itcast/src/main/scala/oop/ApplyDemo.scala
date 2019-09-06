package oop

/**
  * apply方法
  * Created by zzy on 2019/9/6
  */
object ApplyDemo {
  def main(args: Array[String]): Unit = {
    // 调用了Array伴生对象的apply方法
    // def apply(x: Int, xs: Int*): Array[Int]
    // arr1中只有一个元素
    val arr1 = Array(5)
    println(arr1.toBuffer)

    // new了一个长度为5的array，数组里面包含5个null
    val arr2 = new Array(5)
  }
}
