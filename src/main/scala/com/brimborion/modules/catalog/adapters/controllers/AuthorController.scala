package com.brimborion.modules.catalog.adapters.controllers

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.catalog.adapters.controllers.dtos.{AuthorDto, PostAuthorDto}
import com.brimborion.modules.catalog.domain.usecases.AuthorUseCases
import org.scalatra.{Created, Ok}

class AuthorController(private val authorUseCases: AuthorUseCases) extends CustomController {
  post("/") {
    val postAuthorDto = parsedBody.extract[PostAuthorDto]

    authorUseCases.addAuthor(name = postAuthorDto.name, birthDate = postAuthorDto.birthDate)
      .map(author => Created(AuthorDto(author)))
  }
}
