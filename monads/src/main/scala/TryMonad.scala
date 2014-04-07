import Composition._
import scala.util.Try

object TryMonad {
  
  /*
   * no checked exceptions in scala, these would only be enforced if this
   * code were called from java
   */
  
  @throws(classOf[IllegalArgumentException])
  val excsqrt = (x: Double) => 
    if (x < 0) throw new IllegalArgumentException("sqrt of negative number")
    else sqrt(x)
    
  @throws(classOf[IllegalArgumentException])
  val excinvert = (x: Double) =>
    if (math.abs(x) < 1e-20) throw new IllegalArgumentException("invert tiny number")
    else invert(x)
    
 
  val trysqrt = (x:Double) => Try(excsqrt(x))
  val tryinvert = (x:Double) => Try(excinvert(x))
  val tryplus = (x:Double) => (y:Double) => Try(plus(x)(y))
}