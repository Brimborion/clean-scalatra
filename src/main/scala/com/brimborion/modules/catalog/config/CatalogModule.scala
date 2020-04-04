package com.brimborion.modules.catalog.config

import com.brimborion.core.module.{ControllerDef, Module}
import com.brimborion.modules.catalog.adapters.controllers.{AuthorController, BookController, BookItemController}
import com.brimborion.modules.catalog.adapters.repositories.{FakeAuthorRepository, FakeBookItemRepository, FakeBookRepository}
import com.brimborion.modules.catalog.domain.usecases.{AuthorUseCases, BookItemUseCases, BookUseCases}

object CatalogModule extends Module {
  // Repositories initialization
  private val bookItemRepository = new FakeBookItemRepository
  private val bookRepository = new FakeBookRepository
  private val authorRepository = new FakeAuthorRepository

  // Services initialization
  private val bookItemUseCases = new BookItemUseCases(bookItemRepository, bookRepository)
  private val bookUseCases = new BookUseCases(bookRepository)
  private val authorUseCases = new AuthorUseCases(authorRepository)

  override def getControllers: List[ControllerDef] = List(
    ControllerDef("/authors", new AuthorController(authorUseCases)),
    ControllerDef("/books", new BookController(bookUseCases)),
    ControllerDef("/book-items", new BookItemController(bookItemUseCases))
  )
}
