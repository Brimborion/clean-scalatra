package com.brimborion.modules.user.domain.entities.mocks

import com.brimborion.core.test.Mock
import com.brimborion.modules.user.domain.entities.Person

class PersonMock extends Mock[Person] {
  private var name = "Michel"
  private var email = "michel@test.com"

  def setName(name: String): PersonMock = {
    this.name = name
    this
  }

  def setEmail(email: String): PersonMock = {
    this.email = email
    this
  }

  def build(): Person = Person(this.name, this.email)
}
