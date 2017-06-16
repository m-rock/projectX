package com.projectx.couchbaseServices.factories

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject
import com.couchbase.client.java.query.N1qlQuery
import com.couchbase.client.java.view.ViewQuery
import com.couchbase.spark._
import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Try

trait EventDBAccess {

  val config = ConfigFactory.load("application.conf")
  val couchbaseUrl = config.getString("couchbase.url")
  val bucketName = config.getString("couchbase.bucketName")

  val bucketPassword = config.getString("couchbase.bucketPassword")

  val sparkConf: SparkConf = new SparkConf().setAppName("spark-akka-http-couchbase-starter-kit").setMaster("local")
    .set("com.couchbase.nodes", couchbaseUrl).set(s"com.couchbase.bucket.$bucketName", bucketPassword)
  val sc = new SparkContext(sparkConf)

  def putEvent(documentId: String, jsonObject: JsonObject): Boolean = {
    val jsonDocument = JsonDocument.create(documentId, jsonObject)
    val savedData = sc.parallelize(Seq(jsonDocument))
    Try(savedData.saveToCouchbase()).toOption.fold(false)(x => true)
  }
}

object EventDBAccess extends EventDBAccess

