package com.projectx.couchbaseServices.routes

import java.util.UUID

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ ExceptionHandler, Route }
import akka.stream.ActorMaterializer
import com.projectx.couchbaseServices.factories.EventDBAccess
import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject

trait CollectorService extends EventDBAccess {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  val logger = Logging(system, getClass)

  implicit def myExceptionHandler =
    ExceptionHandler {
      case e: ArithmeticException =>
        extractUri { uri =>
          complete(HttpResponse(StatusCodes.InternalServerError, entity = s"Data is not persisted and something went wrong"))
        }
    }

  val routes: Route = {
    put {
      path("putEvent" / "event" / Segment) { (event: String) =>
        complete {
          val documentId = UUID.randomUUID().toString
          try {
            val isPersisted = putEvent(documentId, JsonObject.fromJson(event))
            isPersisted match {
              case true  => HttpResponse(StatusCodes.Created, entity = s"Data is successfully persisted with id $documentId")
              case false => HttpResponse(StatusCodes.InternalServerError, entity = s"Error found for id : $documentId")
            }
          } catch {
            case ex: Throwable =>
              logger.error(ex, ex.getMessage)
              HttpResponse(StatusCodes.InternalServerError, entity = s"Error found for id : $documentId")
          }
        }
      }
    }
  }
}
