package com.brimborion.modules.user.domain.usecases

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.enums.BookStatus
import com.brimborion.modules.catalog.domain.usecases.exceptions.UnavailableBookItemException
import com.brimborion.modules.user.config.UserConfig
import com.brimborion.modules.user.domain.usecases.exceptions.TooMuchBorrowingsException
import com.brimborion.modules.user.domain.usecases.interfaces.{BookItemService, UserRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BorrowingUseCases(private val userRepository: UserRepository, private val bookItemService: BookItemService) {
  def borrowBook(userId: UUID, bookItemId: UUID): Future[Unit] =
    for {
      user <- userRepository.find(userId)
      bookItem <- bookItemService.getBookItem(bookItemId)
    } yield {
      if (!bookItem.status.equals(BookStatus.AVAILABLE)) {
        throw UnavailableBookItemException(s"Book with id ${bookItemId} is has status ${bookItem.status}")
      }
      if (user.borrowings.size >= UserConfig.maxBorrowings) {
        throw TooMuchBorrowingsException(s"Maximum borrowings is ${UserConfig.maxBorrowings}")
      }

      bookItemService.updateBookItemStatus(bookItemId = bookItem.id, status = BookStatus.LOANED).map(updatedBookItem => {
        val userToUpdate = user.copy(borrowings = user.borrowings :+ updatedBookItem)
        userRepository.update(userToUpdate)
      })
    }

  def returnBook(userId: UUID, bookItemId: UUID): Future[Unit] = ???
}