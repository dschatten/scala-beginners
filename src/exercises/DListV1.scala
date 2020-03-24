package exercises

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV1 {

  def head: Int
  def tail: DListV1
  def isEmpty: Boolean
  def add(element: Integer): DListV1

//  override protected def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
object DEmptyListV1 extends DListV1{
  def head: Int = throw new NoSuchElementException
  def tail: DListV1 = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Integer): DListV1 = new DConsV1(element, DEmptyListV1)
}

class DConsV1(h: Int, t: DListV1) extends DListV1{
  def head: Int = h
  def tail: DListV1 = t
  def isEmpty: Boolean = false
  def add(element: Integer): DConsV1 = new DConsV1(element, this)
}

object Driver extends App{
  val aList = new DConsV1(1, DEmptyListV1)
    println(aList.head)

  //Create a linked list
  var ll = new DConsV1(3, new DConsV1(2, new DConsV1(1, DEmptyListV1)))
  println(ll.head)
}