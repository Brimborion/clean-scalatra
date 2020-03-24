package com.brimborion.modules.catalog.domain.services.interfaces

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.{Author, Book}

import scala.concurrent.Future

trait AuthorRepository {
  def create(name: String, birthDate: LocalDate): Future[Author]

  def find(id: UUID): Future[Author]
}
