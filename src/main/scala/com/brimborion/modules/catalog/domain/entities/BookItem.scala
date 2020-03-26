package com.brimborion.modules.catalog.domain.entities

import java.util.UUID

import com.brimborion.modules.catalog.domain.entities.enums.BookStatus.BookStatus

case class BookItem(id: UUID, book: Book, tag: String, status: BookStatus)
