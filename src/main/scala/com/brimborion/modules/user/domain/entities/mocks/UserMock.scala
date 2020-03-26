package com.brimborion.modules.user.domain.entities.mocks

import java.util.UUID

import com.brimborion.core.test.Mock
import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.user.domain.entities.enums.UserStatus
import com.brimborion.modules.user.domain.entities.enums.UserStatus.UserStatus
import com.brimborion.modules.user.domain.entities.{Person, User}

class UserMock(val id: UUID) extends Mock[User] {
  private var person: Person = new PersonMock().build()
  private var borrowings: Vector[BookItem] = Vector.empty
  private var status: UserStatus = UserStatus.ACTIVE

  def setPerson(person: Person): UserMock = {
    this.person = person
    this
  }

  def setBorrowings(borrowings: Vector[BookItem]): UserMock = {
    this.borrowings = borrowings
    this
  }

  def status(status: UserStatus): UserMock = {
    this.status = status
    this
  }

  override def build(): User = User(this.id, this.person, this.borrowings, this.status)
}
