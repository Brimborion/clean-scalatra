package com.brimborion.core.controllers

import com.brimborion.core.controllers.serializers.JavaTimeSerializer
import org.json4s.ext.JavaTypesSerializers
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{FutureSupport, ScalatraServlet}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class CustomController extends ScalatraServlet with FutureSupport with JacksonJsonSupport {
  protected implicit def executor: ExecutionContextExecutor = ExecutionContext.global

  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ JavaTimeSerializer.all

  before() {
    contentType = formats("json")
  }
}
