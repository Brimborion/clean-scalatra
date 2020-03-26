package com.brimborion.core.controllers.serializers

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}

import org.json4s.{CustomSerializer, JNull, JString}

object JavaTimeSerializer {
  def all = List(LocalDateSerializer)
}

case object LocalDateSerializer extends CustomSerializer[LocalDate](format => ( {
  case JString(isoDate) => LocalDateTime.ofInstant(Instant.parse(isoDate), ZoneId.of("UTC")).toLocalDate
  case JNull => null
}, {
  case localDate: LocalDate => JString(localDate.atStartOfDay(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT))
}
))