package com.brimborion.modules.user.domain.entities

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.BookItem
import com.brimborion.modules.user.domain.entities.enums.UserStatus

case class User(id: UUID, person: Person, borrowings: Vector[BookItem], status: UserStatus)
