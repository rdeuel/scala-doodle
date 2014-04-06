import Composition._

object wk {
  
  def one_plus_sqrt_one_over_x(x: Double) =
    id(plus(1)(sqrt(invert(x))))                  //> one_plus_sqrt_one_over_x: (x: Double)Double
  
  one_plus_sqrt_one_over_x(10)                    //> res0: Double = 1.3164201586865079

  val one_plus_sqrt_one_over_x_compose =
    plus(1) compose
    sqrt compose
    invert compose
    id[Double]                                    //> one_plus_sqrt_one_over_x_compose  : Double => Double = <function1>
    
  one_plus_sqrt_one_over_x_compose(10)            //> res1: Double = 1.3164201586865079
  
  val one_plus_sqrt_one_over_x_andthen =
    id[Double] andThen
    invert andThen
    sqrt andThen
    plus(1)                                       //> one_plus_sqrt_one_over_x_andthen  : Double => Double = <function1>
    
  one_plus_sqrt_one_over_x_andthen(10)            //> res2: Double = 1.3164201586865079
  
  def safe_one_plus_sqrt_one_over_x(x:Double) =
    safeinvert(x) match {
      case Yes(v1) => safesqrt(v1) match {
        case Yes(v2) => plus(1)(v2)
        case No(msg) => No(msg)
      }
      case No(msg) => No(msg)
    }                                             //> safe_one_plus_sqrt_one_over_x: (x: Double)Any
  
  safe_one_plus_sqrt_one_over_x(0)                //> res3: Any = No(invert tiny number: 0.0)
  safe_one_plus_sqrt_one_over_x(-2)               //> res4: Any = No(sqrt of negative number: -0.5)
    
  val safe_one_plus_sqrt_one_over_x_flatmap = (x:Double) =>
    Possibly.id(x) flatMap
    safeinvert flatMap
    safesqrt flatMap
    safeplus(1.0)                                 //> safe_one_plus_sqrt_one_over_x_flatmap  : Double => Composition.Possibly[Doub
                                                  //| le] = <function1>

  safe_one_plus_sqrt_one_over_x_flatmap(0)        //> res5: Composition.Possibly[Double] = No(invert tiny number: 0.0)
  safe_one_plus_sqrt_one_over_x_flatmap(-2)       //> res6: Composition.Possibly[Double] = No(sqrt of negative number: -0.5)
  
}