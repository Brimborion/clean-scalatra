package com.brimborion.core.controllers

import org.scalatra._

class DefaultController extends ScalatraServlet {
  get("*") {
    NotFound("The route you are asking for does not exists.")
  }
}
