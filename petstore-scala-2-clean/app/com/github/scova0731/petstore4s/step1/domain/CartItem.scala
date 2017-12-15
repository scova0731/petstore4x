package com.github.scova0731.petstore4s.step1.domain

import play.api.libs.json.Json

/**
  * The Class CartItem.
  */
case class CartItem(
  item: Item,
  inStock: Boolean,
  quantity: Int = 0
) {

  lazy val total: BigDecimal =
    item.listPrice * quantity

  def setQuantity(quantity: Int): CartItem = {
    this.copy(quantity = quantity)
  }

  def incrementQuantity(): CartItem = {
    this.copy(quantity = quantity + 1)
  }
}


object CartItem {

  /**
    * JSON deserializer for session cache
    */
  implicit val reads = Json.reads[CartItem]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[CartItem]
}

