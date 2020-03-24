package com.brimborion.modules.user.adapters.controllers

import java.util.UUID

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.user.domain.services.UserService
import org.scalatra.Ok

class UserController(val userService: UserService) extends CustomController {
  get("/:id") {
    val id = params("id")
    userService.getUser(UUID.fromString(id)).map(user => Ok(user))
  }
}
