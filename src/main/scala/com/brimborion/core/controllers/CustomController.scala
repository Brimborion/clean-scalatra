package com.brimborion.core.controllers

import org.scalatra.{FutureSupport, ScalatraServlet}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class CustomController extends ScalatraServlet with FutureSupport {
  protected implicit def executor: ExecutionContextExecutor = ExecutionContext.global
}
