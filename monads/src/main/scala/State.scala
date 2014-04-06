
import scala.util.{Left, Right, Either}

object State {
	
  case class ParseState(result : Either[String, List[Char]], rest: List[Char]) {
    def flatMap(f: List[Char] => ParseState) : ParseState = result match {
      case Left(error) => ParseState(Left(error), rest)
      case Right(ms) => f(rest) match {
        case ParseState(Left(error), newrest) => ParseState(Left(error), newrest)
        case ParseState(Right(newMs), newrest) => ParseState(Right(ms ++ newMs), newrest)
      }
    
    }   
  }

 
  val charParser = (c: Char) => (input: List[Char]) => input match {
	case curr :: rest => if (curr == c) ParseState(Right(List(c)), rest)
	                     else ParseState(Left("failed to find " + c + " at " + curr), rest)
	case Nil => ParseState(Left("No match on empty string"), List.empty[Char])
  }
  
  val id = (input : List[Char]) => ParseState(Right(List.empty[Char]), input)
  
  def or(left : List[Char] => ParseState,
         right : List[Char] => ParseState)
         (input : List[Char]) = left(input) match {
    case leftIsGood @ ParseState(Right(_), _) => leftIsGood
    case _ => right(input)
  }
}