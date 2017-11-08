package com.github.scova0731.petstore4s.step1.domain

/**
  * The Class CartItem.
  */
case class CartItem(
  item: Item = null,
  quantity: Int = 0,
  inStock: Boolean
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
