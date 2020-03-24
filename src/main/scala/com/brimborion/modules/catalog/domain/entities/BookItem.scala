package com.brimborion.modules.catalog.domain.entities

import java.time.LocalDate
import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.enums.BookStatus

case class BookItem(id: UUID, book: Book, tag: String, status: BookStatus)
