package com.brimborion.modules.user.adapters.repositories

import java.util.UUID

import com.brimborion.core.exceptions.{DuplicateKeyException, NotFoundException}
import com.brimborion.modules.catalog.adapters.repositories.FakeBookItemRepository
import com.brimborion.modules.catalog.domain.entities.mocks.BookItemMock
import com.brimborion.modules.user.domain.entities.enums.UserStatus.UserStatus
import com.brimborion.modules.user.domain.entities.mocks.{PersonMock, UserMock}
import com.brimborion.modules.user.domain.entities.{Person, User}
import com.brimborion.modules.user.domain.usecases.interfaces.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FakeUserRepository extends UserRepository {
  val bookItemRepository = new FakeBookItemRepository
  private var users = Vector(
    new UserMock(UUID.fromString("1884323b-d91d-4411-ad08-69cda50e7f3e"))
      .setPerson(new PersonMock().setName("Toto").setEmail("toto@test.com").build())
      .build(),
    new UserMock(UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87"))
      .setPerson(new PersonMock().setName("Francis").setEmail("francis@test.com").build())
      .setBorrowings(Vector(
        new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f647")).build(),
        new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f648")).build(),
        new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f649")).build(),
        new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f650")).build(),
        new BookItemMock(UUID.fromString("ea5d62c5-fe03-46bb-a814-4db09270f651")).build()
      ))
      .build()
  )

  override def create(person: Person, borrowingIds: Vector[UUID], status: UserStatus): Future[User] = {
    if (users.exists(user => user.person.email.equals(person.email))) {
      Future.failed(throw DuplicateKeyException(s"A user with email ${person.email} already exists."))
    }

    Future.sequence(borrowingIds.map(id => bookItemRepository.find(id))).map(borrowings => {
      val user = User(
        id = UUID.randomUUID(),
        person,
        borrowings,
        status
      )
      users = users :+ user

      user
    })
  }

  override def find(id: UUID): Future[User] = {
    val maybeUser = users.find(user => user.id.equals(id))
    maybeUser match {
      case None => Future.failed(throw NotFoundException(s"User with id ${id} not found."))
      case Some(user) => Future.successful(user)
    }
  }

  override def disable(id: UUID): Future[Unit] = {
    val maybeUser = users.find(user => user.id.equals(id))
    maybeUser match {
      case None => Future.failed(throw NotFoundException(s"User with id ${id} not found."))
      case _ => Future.successful {
        users = users.filter(user => !user.id.equals(id))
      }
    }
  }

  override def update(user: User): Future[User] = {
    val index = users.indexWhere(u => u.id.equals(user.id))
    if (index == -1) {
      throw NotFoundException(s"User with id ${user.id} not found.")
    }

    users = users.updated(index, user)
    Future.successful(user)
  }

  // Only for demo tests purpose
  def dropData(): Unit = {
    users = Vector.empty
  }

  // Only for demo tests purpose
  def addData(user: User): Unit = {
    users = users :+ user
  }

  // Only for demo tests purpose
  def getAllData: Vector[User] = users
}
