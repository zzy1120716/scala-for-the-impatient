class Author(val author: String) extends AnyVal {
  override def toString: String = author
}

object Author {
  def apply(a: String): Author = new Author(a)
}