package com.brimborion.modules.catalog.adapters.controllers

import java.util.UUID

import com.brimborion.core.controllers.CustomController
import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.adapters.controllers.dtos.{AuthorDto, PostAuthorDto}
import com.brimborion.modules.catalog.domain.usecases.AuthorUseCases
import org.scalatra.{NotFound, Ok}

import scala.concurrent.Future

class AuthorController(private val authorUseCases: AuthorUseCases) extends CustomController {
  post("/") {
    val postAuthorDto = parsedBody.extract[PostAuthorDto]

    authorUseCases.addAuthor(name = postAuthorDto.name, birthDate = postAuthorDto.birthDate)
      .map(author => Ok(AuthorDto(author)))
  }

  get("/:id") {
    val authorId = UUID.fromString(params("id"))

    authorUseCases.getAuthor(authorId)
      .map(author => Ok(AuthorDto(author)))
      .recoverWith{
        case e: NotFoundException => Future.successful(NotFound(e.message))
      }
  }
}
