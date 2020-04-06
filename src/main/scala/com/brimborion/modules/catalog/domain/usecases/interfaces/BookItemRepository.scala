package com.brimborion.modules.catalog.domain.usecases.interfaces

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus

import scala.concurrent.Future

trait BookItemRepository {
  def create(bookIsbn: String, tag: String, status: BookStatus): Future[BookItem]

  @throws[NotFoundException]
  def find(id: UUID): Future[BookItem]

  def update(bookItem: BookItem): Future[BookItem]
}
