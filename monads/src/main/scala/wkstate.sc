import State._

object wkstate {
  val pB = charParser('B')                        //> pB  : List[Char] => State.ParseState = <function1>
  pB("Bob".toList)                                //> res0: State.ParseState = ParseState(Right(List(B)),List(o, b))
  pB("Steve".toList)                              //> res1: State.ParseState = ParseState(Left(failed to find B at S),List(t, e, v
                                                  //| , e))
  
  val pBob = (input:List[Char]) =>
    id(input) flatMap
    charParser('B') flatMap
    charParser('o') flatMap
    charParser('b')                               //> pBob  : List[Char] => State.ParseState = <function1>
    
  pBob("Bob".toList)                              //> res2: State.ParseState = ParseState(Right(List(B, o, b)),List())
  pBob("Barb".toList)                             //> res3: State.ParseState = ParseState(Left(failed to find o at a),List(r, b))
                                                  //| 
  
  val pDeuel = (input:List[Char]) =>
    id(input) flatMap
    charParser('D') flatMap
    charParser('e') flatMap
    charParser('u') flatMap
    charParser('e') flatMap
    charParser('l')                               //> pDeuel  : List[Char] => State.ParseState = <function1>
    

  val pBobDeuel = (input:List[Char]) =>
     pBob(input) flatMap charParser(' ') flatMap pDeuel
                                                  //> pBobDeuel  : List[Char] => State.ParseState = <function1>
     
  pBobDeuel("Bob Deuel".toList)                   //> res4: State.ParseState = ParseState(Right(List(B, o, b,  , D, e, u, e, l)),L
                                                  //| ist())
  pBobDeuel("Bob Denver".toList)                  //> res5: State.ParseState = ParseState(Left(failed to find u at n),List(v, e, r
                                                  //| ))
  
  val pDenver = (input:List[Char]) =>
    id(input) flatMap
    charParser('D') flatMap
    charParser('e') flatMap
    charParser('n') flatMap
    charParser('v') flatMap
    charParser('e') flatMap
    charParser('r')                               //> pDenver  : List[Char] => State.ParseState = <function1>
 
 val programmer_or_singer = (input: List[Char]) =>
     id(input) flatMap
     pBob flatMap
     charParser(' ') flatMap
     or(pDeuel, pDenver)                          //> programmer_or_singer  : List[Char] => State.ParseState = <function1>
                                                  
 programmer_or_singer("Bob Deuel".toList)         //> res6: State.ParseState = ParseState(Right(List(B, o, b,  , D, e, u, e, l)),
                                                  //| List())
 programmer_or_singer("Bob Denver".toList)        //> res7: State.ParseState = ParseState(Right(List(B, o, b,  , D, e, n, v, e, r
                                                  //| )),List())
 programmer_or_singer("Bob Barker".toList)        //> res8: State.ParseState = ParseState(Left(failed to find D at B),List(a, r, 
                                                  //| k, e, r))
 
 val gw = wordParser("George Washington")         //> gw  : List[Char] => State.ParseState = <function1>
 gw("George Washington".toList)                   //> res9: State.ParseState = ParseState(Right(List(G, e, o, r, g, e,  , W, a, s
                                                  //| , h, i, n, g, t, o, n)),List())
 gw("George Jefferson".toList)                    //> res10: State.ParseState = ParseState(Left(failed to find W at J),List(e, f,
                                                  //|  f, e, r, s, o, n))
 
 numberOnly("0".toList)                           //> res11: State.ParseState = ParseState(Right(List(0)),List())
 numberOnly("a".toList)                           //> res12: State.ParseState = ParseState(Left(failed to find 9 at a),List())
 number("01234".toList)                           //> res13: Product with Serializable with scala.util.Either[String,Int] = Right
                                                  //| (0)
}