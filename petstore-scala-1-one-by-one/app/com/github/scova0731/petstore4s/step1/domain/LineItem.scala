package com.github.scova0731.petstore4s.step1.domain

import play.api.libs.json.Json

/**
  * The Class LineItem.
  */
case class LineItem(
  orderId: Int,
  lineNumber: Int,
  quantity: Int,
  itemId: String,
  unitPrice: BigDecimal,
  item: Item
) {

  lazy val total: BigDecimal =
    item.listPrice * quantity

  /**
    * Instantiates a new line item.
    *
    * @param lineNumber the line number
    * @param cartItem   the cart item
    */
  def this(lineNumber: Int, cartItem: CartItem) {
    this(
      orderId = 0,
      lineNumber = lineNumber,
      quantity = cartItem.quantity,
      itemId = cartItem.item.itemId,
      unitPrice = cartItem.item.listPrice,
      item = cartItem.item
    )
  }
}

object LineItem {

  /**
    * JSON deserializer for session cache
    */
  implicit val reads = Json.reads[LineItem]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[LineItem]
}
