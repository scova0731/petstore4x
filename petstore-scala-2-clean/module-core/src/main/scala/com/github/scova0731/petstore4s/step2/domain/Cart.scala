package com.github.scova0731.petstore4s.step2.domain

import play.api.libs.json.Json


/**
  * The Class Cart.
  *
  */
case class Cart(
  items: Seq[CartItem] = Seq.empty
) {

  val isEmpty = items.isEmpty
  val nonEmpty = items.nonEmpty

  def containsItemId(itemId: String): Boolean =
    items.exists(_.item.itemId == itemId)

  /**
    * Adds the item.
    *
    * @param item      the item
    * @param isInStock the is in stock
    */
  def addItem(item: Item, isInStock: Boolean): Cart = {
    val newItem = items
      .find(_.item.itemId == item.itemId)
      .getOrElse(CartItem(
          item = item,
          inStock = isInStock
        ))
      .incrementQuantity()

    replaceOrAddItem(newItem)
  }

  /**
    * Removes the item by id.
    *
    * @param itemId the item id
    * @return the item
    */
  def removeItemById(itemId: String): Option[Cart] = {
    if (containsItemId(itemId))
      Some(Cart(items.filter(_.item.itemId != itemId)))
    else
      None
  }

  /**
    * Increment quantity by item id.
    *
    * @param itemId the item id
    */
  def incrementQuantityByItemId(itemId: String): Cart = {
    Cart(items.map { ci =>
      if (ci.item.itemId == itemId)
        ci.incrementQuantity()
      else
        ci
    })
  }

  def setQuantityByItemId(itemId: String, quantity: Int): Cart = {
    Cart(items.map { ci =>
      if (ci.item.itemId == itemId)
        ci.setQuantity(quantity)
      else
        ci
    })

  }

  /**
    * Gets the sub total.
    *
    * @return the sub total
    */
  lazy val subTotal: BigDecimal = {
    items.map { cartItem =>
      cartItem.item.listPrice * cartItem.quantity
    }.sum
  }

  // NOTE Added by scova0731
  private def replaceOrAddItem(newItem: CartItem): Cart = {
    if (containsItemId((newItem.item.itemId)))
      Cart(items.map(ci =>
        if (ci.item.itemId == newItem.item.itemId)
          newItem
        else
          ci
      ))
    else
      Cart(items :+ newItem)
  }
}

object Cart {

  /**
    * JSON deserializer for session cache
    */
  implicit val reads = Json.reads[Cart]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[Cart]
}
