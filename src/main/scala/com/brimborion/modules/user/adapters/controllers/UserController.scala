package com.brimborion.modules.user.adapters.controllers

import java.util.UUID

import com.brimborion.core.controllers.CustomController
import com.brimborion.core.exceptions.{DuplicateKeyException, NotFoundException}
import com.brimborion.modules.user.adapters.controllers.dtos.{PostUserDto, UserDto}
import com.brimborion.modules.user.domain.usecases.UserUseCases
import org.scalatra.{Conflict, Created, NotFound, Ok}

class UserController(private val userService: UserUseCases) extends CustomController {
  get("/:id") {
    val id = params("id")

    userService.getUser(UUID.fromString(id))
      .map(user => Ok(UserDto(user)))
      .recover {
        case e: NotFoundException => NotFound(e.message)
      }
  }

  post("/") {
    val postUserDto = parsedBody.extract[PostUserDto]

    userService.addUser(postUserDto.toPerson)
      .map(user => Created(UserDto(user)))
      .recover {
        case e: DuplicateKeyException => Conflict(e.message)
      }
  }
}
