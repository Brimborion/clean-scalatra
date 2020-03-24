package com.brimborion.modules.user.domain.entities.enums

sealed trait Role
case object Borrower extends Role
case object Librarian extends Role
