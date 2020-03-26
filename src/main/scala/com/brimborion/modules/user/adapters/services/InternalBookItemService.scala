package com.brimborion.modules.user.adapters.services

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus
import com.brimborion.modules.catalog.domain.usecases.BookItemUseCases
import com.brimborion.modules.user.domain.usecases.interfaces.BookItemService

import scala.concurrent.Future

class InternalBookItemService(private val bookItemUseCases: BookItemUseCases) extends BookItemService {
  override def getBookItem(id: UUID): Future[BookItem] = bookItemUseCases.getBookItem(id)

  override def updateBookItemStatus(bookItemId: UUID, status: BookStatus): Future[BookItem] =
    bookItemUseCases.updateBookItemStatus(bookItemId, status)
}
