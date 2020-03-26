package core

import com.brimborion.core.controllers.serializers.JavaTimeSerializer
import org.json4s.ext.JavaTypesSerializers
import org.json4s.{DefaultFormats, Formats}
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfter
import org.scalatra.test.scalatest.ScalatraSpec

trait ComponentSpec extends ScalatraSpec with MockFactory with BeforeAndAfter {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ JavaTimeSerializer.all
}