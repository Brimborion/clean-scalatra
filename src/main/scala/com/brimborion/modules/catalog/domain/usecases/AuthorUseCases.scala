package com.brimborion.modules.catalog.domain.usecases

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.Author
import com.brimborion.modules.catalog.domain.usecases.interfaces.AuthorRepository

import scala.concurrent.Future

class AuthorUseCases(private val authorRepository: AuthorRepository) {
  def addAuthor(name: String, birthDate: LocalDate): Future[Author] = authorRepository.create(name, birthDate)

  def getAuthor(authorId: UUID): Future[Author] = authorRepository.find(authorId)
}
