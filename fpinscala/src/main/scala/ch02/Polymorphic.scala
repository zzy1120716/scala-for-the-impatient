package ch02

/**
  * 多态函数
  * Created by zzy on 2019/9/10
  */
object Polymorphic {

  // 在数组中查找元素
  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    @annotation.tailrec
    def loop(n: Int): Int =
      if (n >= as.length) -1
      else if (p(as(n))) n
      else loop(n + 1)

    loop(0)
  }

  // 检测数组是否按照给定的比较函数排序
  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def loop(n: Int): Boolean =
      if (n >= as.length - 1) true
      else if (ordered(as(n), as(n + 1))) loop(n + 1)
      else false

    loop(0)
  }

  def main(args: Array[String]): Unit = {
    println(findFirst(Array(7, 9, 13), (x: Int) => x == 9))
    println(isSorted(Array(7, 9, 13), (a: Int, b: Int) => a < b))
  }
}
