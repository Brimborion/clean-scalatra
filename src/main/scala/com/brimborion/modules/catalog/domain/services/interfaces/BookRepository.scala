package com.brimborion.modules.catalog.domain.services.interfaces

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.{Author, Book}

import scala.concurrent.Future

trait BookRepository {
  def create(isbn: String, title: String, authorId: UUID, publicationDate: LocalDate): Future[Book]

  def find(isbn: String): Future[Book]
}
