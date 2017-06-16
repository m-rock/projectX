package com.projectx.couchbaseServices

import java.util.UUID

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.couchbase.client.java.document.json.JsonObject
import com.projectx.couchbaseServices.routes.CollectorService
import org.scalatest.{Matchers, WordSpec}

class CollectorServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with CollectorService {

  "The CollectorService" should {

    "be able to insert data into events bucket in couchbase" in {
      Put("""/putEvent/event/{"location":"85215"}""") ~> routes ~> check {
        responseAs[String].contains("Data is successfully persisted with id") shouldEqual true
      }
    }
  }
}
