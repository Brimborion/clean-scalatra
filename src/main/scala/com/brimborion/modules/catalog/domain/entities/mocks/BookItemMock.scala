package com.brimborion.modules.catalog.domain.entities.mocks

import java.time.LocalDate
import java.util.UUID

import com.brimborion.core.test.Mock
import com.brimborion.modules.catalog.domain.entities.enums.{Available, BookStatus}
import com.brimborion.modules.catalog.domain.entities.{Available, Book, BookItem}

class BookItemMock(val id: UUID) extends Mock[BookItem] {
  private var book = new BookMock().build()
  private var tag = "LRT"
  private var status: BookStatus = Available

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
