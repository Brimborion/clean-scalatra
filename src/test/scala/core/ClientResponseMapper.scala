package core

import com.atlassian.oai.validator.model.SimpleResponse
import org.scalatra.test.ClientResponse

import scala.collection.JavaConversions._

object ClientResponseMapper {
  def toSimpleResponse(clientResponse: ClientResponse): SimpleResponse = {
    val builder = new SimpleResponse.Builder(clientResponse.status)
      .withBody(clientResponse.body)
      .withContentType(clientResponse.getContentType)

    clientResponse.getHeaderNames.foreach(headerName =>
      builder.withHeader(headerName, clientResponse.getHeaderValues(headerName).toList
    ))

    builder.build
  }
}
