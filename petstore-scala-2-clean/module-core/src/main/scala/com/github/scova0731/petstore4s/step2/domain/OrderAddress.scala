package com.github.scova0731.petstore4s.step2.domain

import play.api.libs.json.Json

case class OrderAddress(
  toFirstName : String,
  toLastName : String,
  address1 : String,
  address2 : String,
  city : String,
  state : String,
  zip : String,
  country : String
)

object OrderAddress {

  /**
    * JSON deserializer for session cache
    */

  implicit val reads = Json.reads[OrderAddress]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[OrderAddress]

}
