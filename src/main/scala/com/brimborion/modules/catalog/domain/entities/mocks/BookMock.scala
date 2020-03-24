package com.brimborion.modules.catalog.domain.entities.mocks

import java.time.LocalDate
import java.util.UUID

import com.brimborion.core.test.Mock
import com.brimborion.modules.catalog.domain.entities.{Author, Book}

class BookMock extends Mock[Book] {
  private var isbn = "978-0261102385"
  private var title = "The Lord of the Rings"
  private var author = new AuthorMock(UUID.randomUUID()).build()
  private var publicationDate = LocalDate.of(1991,11,27)

  def setIsbn(isbn: String): BookMock = {
    this.isbn = isbn
    this
  }
  def setTitle(title: String): BookMock = {
    this.title = title
    this
  }
  def setAuthor(author: Author): BookMock = {
    this.author = author
    this
  }
  def setPublicationDate(publicationDate: LocalDate): BookMock = {
    this.publicationDate = publicationDate
    this
  }

  override def build(): Book = Book(this.isbn, this.title, this.author, this.publicationDate)
}
