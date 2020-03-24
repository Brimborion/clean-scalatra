package com.brimborion.modules.catalog.domain.entities.enums

sealed trait BookStatus
case object Available extends BookStatus
case object Loaned extends BookStatus
case object Lost extends BookStatus