package com.brimborion.modules.user.adapters.controllers

import com.brimborion.modules.user.domain.usecases.{BorrowingUseCases, UserUseCases}

class MainUserController(val userUseCases: UserUseCases, val borrowingUseCases: BorrowingUseCases) extends AbstractUserController
  with BorrowingController
  with UserController
