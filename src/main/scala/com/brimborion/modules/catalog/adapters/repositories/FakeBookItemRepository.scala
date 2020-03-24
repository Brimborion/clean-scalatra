package com.brimborion.modules.catalog.adapters.repositories

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.mocks.BookItemMock
import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus
import com.brimborion.modules.catalog.domain.services.interfaces.{BookItemRepository, BookRepository}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FakeBookItemRepository extends BookItemRepository {
  private val bookRepository = new FakeBookRepository
  private var bookItems = Vector(
    new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f647")).setBook(bookRepository.books(0)).build()
  )

  override def create(bookIsbn: String, tag: String, status: BookStatus): Future[BookItem] = {
    bookRepository.find(bookIsbn).map(book => {
      val bookItem = BookItem(id = UUID.randomUUID(),book,tag,status)
      bookItems = bookItems :+ bookItem

      bookItem
    })
  }

  override def find(id: UUID): Future[BookItem] = {
    val futureBookItem = bookItems.find(bookItem => bookItem.id.equals(id))
    futureBookItem match {
      case None => Future.failed(throw NotFoundException(s"BookItom with id ${id} not found."))
      case Some(bookItem) => Future.successful(bookItem)
    }
  }

  override def update(bookItem: BookItem): Future[BookItem] = {
    val index = bookItems.indexWhere(bi => bi.id.equals(bookItem.id))
    if (index == -1) {
      throw NotFoundException(s"BookItem with id ${bookItem.id} not found.")
    }

    bookItems = bookItems.updated(index, bookItem)
    Future.successful(bookItem)
  }
}
