package com.brimborion.modules.catalog.adapters.controllers

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.catalog.adapters.controllers.dtos.{BookItemDto, PostBookItemDto}
import com.brimborion.modules.catalog.domain.usecases.BookItemUseCases
import org.scalatra.{Created, Ok}

class BookItemController(private val bookItemUseCases: BookItemUseCases) extends CustomController {
  post("/") {
    val postBookItemDto = parsedBody.extract[PostBookItemDto]

    bookItemUseCases.addBookItem(bookIsbn = postBookItemDto.bookIsbn, tag = postBookItemDto.tag)
      .map(bookItem => Created(BookItemDto(bookItem)))
  }
}
