package com.brimborion.modules.user.adapters.controllers.dtos

import java.util.UUID

import com.brimborion.modules.catalog.adapters.controllers.dtos.BookItemDto
import com.brimborion.modules.user.domain.entities.User
import com.brimborion.modules.user.domain.entities.enums.UserStatus

case class UserDto(id: String, person: PersonDto, borrowings: Vector[BookItemDto], status: String) {
  def toUser: User = User(
    id = UUID.fromString(id),
    person = person.toPerson,
    borrowings = borrowings.map(bookItemDto => bookItemDto.toBookItem),
    status = UserStatus.withName(status)
  )
}

object UserDto {
  def apply(user: User): UserDto = UserDto(
    id = user.id.toString,
    person = PersonDto(user.person),
    borrowings = user.borrowings.map(bookItem => BookItemDto(bookItem)),
    status = user.status.toString
  )
}
