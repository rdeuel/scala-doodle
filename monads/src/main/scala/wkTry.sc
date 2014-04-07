import Composition._
import TryMonad._
import scala.util.Try

object wkTry {
  val one_plus_sqrt_one_over_x_exc =
    plus(1) compose
    excsqrt compose
    excinvert compose
    id[Double]                                    //> one_plus_sqrt_one_over_x_exc  : Double => Double = <function1>
    
  /*
   * works ok, but the compiler doesn't make
   * me catch the exception, and the exception
   * isn't part of the type.
   */
  one_plus_sqrt_one_over_x_exc(10)                //> res0: Double = 1.3164201586865079
 // one_plus_sqrt_one_over_x_exc(-2)
  
  trysqrt(-1)                                     //> res1: scala.util.Try[Double] = Failure(java.lang.IllegalArgumentException: s
                                                  //| qrt of negative number)
  trysqrt(64)                                     //> res2: scala.util.Try[Double] = Success(8.000000371689179)
  
  val one_plus_sqrt_one_over_x_try = (x : Double) =>
    Try(x) flatMap
    tryinvert flatMap
    trysqrt flatMap
    tryplus(1)                                    //> one_plus_sqrt_one_over_x_try  : Double => scala.util.Try[Double] = <function
                                                  //| 1>
    
    one_plus_sqrt_one_over_x_try(10)              //> res3: scala.util.Try[Double] = Success(1.3164201586865079)
    one_plus_sqrt_one_over_x_try(-2)              //> res4: scala.util.Try[Double] = Failure(java.lang.IllegalArgumentException: s
                                                  //| qrt of negative number)
                                                  
    Try(1.0) flatMap tryinvert                    //> res5: scala.util.Try[Double] = Success(1.0)
    tryinvert(1.0) flatMap {x => Try(x)}          //> res6: scala.util.Try[Double] = Success(1.0)
 
    Try(0.0) flatMap tryinvert                    //> res7: scala.util.Try[Double] = Failure(java.lang.IllegalArgumentException: i
                                                  //| nvert tiny number)
    tryinvert(0.0) flatMap {x => Try(x)}          //> res8: scala.util.Try[Double] = Failure(java.lang.IllegalArgumentException: i
                                                  //| nvert tiny number)
}