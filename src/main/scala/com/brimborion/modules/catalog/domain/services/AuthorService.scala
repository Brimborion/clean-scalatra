package com.brimborion.modules.catalog.domain.services

import java.time.LocalDate

import com.brimborion.modules.catalog.domain.entities.Author
import com.brimborion.modules.catalog.domain.services.interfaces.AuthorRepository

import scala.concurrent.Future

class AuthorService(val authorRepository: AuthorRepository) {
  def addAuthor(name: String, birthDate: LocalDate): Future[Author] = authorRepository.create(name, birthDate)
}
