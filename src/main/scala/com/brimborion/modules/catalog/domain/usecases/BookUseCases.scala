package com.brimborion.modules.catalog.domain.usecases

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.Book
import com.brimborion.modules.catalog.domain.usecases.interfaces.BookRepository

import scala.concurrent.Future

class BookUseCases(private val bookRepository: BookRepository) {
  def addBook(isbn: String, title: String, authorId: UUID, publicationDate: LocalDate): Future[Book] =
    bookRepository.create(isbn, title, authorId, publicationDate)
}
