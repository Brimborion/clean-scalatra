package com.brimborion.core.module

trait Module {
  def getControllers: List[ControllerDef]
}
