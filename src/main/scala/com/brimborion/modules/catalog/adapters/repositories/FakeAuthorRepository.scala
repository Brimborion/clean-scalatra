package com.brimborion.modules.catalog.adapters.repositories

import java.time.LocalDate
import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.catalog.domain.entities.Author
import com.brimborion.modules.catalog.domain.entities.mocks.AuthorMock
import com.brimborion.modules.catalog.domain.services.interfaces.AuthorRepository

import scala.concurrent.Future

class FakeAuthorRepository extends AuthorRepository {
  private var authors = Vector(
    new AuthorMock(UUID.fromString("6859affc-9192-421d-841b-6985a2d2cb4f")).setName("Fakespeare").build(),
    new AuthorMock(UUID.fromString("402d8eb4-acd8-4467-9a01-e264bf4e276a")).setName("Petrone").build()
  )

  override def create(name: String, birthDate: LocalDate): Future[Author] = {
    val author = Author(UUID.randomUUID(), name, birthDate)
    authors = authors :+ author

    Future.successful(author)
  }

  override def find(id: UUID): Future[Author] = {
    val futureAuthor = authors.find(author => author.id.equals(id))
    futureAuthor match {
      case None => Future.failed(throw NotFoundException(s"Author with id ${id} not found."))
      case Some(author) => Future.successful(author)
    }
  }
}
