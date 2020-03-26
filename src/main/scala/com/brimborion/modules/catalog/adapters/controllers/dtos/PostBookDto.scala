package com.brimborion.modules.catalog.adapters.controllers.dtos

import java.time.LocalDate
import java.util.UUID

case class PostBookDto(isbn: String, title: String, authorId: UUID, publicationDate: LocalDate)
