package com.brimborion.modules.catalog.adapters.controllers.dtos

import java.time.LocalDate

import com.brimborion.modules.catalog.domain.entities.Book

case class BookDto(isbn: String, title: String, author: AuthorDto, publicationDate: LocalDate) {
  def toBook: Book = Book(
    isbn = isbn,
    title = title,
    author = author.toAuthor,
    publicationDate = publicationDate
  )
}

object BookDto {
  def apply(book: Book): BookDto = BookDto(
    isbn = book.isbn,
    title = book.title,
    author = AuthorDto(book.author),
    publicationDate = book.publicationDate
  )
}
