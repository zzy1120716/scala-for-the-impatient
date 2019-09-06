package basic

/**
  * Created by zzy on 2019/9/5
  */
object ForDemo {
  def main(args: Array[String]): Unit = {
    // for (i <- 表达式)，表达式1 to 10返回一个Range（区间）
    // 每次循环将区间中的一个值赋给i
    for (i <- 1 to 10)
      println(i)

    // for (i <- 数组)
    val arr = Array("a", "b", "c")
    for (i <- arr)
      println(i)

    // 高级for循环
    // 每个迭代生成器都可以带一个条件，注意：if前面没有分号
    for (i <- 1 to 3; j <- 1 to 3 if i != j)
      print((10 * i + j) + " ")
    println()

    // for推导式：如果for循环的循环体以yield开始，则该循环会构建出一个集合
    // 每次迭代生成集合中的一个值
    val v = for (i <- 1 to 10) yield i * 10
    println(v)

    val a1 = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)

    // 循环推导式，加条件
    val a2 = for (i <- a1 if i % 2 == 0) yield i
    println(a2.toBuffer)

    // 过滤，与上面效果相同
    val a3 = a1.filter(_ % 2 == 0)
    println(a3.toBuffer)

    // to进行循环，下标需要-1，可读性差
    for (i <- 1 to a1.length)
      println(a1(i - 1))

    // until关键字，实现与上面代码相同的效果
    // 表示循环条件区间是"左闭右开"
    for (i <- 0 until a1.length)
      println(a1(i))
  }
}
