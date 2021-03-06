package com.brimborion.modules.catalog.adapters.repositories

import java.time.LocalDate
import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.Book
import com.brimborion.modules.catalog.domain.entities.mocks.BookMock
import com.brimborion.modules.catalog.domain.usecases.interfaces.BookRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FakeBookRepository extends BookRepository {
  private val authorRepository = new FakeAuthorRepository
  var books = Vector(
    new BookMock().build()
  )
  override def create(isbn: String, title: String, authorId: UUID, publicationDate: LocalDate): Future[Book] = {
    authorRepository.find(authorId).map(author => {
      val book = Book(isbn, title, author, publicationDate)
      books = books :+ book

      book
    })
  }

  override def find(isbn: String): Future[Book] = Future {
    val futureBook = books.find(book => book.isbn.equals(isbn))
    futureBook match {
      case None => throw NotFoundException(s"Book with isbn ${isbn} not found.")
      case Some(book) => book
    }
  }
}
