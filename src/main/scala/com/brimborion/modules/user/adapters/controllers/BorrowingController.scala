package com.brimborion.modules.user.adapters.controllers

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.usecases.exceptions.UnavailableBookItemException
import com.brimborion.modules.user.adapters.controllers.dtos.PostBorrowingDto
import com.brimborion.modules.user.domain.usecases.exceptions.TooMuchBorrowingsException
import org.scalatra.{Conflict, NoContent, NotFound}

trait BorrowingController extends AbstractUserController {
  post("/:userId/borrowings") {
    val userId = UUID.fromString(params("userId"))
    val postBorrowingDto = parsedBody.extract[PostBorrowingDto]

    borrowingUseCases.borrowBook(userId, postBorrowingDto.bookItemId)
      .map(_ => NoContent())
      .recover {
        case e: NotFoundException => NotFound(e.message)
        case e: UnavailableBookItemException => Conflict(e.message)
        case e: TooMuchBorrowingsException => Conflict(e.message)
      }
  }
}
