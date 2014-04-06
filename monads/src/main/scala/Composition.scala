import math.abs

object Composition {

  def id[T] : (T => T) = x => x

  val sqrt = (x:Double) => {
    def iter(curr:Double) : Double = {
      val quot = x / curr
      if (abs(quot - curr) < 0.001) curr
      else iter((quot + curr) / 2)
    }
    iter(10)
  }
  
  val invert = (x:Double) => 1/x
  
  val power : Int => Double => Double = y => x => {
    def iter(pow:Int): Double = if (y == 0) 1 else x * iter(y - 1)
    iter(y)
  }
  
  val plus = (x:Double) => (y:Double) => x + y
  
  abstract class Possibly[+A] {
    def flatMap[B](f: A => Possibly[B]) : Possibly[B]
  }
  object Possibly {
    def id[A] : (A => Possibly[A]) = x => Yes(x)
  }
  case class Yes[+A](x: A) extends Possibly[A] {
    def flatMap[B](f: A => Possibly[B]) : Possibly[B] = f(x)
  }
  case class No[+A](why:String) extends Possibly[A] {
    def flatMap[B](f: A => Possibly[B]) : Possibly[B] = No(why)
  }

  val safesqrt : (Double => Possibly[Double]) = x => if (x >= 0) Yes(sqrt(x))
                               else No("sqrt of negative number: " + x)
  val safeinvert = (x:Double) => if (math.abs(x) < 1e-20) No("invert tiny number: " + x)
                                 else Yes(invert(x))
  val safeplus : (Double => Double => Possibly[Double]) = x => y => Yes(plus(x)(y))
  val safepower = (y:Int) => (x:Double) => Yes(power(y)(x))
  
}