package com.brimborion.core.test

trait Mock[T] {
  def build(): T
}
