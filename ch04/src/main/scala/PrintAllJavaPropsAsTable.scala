import collection.JavaConverters._

object PrintAllJavaPropsAsTable extends App {

  def getLongestKeyLen(props: collection.mutable.Map[String, String]): Int = {
    val lens = for (prop <- props.keySet.toArray) yield prop.length
    lens.max
  }

  def print(): Unit = {
    val props: collection.mutable.Map[String, String] = System.getProperties.asScala
    val maxKeyLen = getLongestKeyLen(props)
    props foreach {
      prop => println(prop._1 + " " * (maxKeyLen - prop._1.length + 1) + "\t\t| " + prop._2)
    }
  }

  print()
}
