package exercises

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV2 [+A]{

  def head: A
  def tail: DListV2[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV2[B]

  override def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
object DEmptyListV2 extends DListV2[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV2[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV2[B] = new DConsV2(element, DEmptyListV2)
  //  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

  override def toString: String = " "
}

  //case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
class DConsV2[+A](h: A, t: DListV2[A]) extends DListV2[A]{
  def head: A = h
  def tail: DListV2[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV2[B] = new DConsV2(element, this)
// def add[B >: A](element: B): MyList[B] = new Cons(element, this)
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

  //Try with another type
  val stringList = new DConsV2("My String 1",  DEmptyListV2)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")

}