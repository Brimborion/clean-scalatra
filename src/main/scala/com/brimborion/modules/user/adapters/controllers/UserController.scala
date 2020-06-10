package com.brimborion.modules.user.adapters.controllers

import java.util.UUID

import com.brimborion.core.exceptions.{DuplicateKeyException, NotFoundException}
import com.brimborion.modules.user.adapters.controllers.dtos.{PostUserDto, UserDto}
import org.scalatra.{Conflict, Created, NotFound, Ok}

trait UserController extends AbstractUserController {
  get("/:userId") {
    val userId = params("userId")

    userUseCases.getUser(UUID.fromString(userId))
      .map(user => Ok(UserDto(user)))
      .recover {
        case e: NotFoundException => NotFound(e.message)
      }
  }

  post("/") {
    val postUserDto = parsedBody.extract[PostUserDto]

    userUseCases.addUser(postUserDto.toPerson)
      .map(user => Created(UserDto(user)))
      .recover {
        case e: DuplicateKeyException => Conflict(e.message)
      }
  }
}
