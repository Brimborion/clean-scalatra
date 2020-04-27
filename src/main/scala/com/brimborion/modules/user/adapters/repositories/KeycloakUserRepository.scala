package com.brimborion.modules.user.adapters.repositories

import java.util.UUID

import akka.util.ByteString
import com.brimborion.core.exceptions.{DuplicateKeyException, NotFoundException}
import com.brimborion.modules.user.config.UserConfig
import com.brimborion.modules.user.domain.entities.enums.UserStatus
import com.brimborion.modules.user.domain.entities.enums.UserStatus.UserStatus
import com.brimborion.modules.user.domain.entities.{Person, User}
import com.brimborion.modules.user.domain.usecases.interfaces.UserRepository
import com.fullfacing.backend.AkkaMonixHttpBackend
import com.fullfacing.keycloak4s.admin.monix.client.{Keycloak, KeycloakClient}
import com.fullfacing.keycloak4s.admin.monix.services.Users
import com.fullfacing.keycloak4s.core.models.{ConfigWithAuth, KeycloakConfig, User => KeycloakUser}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import monix.reactive.Observable
import sttp.client.{NothingT, SttpBackend}

import scala.concurrent.Future

class KeycloakUserRepository extends UserRepository {

  private val authConfig = KeycloakConfig.Secret(
    realm = UserConfig.keycloakClientRealm,
    clientId = UserConfig.keycloakClientId,
    clientSecret = UserConfig.keycloakClientSecret
  )
  private val keycloakConfig = ConfigWithAuth(
    scheme = UserConfig.keycloakScheme,
    host = UserConfig.keycloakHost,
    port = UserConfig.keycloakPort,
    realm = UserConfig.keycloakConfigRealm,
    authn = authConfig
  )

  implicit private val backend: SttpBackend[Task, Observable[ByteString], NothingT] = AkkaMonixHttpBackend()
  private val keycloakClient: KeycloakClient[ByteString]                            = new KeycloakClient[ByteString](keycloakConfig)
  private val keycloakUserService: Users[ByteString]                                = Keycloak.Users(keycloakClient)

  private var users: Vector[User] = Vector.empty

  override def create(person: Person, status: UserStatus): Future[User] = {
    if (users.exists(user => user.person.email.equals(person.email))) {
      Future.failed(throw DuplicateKeyException(s"A user with email ${person.email} already exists."))
    }

    val keycloakUserToCreate = KeycloakUser.Create(username = person.email, enabled = status == UserStatus.ACTIVE)

    keycloakUserService
      .createAndRetrieve(keycloakUserToCreate)
      .runToFuture
      .map {
        case Left(error)        => throw error
        case Right(createdUser) => createdUser
      }
      .map { keycloakUser =>
        val user = User(id = keycloakUser.id, person, Vector.empty, status)
        users = users :+ user

        user
      }
  }

  override def find(id: UUID): Future[User] = Future {
    val maybeUser = users.find(user => user.id.equals(id))
    maybeUser match {
      case None       => throw NotFoundException(s"User with id ${id} not found.")
      case Some(user) => user
    }
  }

  override def disable(id: UUID): Future[Unit] = Future {
    val maybeUser = users.find(user => user.id.equals(id))
    maybeUser match {
      case None => throw NotFoundException(s"User with id ${id} not found.")
      case _    => users = users.filter(user => !user.id.equals(id))
    }
  }

  override def update(user: User): Future[User] = Future {
    val index = users.indexWhere(u => u.id.equals(user.id))
    if (index == -1) {
      throw NotFoundException(s"User with id ${user.id} not found.")
    }

    users = users.updated(index, user)

    user
  }

  // Only for demo tests purpose
  def dropData(): Unit =
    users = Vector.empty

  // Only for demo tests purpose
  def addData(user: User): Unit =
    users = users :+ user

  // Only for demo tests purpose
  def getAllData: Vector[User] = users
}
