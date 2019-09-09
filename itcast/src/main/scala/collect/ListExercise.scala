package collect

/**
  * Created by zzy on 2019/9/7
  */
object ListExercise {
  def main(args: Array[String]): Unit = {
    // 创建一个List
    val lst0 = List(1, 7, 9, 8, 0, 3, 5, 4, 6, 2)
    println(s"lst0=$lst0")

    // 将lst0中每个元素乘以10后生成一个新的集合
    val lst1 = lst0.map(x => x * 10)
    println(s"lst1=$lst1")

    // 将lst0中的偶数取出来生成一个新的集合
    val lst2 = lst0.filter(x => x % 2 == 0)
    println(s"lst2=$lst2")

    // 将lst0排序后生成一个新的集合
    val lst3 = lst0.sorted
    println(s"lst3=$lst3")

    val lst4 = lst0.sortBy(x => x)
    println(s"lst4=$lst4")

    //val lst5 = lst0.sortWith((x, y) => x < y)
    val lst5 = lst0.sortWith(_ < _)
    println(s"lst5=$lst5")

    // 反转顺序
    val lst6 = lst3.reverse
    println(s"lst6=$lst6")

    // 将lst0中的元素4个一组，类型为Iterator[List[Int]]
    val it = lst0.grouped(4)
    // 将Iterator转换成List
    val lst7 = it.toList
    println(s"lst7=$lst7")

    // 将多个List压扁成一个List
    val lst8 = lst7.flatten
    println(s"lst8=$lst8")

    // 先按空格切分，再压平
    val a = Array("a b c", "d e f", "h i j")
    val arr = a.flatMap(_.split(" "))
    println(s"arr=${arr.toBuffer}")

    // Word Count
    // 方法一
    val lines = List("hello tom hello jerry", "hello tom kitty hello hello")
    val grouped = lines.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1)
    val finalResult1 = grouped.map(t => (t._1, t._2.size)).toList.sortBy(_._2).reverse
    println(finalResult1)

    // 方法二：使用mapValues方法，直接取map中的value
    val finalResult2 = grouped.mapValues(_.size).toList.sortBy(_._2).reverse
    println(finalResult2)

    // 方法三：假如map中的每个元素的值并不为1（MapReduce中有Combiner的场景），不能直接通过size获取个数
    // 利用fold方法
    val finalResult3 = grouped.mapValues(_.foldLeft(0)(_ + _._2))
    println(finalResult3)

    // reduce操作
    val l = List(1, 2, 3, 4, 5, 6)
    println(l.reduce(_ + _))
    println(l.reduceLeft(_ + _))
    println(l.reduce(_ - _))
    println(l.reduceRight(_ - _))

    // 转化为并行集合
    println(l.par.reduce(_ + _))

    // 柯里化，指定初值为10，再求和
    println(l.fold(10)(_ + _))
    println(l.par.fold(10)(_ + _))
    println(l.par.fold(0)(_ + _))
    println(l.par.foldLeft(10)(_ + _))
    println(l.par.foldRight(10)(_ + _))
  }
}
