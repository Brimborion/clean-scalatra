package com.brimborion.modules.catalog.domain.entities.enums

object BookStatus extends Enumeration {
  type BookStatus = Value
  val AVAILABLE, LOANED, LOST = Value
}