package com.brimborion.core

import com.brimborion.core.controllers.DefaultController
import org.scalatra.test.scalatest._

class DefaultControllerTests extends ScalatraSpec {

  addServlet(classOf[DefaultController], "/*")

  describe("GET *") {
    it("should always return 404") {
      get("/") {
        status should equal(404)
      }
    }
  }

}
