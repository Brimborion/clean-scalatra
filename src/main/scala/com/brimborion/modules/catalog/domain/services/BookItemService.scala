package com.brimborion.modules.catalog.domain.services

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.enums.{Available, BookStatus}
import com.brimborion.modules.catalog.domain.entities.{Available, BookItem}
import com.brimborion.modules.catalog.domain.services.interfaces.{BookItemRepository, BookRepository}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class BookItemService(val bookItemRepository: BookItemRepository, val bookRepository: BookRepository) {
  def addBookItem(bookIsbn: String, tag: String): Future[BookItem] =
    bookItemRepository.create(bookIsbn, tag, Available)

  def getBookItem(bookItemId: UUID): Future[BookItem] = bookItemRepository.find(bookItemId)

  def updateBookItemStatus(bookItemId: UUID, status: BookStatus): Future[BookItem] =
    bookItemRepository.find(bookItemId).flatMap(bookItem => bookItemRepository.update(bookItem.copy(status = status)))
}
