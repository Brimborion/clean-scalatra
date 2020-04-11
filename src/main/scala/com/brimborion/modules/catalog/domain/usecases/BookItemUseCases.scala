package com.brimborion.modules.catalog.domain.usecases

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus
import com.brimborion.modules.catalog.domain.usecases.interfaces.{BookItemRepository, BookRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BookItemUseCases(private val bookItemRepository: BookItemRepository, private val bookRepository: BookRepository) {
  def addBookItem(bookIsbn: String, tag: String): Future[BookItem] =
    bookItemRepository.create(bookIsbn, tag, BookStatus.AVAILABLE)

  def getBookItem(bookItemId: UUID): Future[BookItem] = bookItemRepository.find(bookItemId)

  def updateBookItemStatus(bookItemId: UUID, status: BookStatus): Future[BookItem] = {
    for {
      bookItem <- bookItemRepository.find(bookItemId)
      updatedBookItem <- bookItemRepository.update(bookItem.copy(status = status))
    } yield updatedBookItem
  }
}
