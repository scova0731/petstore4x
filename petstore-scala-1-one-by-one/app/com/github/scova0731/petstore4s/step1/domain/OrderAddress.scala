package com.github.scova0731.petstore4s.step1.domain

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

  implicit val reads = Json.reads[OrderAddress]
  implicit val writes = Json.writes[OrderAddress]

}
