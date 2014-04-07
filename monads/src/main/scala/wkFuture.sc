import FutureMonad._
import scala.concurrent._
import scala.concurrent.duration._
import java.util.Date

object wkFuture {
  getTime()                                       //> res0: String = 23:17:44
  Await.ready(makeDu, Duration.Inf)               //> [23:17:50] Finished creating machine du-1 with ip 172.31.100.1
                                                  //| [23:18:00] Finished creating database instance rds-1 with ip 172.31.100.2
                                                  //| [23:18:03] Finished customization on 172.31.100.1 with params Map(ADMINUSER 
                                                  //| -> bob, dbip -> 172.31.100.2)
                                                  //| [23:18:08] Finished dns setup for bob-the-customer at 172.31.100.1
                                                  //| res1: awaitable.type = scala.concurrent.impl.Promise$DefaultPromise@558e0793
                                                  //| 
  getTime()                                       //> res2: String = 23:18:08
  Await.ready(makeDuParallel, Duration.Inf)       //> [23:18:13] Finished creating machine du-1 with ip 172.31.100.1
                                                  //| [23:18:18] Finished creating database instance rds-1 with ip 172.31.100.2
                                                  //| [23:18:21] Finished customization on 172.31.100.1 with params Map(ADMINUSER 
                                                  //| -> bob, dbip -> 172.31.100.2)
                                                  //| [23:18:23] Finished dns setup for bob-the-customer at 172.31.100.1
                                                  //| res3: awaitable.type = scala.concurrent.impl.Promise$DefaultPromise@76022cac
                                                  //| 
  println(new Date())                             //> Sun Apr 06 23:18:23 PDT 2014
}