package com.github.scova0731.petstore4s.step1.domain

import java.util

import play.api.libs.json.Json


/**
  * The Class Cart.
  *
  */
case class Cart(
  items: Seq[CartItem] = Seq.empty //,

//  @deprecated("Use items instead")
//  itemMap2: Map[String, CartItem] = Map.empty,
//  @deprecated("Use items instead")
//  itemList2: List[CartItem] = List.empty
) {

  val isEmpty = items.isEmpty
  val nonEmpty = items.nonEmpty

//  @deprecated("Use items instead")
//  private val itemMap = new java.util.concurrent.ConcurrentHashMap[String, CartItem]
//  @deprecated("Use items instead")
//  private val itemList = new util.ArrayList[CartItem]


//  def getAllCartItems: util.Iterator[CartItem] = itemList.iterator

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

    replaceItem(newItem)
  }

  // TODO どうにかしたい。マップを併用するのか？
  private def replaceItem(newItem: CartItem): Cart = {
    if (items.exists(_.item.itemId == newItem.item.itemId))
      Cart(items.map(ci =>
        if (ci.item.itemId == newItem.item.itemId)
          newItem
        else
          ci
      ))
    else
      Cart(items :+ newItem)
  }

  /**
    * Removes the item by id.
    *
    * @param itemId the item id
    * @return the item
    */
  def removeItemById(itemId: String): Option[Cart] = {
    if (items.exists(_.item.itemId == itemId))
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
  // TODO rewrite this one
  def subTotal: BigDecimal = {
    var subTotal = BigDecimal(0)
//    val items = getAllCartItems
//    while ( {
//      items.hasNext
//    }) {
//      val cartItem = items.next
//      val item = cartItem.item
//      val listPrice = item.listPrice
//      val quantity = BigDecimal(String.valueOf(cartItem.quantity))
//      subTotal = subTotal.+(listPrice.*(quantity))
//    }
    subTotal
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
