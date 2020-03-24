package com.brimborion.modules.user.config

object UserConfig {
  val maxBorrowings: Int = sys.env.getOrElse("MAX_BORROWINGS", "5").toInt
}
