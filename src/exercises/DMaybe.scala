package exercises

/*
  Collection data structure for at most one element.
 */
abstract class DMaybe[+A] {
  def map[B](transformer: A => B): DMaybe[B]
  def flatMap[B](transformer: A => DMaybe[B]): DMaybe[B]
  def filter(predicate: A => Boolean): DMaybe[A]
}

case object DMaybeEmpty extends DMaybe[Nothing] {
  def map[B](transformer: Nothing => B): DMaybe[B] = DMaybeEmpty

  def flatMap[B](transformer: Nothing => DMaybe[B]): DMaybe[B] = DMaybeEmpty

  def filter(predicate: Nothing => Boolean): DMaybe[Nothing] = DMaybeEmpty

}

//case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
case class DMaybeOne[+A](value: A) extends DMaybe[A] {

  def map[B](transformer: A => B): DMaybe[B] =
    new DMaybeOne(transformer(value))

  //  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
  //    transformer(h) ++ t.flatMap(transformer)
  //
  def flatMap[B](transformer: A => DMaybe[B]): DMaybe[B] =
    transformer(value)

  //  def filter(predicate: A => Boolean): MyList[A] =
  //    if (predicate(h)) new Cons(h, t.filter(predicate))
  //    else t.filter(predicate)
  def filter(predicate: A => Boolean): DMaybe[A] =
    if (predicate(value)) this
    else DMaybeEmpty

}

object DMaybeDriver extends App{
  var dMaybeOne = new DMaybeOne(1)
  println(dMaybeOne)
  println(dMaybeOne.map(x => x + 1))
  println(dMaybeOne.filter(_ % 2 == 0))

  println(dMaybeOne.flatMap(elem => new DMaybeOne(elem + 1)).toString)


}