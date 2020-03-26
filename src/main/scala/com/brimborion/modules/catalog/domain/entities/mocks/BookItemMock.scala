package com.brimborion.modules.catalog.domain.entities.mocks

import java.util.UUID

import com.brimborion.core.test.Mock
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus
import com.brimborion.modules.catalog.domain.entities.{Book, BookItem}

class BookItemMock(val id: UUID) extends Mock[BookItem] {
  private var book = new BookMock().build()
  private var tag = "LRT"
  private var status: BookStatus = BookStatus.AVAILABLE

  def setBook(book: Book): BookItemMock = {
    this.book = book
    this
  }

  def setTag(tag: String): BookItemMock = {
    this.tag = tag
    this
  }

  def setStatus(status: BookStatus): BookItemMock = {
    this.status = status
    this
  }

  override def build(): BookItem = BookItem(this.id, this.book, this.tag, this.status)
}
