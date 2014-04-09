import scala.async.Async.{async, await}
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Promise
import scala.concurrent.future
import scala.concurrent.blocking
import scala.concurrent.ExecutionContext.Implicits.global


object FutureMonad {
  val timeFmt = new java.text.SimpleDateFormat("HH:mm:ss")
  def getTime() = timeFmt.format(new java.util.Date())
  
  def makeEc2Machine(name : String) : Future[String] = future {blocking {
    Thread.sleep(5000)
    val ip = "172.31.100.1"
    println(s"[$getTime] Finished creating machine $name with ip $ip")
    ip
  }}
  
  def makeRdsInstance(name : String) : Future[String] = future {blocking {
    Thread.sleep(10000)
    val ip = "172.31.100.2"
    println(s"[$getTime] Finished creating database instance $name with ip $ip")
    ip
  }}
  
  def runCustomization(params : Map[String, String])(ip: String) = future {blocking {
    Thread.sleep(3000)
    println(s"[$getTime] Finished customization on $ip with params $params")
  }}
	
  def setupDns(shortname: String)(ip : String) = future {blocking {
    Thread.sleep(5000)
    println(s"[$getTime] Finished dns setup for $shortname at $ip")
  }}
  
  // serial
  def makeDu = makeEc2Machine("du-1") flatMap {ec2Ip => 
    makeRdsInstance("rds-1") flatMap {rdsIp =>
      runCustomization(Map("ADMINUSER" -> "bob", "dbip" -> rdsIp))(ec2Ip) flatMap {Unit =>
        setupDns("bob-the-customer")(ec2Ip)
      }
    }
  }
  
  def all[T](fs: List[Future[T]]): Future[List[T]] = {
    val p = Promise[List[T]]()
	fs match {
	  case Nil => p.success(Nil)
	  case f :: rest => {
	    f.onComplete {
	      case Failure(ex) => p.failure(ex)
	      case Success(v) => {
	        all(rest).onComplete {
	          case Failure(ex) => p.failure(ex)
	          case Success(vs) => p.success(v :: vs)
	        }
	      }
	    }
	  }
	}
	  
	p.future
  }
  
  def makeDuParallel =
    all(List(makeEc2Machine("du-1"), makeRdsInstance("rds-1"))) flatMap {
      case (ec2Ip :: rdsIp :: Nil) => {
        all(List(runCustomization(Map("ADMINUSER" -> "bob", "dbip" -> rdsIp))(ec2Ip),
                                  setupDns("bob-the-customer")(ec2Ip)))
      }
    }
  
  /*
  val anotherDu = async {
    val ips = await(all(List(makeEc2Machine("du-1"), makeRdsInstance("rds-1"))))
    ips match {
      case (ec2Ip :: rdsIp :: Nil) =>
        all(List(runCustomization(Map("ADMINUSER" -> "bob", "dbip" -> rdsIp))(ec2Ip),
                 setupDns("bob-the-customer")(ec2Ip)))
    }
 */
  }