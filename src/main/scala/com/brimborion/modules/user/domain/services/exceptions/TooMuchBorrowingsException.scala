package com.brimborion.modules.user.domain.services.exceptions

case class TooMuchBorrowingsException(message: String) extends Exception(message)
