package exercises

//TODO Review all of this, it's quite confusing
//TODO DRS - Don't really understand why he said to make this contravariant
trait MyTransformer[-A, B]{
  def transform(myElem: A) : B
}

//TODO DRS - Don't really understand why he said to make this contravariant
trait MyPredicate[-T]{
  def test(myElem: T): Boolean
}

/*
Abstract class to build out common List operations
For different types of Lists
 */
abstract class DListV3 [+A]{

  def head: A
  def tail: DListV3[A]
  def isEmpty: Boolean
  def add[B >:A](element: B): DListV3[B]

  //TODO Confusing Methods
  //TODO DRS - WOuldn't the trait be implemented by the abstract class?    Here we're
  //defining a method that's referring to a trait in it's signature....
  def map[B](transformer: MyTransformer[A, B]) : DListV3[B]
  def flatMap[B](transformer: MyTransformer[A, DListV3[B]]): DListV3[B]
  def filter(predicate: MyPredicate[A]) : DListV3[A]

  override def toString: String = super.toString
}

//Define as object, doesn't need to be instantiated
case object DEmptyListV3 extends DListV3[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: DListV3[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): DListV3[B] = new DConsV3(element, DEmptyListV3)

  override def toString: String = " "

  //TODO - The confusing stuff
  //TODO - He copied these three methods from the abstract class, then replaced 'A' with 'Nothing'
  //The one thing that's not confusing is that each of these methods in this class just return an empty list..think I got that
  def map[B](transformer: MyTransformer[Nothing, B]) : DListV3[B] = DEmptyListV3
  def flatMap[B](transformer: MyTransformer[Nothing, DListV3[B]]): DListV3[B]= DEmptyListV3
  def filter(predicate: MyPredicate[Nothing]) : DListV3 [Nothing]= DEmptyListV3
}

case class DConsV3[+A](h: A, t: DListV3[A]) extends DListV3[A]{
  def head: A = h
  def tail: DListV3[A] = t
  def isEmpty: Boolean = false
  def add[B >:A](element: B): DConsV3[B] = new DConsV3(element, this)

  override def toString: String = {
    s"$head " + this.tail.toString
  }

//  override def flatMap[B](transformer: MyTransformer[B, DListV3[B]]): DListV3[B] = ???

  //TODO: This actually kinda makes sense as far as how he's doing the filtering
  def filter(predicate: MyPredicate[A]): DListV3[A] =
    if(predicate.test(h)) new DConsV3(h, t.filter(predicate))
    else t.filter(predicate)

  //TODO: This kinda sorta makes sense
  def map[B](transformer: MyTransformer[A, B]) : DListV3[B] =
    new DConsV3(transformer.transform(h), t.map(transformer))

  def flatMap[B](transformer: MyTransformer[A, DListV3[B]]): DListV3[B] = ???

}


//class EvenPredicate extends MyPredicate[Int] {
//  override def test[Int]: Boolean = ???
//}


object Driver3 extends App{
  val aList = new DConsV3(1, DEmptyListV3)
    println(aList.head)

  //Create a linked list
  val listofIntegers = new DConsV3(3, new DConsV3(2, new DConsV3(1, DEmptyListV3)))
  println(listofIntegers.head)

  //Test the add method
  println(s"List of Integers head ${listofIntegers.add(20).head}")

  //Test the toString method
  println(s"Printing to string: ${listofIntegers.toString}")

  //Try with another type
  val stringList = new DConsV3("My String 1",  DEmptyListV3)
  println(s"Printing string list: ${stringList.toString}")
  println(s"String list head ${stringList.head}")
  println(s"Printing string list with an added value: ${stringList.add("Another chunklet").toString}")

  //TODO - Review all of this crap
  //So we're directly instantiating a trait - MyTransformer....and providing a concrete implementation for it's override method
  //This is an Anonymous Class!!
  println("Transform list of integers by double: ")
  println(listofIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int) : Int = elem * 2
  }))
}