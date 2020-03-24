package com.brimborion.modules.catalog.domain.entities.mocks

import java.time.LocalDate
import java.util.UUID

import com.brimborion.core.test.Mock
import com.brimborion.modules.catalog.domain.entities.Author

class AuthorMock(val id: UUID) extends Mock[Author] {
  private var name: String = "Tolkien"
  private var birthDate: LocalDate = LocalDate.of(1892, 1, 3)

  def setName(name: String): AuthorMock = {
    this.name = name
    this
  }

  def setBirthDate(birthDate: LocalDate): AuthorMock = {
    this.birthDate = birthDate
    this
  }

  override def build(): Author = Author(this.id, this.name, this.birthDate)
}
