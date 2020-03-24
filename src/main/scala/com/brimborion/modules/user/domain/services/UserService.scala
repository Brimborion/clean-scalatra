package com.brimborion.modules.user.domain.services

import java.util.UUID

import com.brimborion.modules.user.domain.entities.enums.Active
import com.brimborion.modules.user.domain.entities.{Person, User}
import com.brimborion.modules.user.domain.services.interfaces.UserRepository

import scala.concurrent.Future

class UserService(val userRepository: UserRepository) {
  def getUser(id: UUID): Future[User] = userRepository.find(id)

  def addUser(person: Person): Future[User] = userRepository.create(person, borrowings = Vector.empty, status = Active)

  def disableUser(userId: UUID): Future[Unit] = userRepository.disable(userId)
}
