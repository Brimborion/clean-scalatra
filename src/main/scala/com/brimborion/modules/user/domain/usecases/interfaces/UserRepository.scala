package com.brimborion.modules.user.domain.usecases.interfaces

import java.util.UUID

import com.brimborion.core.exceptions.NotFoundException
import com.brimborion.modules.user.domain.entities.enums.UserStatus.UserStatus
import com.brimborion.modules.user.domain.entities.{Person, User}

import scala.concurrent.Future

trait UserRepository {
  def create(person: Person, status: UserStatus): Future[User]

  @throws[NotFoundException]
  def find(id: UUID): Future[User]

  def update(user: User): Future[User]

  def disable(id: UUID): Future[Unit]
}
