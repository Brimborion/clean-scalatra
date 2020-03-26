package com.brimborion.modules.catalog.domain.usecases.exceptions

case class UnavailableBookItemException(message: String) extends Exception(message)
