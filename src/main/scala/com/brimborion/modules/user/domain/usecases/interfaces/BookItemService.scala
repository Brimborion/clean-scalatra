package com.brimborion.modules.user.domain.usecases.interfaces

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus

import scala.concurrent.Future

trait BookItemService {
  @throws[NotFoundException]
  def getBookItem(id: UUID): Future[BookItem]

  def updateBookItemStatus(bookItemId: UUID, status: BookStatus): Future[BookItem]
}
