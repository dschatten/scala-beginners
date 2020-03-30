package exercises
//TODO - Review this class implementation and see how it illustrates different concepts through the versions
/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV1 {

  def head: Int
  def tail: DListV1
  def isEmpty: Boolean
  def add(element: Integer): DListV1

  override def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
object DEmptyListV1 extends DListV1{
  def head: Int = throw new NoSuchElementException
  def tail: DListV1 = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Integer): DListV1 = new DConsV1(element, DEmptyListV1)

  override def toString: String = " "
}

class DConsV1(h: Int, t: DListV1) extends DListV1{
  def head: Int = h
  def tail: DListV1 = t
  def isEmpty: Boolean = false
  def add(element: Integer): DConsV1 = new DConsV1(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }
}

object Driver extends App{
  val aList = new DConsV1(1, DEmptyListV1)
    println(aList.head)

  //Create a linked list
  val ll = new DConsV1(3, new DConsV1(2, new DConsV1(1, DEmptyListV1)))
  println(ll.head)

  //Test the add method
  println(s"LL head ${ll.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${ll.toString}")
}