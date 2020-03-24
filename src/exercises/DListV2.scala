package exercises

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV2{

  def head: Int
  def tail: DListV2
  def isEmpty: Boolean
  def add(element: Integer): DListV2

  override def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
object DEmptyListV2 extends DListV2{
  def head: Int = throw new NoSuchElementException
  def tail: DListV2 = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Integer): DListV2 = new DConsV2(element, DEmptyListV2)

  override def toString: String = " "
}

class DConsV2(h: Int, t: DListV2) extends DListV2{
  def head: Int = h
  def tail: DListV2 = t
  def isEmpty: Boolean = false
  def add(element: Integer): DConsV2 = new DConsV2(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }
}

object Driver2 extends App{
  val aList = new DConsV2(1, DEmptyListV2)
    println(aList.head)

  //Create a linked list
  val ll = new DConsV2(3, new DConsV2(2, new DConsV2(1, DEmptyListV2)))
  println(ll.head)

  //Test the add method
  println(s"LL head ${ll.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${ll.toString}")
}