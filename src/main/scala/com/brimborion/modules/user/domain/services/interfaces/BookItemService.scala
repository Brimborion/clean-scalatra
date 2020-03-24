package com.brimborion.modules.user.domain.services.interfaces

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus

import scala.concurrent.Future

trait BookItemService {
  def getBookItem(id: UUID): Future[BookItem]

  def updateBookItemStatus(bookItemId: UUID, status: BookStatus): Future[BookItem]
}
