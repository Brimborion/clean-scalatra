package com.brimborion.modules.user.config

import com.brimborion.core.module.{ControllerDef, Module}
import com.brimborion.modules.user.adapters.controllers.{BorrowingController, UserController}
import com.brimborion.modules.user.adapters.repositories.FakeUserRepository
import com.brimborion.modules.user.domain.services.interfaces.BookItemService
import com.brimborion.modules.user.domain.services.{BorrowingService, UserService}

object UserModule extends Module {
  // Repositories initialization
  private val userRepository = new FakeUserRepository

  // Services initialization
  private val userService = new UserService(userRepository)
  private val bookItemService: BookItemService = ???
  private val borrowingService = new BorrowingService(userRepository, bookItemService)

  override val controllers: List[ControllerDef] = List(
    ControllerDef("/users", new UserController(userService)),
    ControllerDef("/users", new BorrowingController(borrowingService))
  )
}
