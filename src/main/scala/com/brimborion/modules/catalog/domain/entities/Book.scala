package com.brimborion.modules.catalog.domain.entities

import java.time.LocalDate

case class Book(isbn: String, title: String, author: Author, publicationDate: LocalDate)
