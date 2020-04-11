package com.brimborion.core.controllers

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.model.{Request, SimpleRequest}
import com.brimborion.core.controllers.serializers.JavaTimeSerializer
import javax.servlet.http.HttpServletRequest
import org.json4s.ext.JavaTypesSerializers
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{BadRequest, FutureSupport, NotFound, ScalatraServlet}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

trait CustomController extends ScalatraServlet with FutureSupport with JacksonJsonSupport {
  protected implicit def executor: ExecutionContextExecutor = ExecutionContext.global

  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ JavaTimeSerializer.all

  before() {
    contentType = formats("json")

    val validator = servletContext.getAttribute("openApiValidator").asInstanceOf[OpenApiInteractionValidator]
    val validation = validator.validateRequest(servletRequestToSimpleRequest(request, request.getRequestURI, request.body))
    if (validation.hasErrors) {
      val messages = validation.getMessages.asScala

      if (messages.exists(message => message.getKey == "validation.request.path.missing")) {
        halt(NotFound(s"Route ${request.getMethod} ${request.getRequestURI} does not exists."))
      } else {
        halt(BadRequest(validation.getMessages.asScala.map(message => (message.getKey, message.getMessage))))
      }
    }
  }

  private def servletRequestToSimpleRequest(servletRequest: HttpServletRequest, path: String, body: String): SimpleRequest = {
    val method = Request.Method.valueOf(servletRequest.getMethod)
    val builder: SimpleRequest.Builder = new SimpleRequest.Builder(method, path)

    if (!body.isEmpty) {
      builder.withBody(body)
    }

    servletRequest.getHeaderNames
      .foreach(headerName => builder.withHeader(headerName, servletRequest.getHeaders(headerName).toList))
    servletRequest.getParameterNames
      .foreach(paramName => builder.withQueryParam(paramName, servletRequest.getParameterValues(paramName).toList))

    builder.build
  }
}
