package com.brimborion.modules.catalog.domain.services.exceptions

case class UnavailableBookItemException(message: String) extends Exception(message)
