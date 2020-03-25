package exercises

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV3 [+A]{

  def head: A
  def tail: DListV3[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV3[B]

  override def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
case object DEmptyListV3 extends DListV3[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV3[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV3[B] = new DConsV3(element, DEmptyListV3)

  override def toString: String = " "
}

case class DConsV3[+A](h: A, t: DListV3[A]) extends DListV3[A]{
  def head: A = h
  def tail: DListV3[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV3[B] = new DConsV3(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }
}

object Driver2 extends App{
  val aList = new DConsV3(1, DEmptyListV3)
    println(aList.head)

  //Create a linked list
  val ll = new DConsV3(3, new DConsV3(2, new DConsV3(1, DEmptyListV3)))
  println(ll.head)

  //Test the add method
  println(s"LL head ${ll.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${ll.toString}")

  //Try with another type
  val stringList = new DConsV3("My String 1",  DEmptyListV3)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")

}