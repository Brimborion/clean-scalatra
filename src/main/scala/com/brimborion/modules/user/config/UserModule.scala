package com.brimborion.modules.user.config

import com.brimborion.core.module.{ControllerDef, Module}
import com.brimborion.modules.catalog.adapters.repositories.{FakeBookItemRepository, FakeBookRepository}
import com.brimborion.modules.catalog.domain.usecases.BookItemUseCases
import com.brimborion.modules.user.adapters.controllers.{BorrowingController, MainUserController, UserController}
import com.brimborion.modules.user.adapters.repositories.FakeUserRepository
import com.brimborion.modules.user.adapters.services.InternalBookItemService
import com.brimborion.modules.user.domain.usecases.{BorrowingUseCases, UserUseCases}

object UserModule extends Module {
  // Repositories initialization
  private val userRepository = new FakeUserRepository
  private val bookRepository = new FakeBookRepository
  private val bookItemRepository = new FakeBookItemRepository
  // Services initialization
  private val bookItemUseCases = new BookItemUseCases(bookItemRepository, bookRepository)
  private val bookItemService = new InternalBookItemService(bookItemUseCases)
  // Use cases initialization
  private val userUseCases = new UserUseCases(userRepository)
  private val borrowingUseCases = new BorrowingUseCases(userRepository, bookItemService)

  override def getControllers: List[ControllerDef] = List(
    ControllerDef("/users", new MainUserController(userUseCases, borrowingUseCases))
  )
}
