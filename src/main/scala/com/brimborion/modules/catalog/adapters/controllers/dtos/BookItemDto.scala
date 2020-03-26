package com.brimborion.modules.catalog.adapters.controllers.dtos

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus

case class BookItemDto(id: UUID, book: BookDto, tag: String, status: String) {
  def toBookItem: BookItem = BookItem(
    id = id,
    book = book.toBook,
    tag = tag,
    status = BookStatus.withName(status)
  )
}

object BookItemDto {
  def apply(bookItem: BookItem): BookItemDto = BookItemDto(
    id = bookItem.id,
    book = BookDto(bookItem.book),
    tag = bookItem.tag,
    status = bookItem.status.toString
  )
}
