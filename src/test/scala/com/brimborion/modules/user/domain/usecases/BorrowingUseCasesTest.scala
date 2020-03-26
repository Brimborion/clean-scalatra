package com.brimborion.modules.user.domain.usecases

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.enums.BookStatus
import com.brimborion.modules.catalog.domain.entities.mocks.BookItemMock
import com.brimborion.modules.catalog.domain.usecases.exceptions.UnavailableBookItemException
import com.brimborion.modules.user.domain.entities.mocks.UserMock
import com.brimborion.modules.user.domain.usecases.exceptions.TooMuchBorrowingsException
import com.brimborion.modules.user.domain.usecases.interfaces.{BookItemService, UserRepository}
import core.UseCasesSpec

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class BorrowingUseCasesTest extends UseCasesSpec {
  private val userRepositoryMock = stub[UserRepository]
  private val bookItemServiceMock = stub[BookItemService]
  private val borrowingUseCases = new BorrowingUseCases(userRepositoryMock, bookItemServiceMock)

  describe("borrowBook") {
    it("should throw an exception if user is not found") {
      val bookItemId = UUID.randomUUID()
      val bookFindMock = (id: UUID) => Future.successful(new BookItemMock(bookItemId).build())
      (bookItemServiceMock.getBookItem _).when(bookItemId).onCall(bookFindMock)
      val userFindMock = (id: UUID) => Future.failed(throw NotFoundException("Not found"))
      (userRepositoryMock.find _).when(*).onCall(userFindMock)

      intercept[NotFoundException] {
        Await.result(borrowingUseCases.borrowBook(userId = UUID.randomUUID(), bookItemId), 10 seconds)
      }
    }
    it("should throw an exception if book is not found") {
      val userId = UUID.randomUUID()

      val userFindMock = (id: UUID) => Future.successful(new UserMock(userId).build())
      (userRepositoryMock.find _).when(userId).onCall(userFindMock)
      val bookFindMock = (id: UUID) => Future.failed(throw NotFoundException("Not found"))
      (bookItemServiceMock.getBookItem _).when(*).onCall(bookFindMock)

      intercept[NotFoundException] {
        Await.result(borrowingUseCases.borrowBook(userId, bookItemId = UUID.randomUUID()), 10 seconds)
      }
    }
    it("should not be able to borrow a book if it is not available") {
      val userId = UUID.randomUUID()
      val bookItemId = UUID.randomUUID()
      val bookItem = new BookItemMock(bookItemId).setStatus(BookStatus.LOANED).build()

      val userFindMock = (id: UUID) => Future.successful(new UserMock(userId).build())
      (userRepositoryMock.find _).when(userId).onCall(userFindMock)
      val bookFindMock = (id: UUID) => Future.successful(bookItem)
      (bookItemServiceMock.getBookItem _).when(bookItemId).onCall(bookFindMock)

      intercept[UnavailableBookItemException] {
        Await.result(borrowingUseCases.borrowBook(userId, bookItemId), 10 seconds)
      }
    }
    it("should not be able to borrow a book if user has already borrowed the maximum quantity of books") {
      val userId = UUID.randomUUID()
      val bookItemId = UUID.randomUUID()
      val borrowings = Vector(
        new BookItemMock(UUID.randomUUID()).build(),
        new BookItemMock(UUID.randomUUID()).build(),
        new BookItemMock(UUID.randomUUID()).build(),
        new BookItemMock(UUID.randomUUID()).build(),
        new BookItemMock(UUID.randomUUID()).build()
      )
      val user = new UserMock(userId).setBorrowings(borrowings).build()

      val userFindMock = (id: UUID) => Future.successful(user)
      (userRepositoryMock.find _).when(userId).onCall(userFindMock)
      val bookFindMock = (id: UUID) => Future.successful(new BookItemMock(bookItemId).build())
      (bookItemServiceMock.getBookItem _).when(bookItemId).onCall(bookFindMock)

      intercept[TooMuchBorrowingsException] {
        Await.result(borrowingUseCases.borrowBook(userId, bookItemId), 10 seconds)
      }
    }
    it("should update item book with status BookStatus.LOANED") {
      val userId = UUID.randomUUID()
      val bookItemId = UUID.randomUUID()
      val user = new UserMock(userId).build()
      val bookItem = new BookItemMock(bookItemId).build()

      val expectedBookItem = bookItem.copy(status = BookStatus.LOANED)
      val expectedUser = user.copy(borrowings = Vector(expectedBookItem))

      val userFindMock = (id: UUID) => Future.successful(user)
      (userRepositoryMock.find _).when(userId).onCall(userFindMock)
      val bookFindMock = (id: UUID) => Future.successful(bookItem)
      (bookItemServiceMock.getBookItem _).when(bookItemId).onCall(bookFindMock)
      (bookItemServiceMock.updateBookItemStatus _).when(*, *).returns(Future.successful(expectedBookItem))
      (userRepositoryMock.update _).when(*).returns(Future.successful(expectedUser))

      Await.result(borrowingUseCases.borrowBook(userId, bookItemId), 10 seconds)

      (bookItemServiceMock.updateBookItemStatus _).verify(bookItemId, BookStatus.LOANED).once()
    }
    it("should add book to user's borrowings") {
      val userId = UUID.randomUUID()
      val bookItemId = UUID.randomUUID()
      val user = new UserMock(userId).build()
      val bookItem = new BookItemMock(bookItemId).build()

      val expectedBookItem = bookItem.copy(status = BookStatus.LOANED)
      val expectedUser = user.copy(borrowings = Vector(expectedBookItem))

      val userFindMock = (id: UUID) => Future.successful(user)
      (userRepositoryMock.find _).when(userId).onCall(userFindMock)
      val bookFindMock = (id: UUID) => Future.successful(bookItem)
      (bookItemServiceMock.getBookItem _).when(bookItemId).onCall(bookFindMock)
      (bookItemServiceMock.updateBookItemStatus _).when(*, *).returns(Future.successful(expectedBookItem))
      (userRepositoryMock.update _).when(*).returns(Future.successful(expectedUser))

      Await.result(borrowingUseCases.borrowBook(userId, bookItemId), 10 seconds)

      (userRepositoryMock.update _).verify(expectedUser).once()
    }
  }

  describe("returnBook") {
    it("should returnBook") {

    }
  }
}
