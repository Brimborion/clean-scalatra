package com.brimborion.modules.user.adapters.controllers.dtos

import com.brimborion.modules.user.domain.entities.Person

case class PostUserDto(name: String, email: String) {
  def toPerson: Person = Person(
    name = name,
    email = email
  )
}
