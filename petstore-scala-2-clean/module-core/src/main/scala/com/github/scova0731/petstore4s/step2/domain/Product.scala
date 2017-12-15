package com.github.scova0731.petstore4s.step2.domain

import play.api.libs.json.Json

/**
  * The Class Product.
  */
case class Product(
  productId: String,
  categoryId: String,
  name: String,
  description: String
)

object Product {

  /**
    * JSON deserializer for session cache
    */
  implicit val reads = Json.reads[Product]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[Product]
}
