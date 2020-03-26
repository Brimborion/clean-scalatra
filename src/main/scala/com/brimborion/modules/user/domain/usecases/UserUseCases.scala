package com.brimborion.modules.user.domain.usecases

import java.util.UUID

import com.brimborion.modules.user.domain.entities.enums.UserStatus
import com.brimborion.modules.user.domain.entities.{Person, User}
import com.brimborion.modules.user.domain.usecases.interfaces.UserRepository

import scala.concurrent.Future

class UserUseCases(private val userRepository: UserRepository) {
  def getUser(id: UUID): Future[User] = userRepository.find(id)

  def addUser(person: Person): Future[User] = userRepository.create(person, borrowings = Vector.empty, status = UserStatus.ACTIVE)

  def disableUser(userId: UUID): Future[Unit] = userRepository.disable(userId)
}
