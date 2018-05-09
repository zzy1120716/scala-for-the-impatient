class Title(val title: String) extends AnyVal {
  override def toString: String = title
}

object Title {
  def apply(t: String): Title = new Title(t)
}