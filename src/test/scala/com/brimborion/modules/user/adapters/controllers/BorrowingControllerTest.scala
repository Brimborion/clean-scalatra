package com.brimborion.modules.user.adapters.controllers

import java.util.UUID

import com.atlassian.oai.validator.model.Request
import com.brimborion.modules.catalog.adapters.repositories.{FakeBookItemRepository, FakeBookRepository}
import com.brimborion.modules.catalog.domain.entities.mocks.BookItemMock
import com.brimborion.modules.catalog.domain.usecases.BookItemUseCases
import com.brimborion.modules.user.adapters.controllers.dtos.PostBorrowingDto
import com.brimborion.modules.user.adapters.repositories.FakeUserRepository
import com.brimborion.modules.user.adapters.services.InternalBookItemService
import com.brimborion.modules.user.domain.entities.mocks.UserMock
import com.brimborion.modules.user.domain.usecases.BorrowingUseCases
import core.{ClientResponseMapper, ComponentSpec}
import org.json4s.jackson.Serialization.write

class BorrowingControllerTest extends ComponentSpec {
  // Repositories initialization
  val userRepository = new FakeUserRepository
  val bookRepository = new FakeBookRepository
  val bookItemRepository = new FakeBookItemRepository
  // Services initialization
  val bookItemUseCases = new BookItemUseCases(bookItemRepository, bookRepository)
  val bookItemService = new InternalBookItemService(bookItemUseCases)
  // Use cases initialization
  val borrowingUseCases: BorrowingUseCases = new BorrowingUseCases(userRepository, bookItemService)

  addServlet(new BorrowingController(borrowingUseCases), "/borrowings")

  before {
    userRepository.dropData()
    bookItemRepository.dropData()
  }

  describe("POST /borrowings/users/:id") {
    it("should return 404 if user does not exists") {
      val userId = UUID.randomUUID
      val bookItemId = UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f647")
      val bookItem = new BookItemMock(bookItemId).build()
      bookItemRepository.addData(bookItem)

      val postBorrowingDto = PostBorrowingDto(bookItemId)
      val bodyRequest = write(postBorrowingDto)
      post(s"/users/${userId}", bodyRequest) {
        status should equal(404)
      }
    }
    it("should return 404 if bookItem does not exists") {
      val userId = UUID.fromString("1884323b-d91d-4411-ad08-69cda50e7f3e")
      val user = new UserMock(userId).build()
      userRepository.addData(user)
      val bookItemId = UUID.randomUUID

      val postBorrowingDto = PostBorrowingDto(bookItemId)
      val bodyRequest = write(postBorrowingDto)
      post(s"/borrowings/users/${userId}", bodyRequest) {
        status should equal(404)
      }
    }
    it("should return 409 if user can't loan more book") {
      val bookItemId = UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f649")
      val bookItemId2 = UUID.randomUUID
      val bookItemId3 = UUID.randomUUID
      val bookItemId4 = UUID.randomUUID
      val bookItemId5 = UUID.randomUUID
      val bookItemId6 = UUID.randomUUID
      bookItemRepository.addData(new BookItemMock(bookItemId).build())
      bookItemRepository.addData(new BookItemMock(bookItemId2).build())
      bookItemRepository.addData(new BookItemMock(bookItemId3).build())
      bookItemRepository.addData(new BookItemMock(bookItemId4).build())
      bookItemRepository.addData(new BookItemMock(bookItemId5).build())
      bookItemRepository.addData(new BookItemMock(bookItemId6).build())
      val userId = UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87")
      val user = new UserMock(userId)
        .setBorrowings(Vector(
          new BookItemMock(bookItemId).build(),
          new BookItemMock(bookItemId2).build(),
          new BookItemMock(bookItemId3).build(),
          new BookItemMock(bookItemId4).build(),
          new BookItemMock(bookItemId5).build()
        ))
        .build()
      userRepository.addData(user)

      val postBorrowingDto = PostBorrowingDto(bookItemId6)
      val body = write(postBorrowingDto)
      post(s"/borrowings/users/${userId}", body) {
        status should equal(409)
      }
    }
    it("should return 204 if borrowing is processed") {
      val bookItemId = UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f649")
      bookItemRepository.addData(new BookItemMock(bookItemId).build())
      val userId = UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87")
      val user = new UserMock(userId).build()
      userRepository.addData(user)

      val postBorrowingDto = PostBorrowingDto(bookItemId)
      val requestBody = write(postBorrowingDto)
      post(s"/borrowings/users/${userId}", requestBody) {
        status should equal(204)
      }
    }
    it("should return a valid response") {
      val bookItemId = UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f649")
      bookItemRepository.addData(new BookItemMock(bookItemId).build())
      val userId = UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87")
      val user = new UserMock(userId).build()
      userRepository.addData(user)

      val postBorrowingDto = PostBorrowingDto(bookItemId)
      val requestBody = write(postBorrowingDto)
      val uri = s"/borrowings/users/${userId}"
      post(uri, requestBody) {
        val validation = openApiValidator.validateResponse(uri, Request.Method.POST, ClientResponseMapper.toSimpleResponse(response))
        assert(!validation.hasErrors)
      }
    }
    it("should add bookItem to user's borrowings if borrowing is processed") {
      val bookItemId = UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f649")
      bookItemRepository.addData(new BookItemMock(bookItemId).build())
      val userId = UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87")
      val user = new UserMock(userId).build()
      userRepository.addData(user)

      val postBorrowingDto = PostBorrowingDto(bookItemId)
      val requestBody = write(postBorrowingDto)
      val uri = s"/borrowings/users/${userId}"
      post(uri, requestBody) {
        val updatedUser = userRepository.getAllData.find(user => user.id.equals(userId)).get
        assert(updatedUser.borrowings.exists(bookItem => bookItem.id.equals(bookItemId)))
      }
    }
  }

}
