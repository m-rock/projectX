package com.projectx.couchbaseServices

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.projectx.couchbaseServices.routes.CollectorService


class StartServer(implicit val system: ActorSystem,
                       implicit val materializer: ActorMaterializer) extends CollectorService {
  def startServer(address: String, port: Int) = {
    Http().bindAndHandle(routes, address, port)
  }
}

object StartApplication extends App {
  StartApp
}

object StartApp {
  implicit val system: ActorSystem = ActorSystem("Couchbase-Service")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val server = new StartServer()
  val config = server.config
  val serverUrl = config.getString("http.interface")
  val port = config.getInt("http.port")
  server.startServer(serverUrl, port)
}