package com.brimborion.modules.catalog.adapters.controllers

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.catalog.adapters.controllers.dtos.{BookDto, PostBookDto}
import com.brimborion.modules.catalog.domain.usecases.BookUseCases
import org.scalatra.{Created, Ok}

class BookController(private val bookUseCases: BookUseCases) extends CustomController {
  post("/") {
    val postBookDto = parsedBody.extract[PostBookDto]

    bookUseCases.addBook(
      isbn = postBookDto.isbn,
      title = postBookDto.title,
      authorId = postBookDto.authorId,
      publicationDate = postBookDto.publicationDate)
      .map(book => Created(BookDto(book)))
  }
}
