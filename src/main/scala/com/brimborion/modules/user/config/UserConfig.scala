package com.brimborion.modules.user.config

object UserConfig {
  val maxBorrowings: Int           = sys.env.getOrElse("MAX_BORROWINGS", "5").toInt
  val keycloakClientRealm: String  = sys.env.getOrElse("KEYCLOAK_REALM", "demo")
  val keycloakClientId: String     = sys.env.getOrElse("KEYCLOAK_CLIENT_ID", "backend-client")
  val keycloakClientSecret: String = sys.env.getOrElse("KEYCLOAK_CLIENT_SECRET", "b753f3ba-c4e7-4f3f-ac16-a074d4d89353")
  val keycloakScheme: String       = sys.env.getOrElse("KEYCLOAK_SCHEME", "http")
  val keycloakHost: String         = sys.env.getOrElse("KEYCLOAK_HOST", "keycloak-net")
  val keycloakPort: Int            = sys.env.getOrElse("KEYCLOAK_PORT", "8081").toInt
  val keycloakConfigRealm: String  = sys.env.getOrElse("KEYCLOAK_CONFIG_REALM", "demo")
}
