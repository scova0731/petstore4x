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

  val total: BigDecimal = calculateTotal()

  def setQuantity(quantity: Int): CartItem = {
    this.copy(quantity = quantity)
  }

  def incrementQuantity(): CartItem = {
    this.copy(quantity = quantity + 1)
  }

  private def calculateTotal(): BigDecimal = {
    if (item != null && item.listPrice != null) item.listPrice.*(BigDecimal(quantity))
    else null
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

