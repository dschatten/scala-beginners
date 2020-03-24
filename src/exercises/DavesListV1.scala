package exercises

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DavesListV1 {

  def head: Int
  def tail: DavesListV1
  def isEmpty: Boolean
  def add(element: Integer): DavesListV1

//  override protected def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
object DavesEmptyListV1 extends DavesListV1{
  def head: Int = throw new NoSuchElementException
  def tail: DavesListV1 = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Integer): DavesListV1 = new DavesConsV1(element, DavesEmptyListV1)
}

class DavesConsV1(h: Int, t: DavesListV1) extends DavesListV1{
  def head: Int = h
  def tail: DavesListV1 = t
  def isEmpty: Boolean = false
  def add(element: Integer): DavesConsV1 = new DavesConsV1(element, this)
}

object Driver extends App{
  val aList = new DavesConsV1(1, DavesEmptyListV1)
    println(aList.head)
}