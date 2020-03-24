package com.brimborion.modules.user.domain.entities.enums

sealed trait UserStatus
case object Active extends UserStatus
case object Closed extends UserStatus
