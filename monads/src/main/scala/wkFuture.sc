import FutureMonad._
import scala.concurrent._
import scala.concurrent.duration._
import java.util.Date

object wkFuture {
  getTime()                                       //> res0: String = 07:23:44
  Await.ready(makeDu, Duration.Inf)               //> [07:23:49] Finished creating machine du-1 with ip 172.31.100.1
                                                  //| [07:23:59] Finished creating database instance rds-1 with ip 172.31.100.2
                                                  //| [07:24:02] Finished customization on 172.31.100.1 with params Map(ADMINUSER 
                                                  //| -> bob, dbip -> 172.31.100.2)
                                                  //| [07:24:07] Finished dns setup for bob-the-customer at 172.31.100.1
                                                  //| res1: awaitable.type = scala.concurrent.impl.Promise$DefaultPromise@67d6bdeb
                                                  //| 
  //getTime()
  Await.ready(makeDuParallel, Duration.Inf)       //> [07:24:12] Finished creating machine du-1 with ip 172.31.100.1
                                                  //| [07:24:17] Finished creating database instance rds-1 with ip 172.31.100.2
                                                  //| [07:24:20] Finished customization on 172.31.100.1 with params Map(ADMINUSER 
                                                  //| -> bob, dbip -> 172.31.100.2)
                                                  //| [07:24:22] Finished dns setup for bob-the-customer at 172.31.100.1
                                                  //| res2: awaitable.type = scala.concurrent.impl.Promise$DefaultPromise@3a4b9e67
                                                  //| 
  getTime()                                       //> res3: String = 07:24:22
  //Await.ready(makeDuForComp, Duration.Inf)
}