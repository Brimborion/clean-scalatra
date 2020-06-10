package com.brimborion.modules.user.adapters.controllers

import com.brimborion.core.controllers.CustomController
import com.brimborion.modules.user.domain.usecases.{BorrowingUseCases, UserUseCases}

abstract class AbstractUserController extends CustomController {
  protected val userUseCases: UserUseCases
  protected val borrowingUseCases: BorrowingUseCases
}
