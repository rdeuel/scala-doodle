import scala.concurrent.Future
import scala.concurrent.future
import scala.concurrent.blocking
import scala.concurrent.ExecutionContext.Implicits.global

object FutureMonad {
  def makeEc2Machine(name : String) : Future[String] = future {blocking {
    Thread.sleep(5000)
    val ip = "172.31.100.1"
    println(s"Finished creating machine $name with ip $ip")
    ip
  }}
  
  def makeRdsInstance(name : String) : Future[String] = future {blocking {
    Thread.sleep(10000)
    val ip = "172.31.100.2"
    println(s"Finished creating database instance $name with ip $ip")
    ip
  }}
  
  def runCustomization(params : Map[String, String])(ip: String) = future {blocking {
    Thread.sleep(3000)
    println(s"Finished customization on $ip with params $params")
  }}
	
  def setupDns(shortname: String)(ip : String) = future {blocking {
    Thread.sleep(5000)
    println(s"Finished dns setup for $shortname at $ip")
  }}
  
  // serial
  def makeDu = makeEc2Machine("du-1") flatMap {ec2Ip => 
    makeRdsInstance("rds-1") flatMap {rdsIp =>
      runCustomization(Map("ADMINUSER" -> "bob", "dbip" -> rdsIp))(ec2Ip) flatMap {Unit =>
        setupDns("bob-the-customer")(ec2Ip)
      }
    }
  }
  
}