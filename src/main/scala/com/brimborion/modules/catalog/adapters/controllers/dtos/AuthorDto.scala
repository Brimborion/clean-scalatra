package com.brimborion.modules.catalog.adapters.controllers.dtos

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.Author

case class AuthorDto(id: UUID, name: String, birthDate: LocalDate) {
  def toAuthor: Author = Author(
    id = id,
    name = name,
    birthDate = birthDate
  )
}

object AuthorDto {
  def apply(author: Author): AuthorDto = AuthorDto(
    id = author.id,
    name = author.name,
    birthDate = author.birthDate
  )
}