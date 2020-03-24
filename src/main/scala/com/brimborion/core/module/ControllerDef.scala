package com.brimborion.core.module

import org.scalatra.ScalatraServlet

case class ControllerDef(urlPattern: String,
                         implementation: ScalatraServlet)
