package com.brimborion.modules.user.adapters.controllers

import org.scalatra.test.scalatest.{ScalatraFunSuite, ScalatraSpec}

class UserControllerTest extends ScalatraSpec {

  addServlet(classOf[UserController], "/users")

  describe("GET /:id") {
  }
}
