import FutureMonad._
import scala.concurrent._
import scala.concurrent.duration._

object wkFuture {
  Await.ready(makeDu, Duration.Inf)               //> Finished creating machine du-1 with ip 172.31.100.1
                                                  //| Finished creating database instance rds-1 with ip 172.31.100.2
                                                  //| Finished customization on 172.31.100.1 with params Map(ADMINUSER -> bob, dbi
                                                  //| p -> 172.31.100.2)
                                                  //| Finished ns setup for bob-the-customer at 172.31.100.1
                                                  //| res0: awaitable.type = scala.concurrent.impl.Promise$DefaultPromise@636b1b61
                                                  //| 
}