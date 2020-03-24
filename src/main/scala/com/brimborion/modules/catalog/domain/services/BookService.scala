package com.brimborion.modules.catalog.domain.services

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.{Author, Book}
import com.brimborion.modules.catalog.domain.services.interfaces.BookRepository

import scala.concurrent.Future

class BookService(val bookRepository: BookRepository) {
  def addBook(isbn: String, title: String, authorId: UUID, publicationDate: LocalDate): Future[Book] =
    bookRepository.create(isbn, title, authorId, publicationDate)
}
