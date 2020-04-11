package com.brimborion.modules.catalog.domain.entities

import java.time.LocalDate
import java.util.UUID

case class Author(id: UUID, name: String, birthDate: LocalDate)
