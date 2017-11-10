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

  val total: BigDecimal = calculateTotal()

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

  private def calculateTotal(): BigDecimal = {
    if (item != null && item.listPrice != null)
      item.listPrice.*(quantity)
    else
      null
  }
}

object LineItem {

  implicit val reads = Json.reads[LineItem]
  implicit val writes = Json.writes[LineItem]
}
