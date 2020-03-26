package com.brimborion.modules.user.adapters.controllers.dtos

import com.brimborion.modules.user.domain.entities.Person

case class PersonDto(name: String, email: String) {
  def toPerson: Person = Person(
    name = name,
    email = email
  )
}

object PersonDto {
  def apply(person: Person): PersonDto = PersonDto(
    name = person.name,
    email = person.email
  )
}
