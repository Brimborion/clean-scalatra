package com.brimborion.modules.user.adapters.repositories

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.adapters.repositories.FakeBookItemRepository
import com.brimborion.modules.user.domain.entities.enums.{Active, UserStatus}
import com.brimborion.modules.user.domain.entities.{Active, Person, User}
import com.brimborion.modules.user.domain.services.interfaces.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FakeUserRepository extends UserRepository {
  val bookItemRepository = new FakeBookItemRepository
  private var users = Vector(
    User(
      id = UUID.fromString("1884323b-d91d-4411-ad08-69cda50e7f3e"),
      person = Person(name = "Toto", email = "toto@test.com"),
      borrowings = Vector.empty,
      status = Active),
    User(
      id = UUID.fromString("699b011a-6c53-46bf-b47d-30fa1b43bd87"),
      person = Person(name = "Francis", email = "francis@test.com"),
      borrowings = Vector.empty,
      status = Active)
  )

  override def create(person: Person, borrowingIds: Vector[UUID], status: UserStatus): Future[User] = {
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
}
