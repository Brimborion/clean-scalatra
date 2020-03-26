package com.brimborion.modules.catalog.adapters.controllers

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.catalog.adapters.controllers.dtos.PostBookItemDto
import com.brimborion.modules.catalog.domain.usecases.BookItemUseCases

class BookItemController(private val bookItemUseCases: BookItemUseCases) extends CustomController {
  post("/") {
    val postBookItemDto = parsedBody.extract[PostBookItemDto]

    bookItemUseCases.addBookItem(bookIsbn = postBookItemDto.bookIsbn, tag = postBookItemDto.tag)
  }
}
