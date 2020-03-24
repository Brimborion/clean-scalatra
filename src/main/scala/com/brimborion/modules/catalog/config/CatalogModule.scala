package com.brimborion.modules.catalog.config

import com.brimborion.core.module.{ControllerDef, Module}
import com.brimborion.modules.catalog.adapters.repositories.{FakeAuthorRepository, FakeBookItemRepository, FakeBookRepository}
import com.brimborion.modules.catalog.domain.services.{AuthorService, BookItemService, BookService}

object CatalogModule extends Module {
  // Repositories initialization
  private val bookItemRepository = new FakeBookItemRepository
  private val bookRepository = new FakeBookRepository
  private val authorRepository = new FakeAuthorRepository

  // Services initialization
  private val bookItemService = new BookItemService(bookItemRepository, bookRepository)
  private val bookService = new BookService(bookRepository)
  private val authorService = new AuthorService(authorRepository)

  override val controllers: List[ControllerDef] = List(
  )
}
