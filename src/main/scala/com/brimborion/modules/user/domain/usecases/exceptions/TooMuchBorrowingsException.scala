package com.brimborion.modules.user.domain.usecases.exceptions

case class TooMuchBorrowingsException(message: String) extends Exception(message)
