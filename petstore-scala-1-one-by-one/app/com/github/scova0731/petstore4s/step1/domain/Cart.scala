package com.github.scova0731.petstore4s.step1.domain

import java.util


/**
  * The Class Cart.
  *
  */
case class Cart(
  itemMap2: Map[String, CartItem],
  itemList2: List[CartItem]
) {

  private val itemMap = new java.util.concurrent.ConcurrentHashMap[String, CartItem]
  private val itemList = new util.ArrayList[CartItem]

  def getCartItems: util.Iterator[CartItem] = itemList.iterator

  def getCartItemList: util.List[CartItem] = itemList

  def getNumberOfItems: Int = itemList.size
  lazy val getNumberOfItems2: Int = itemList2.size

  def getAllCartItems: util.Iterator[CartItem] = itemList.iterator

  def containsItemId(itemId: String): Boolean = itemMap.containsKey(itemId)

  /**
    * Adds the item.
    *
    * @param item      the item
    * @param isInStock the is in stock
    */
  def addItem(item: Item, isInStock: Boolean): Unit = {
    var cartItem = itemMap.get(item.itemId)
    if (cartItem == null) {
      cartItem = CartItem(
        item = item,
        quantity = 0,
        inStock = isInStock
      )
      itemMap.put(item.itemId, cartItem)
      itemList.add(cartItem)
    }
    cartItem.incrementQuantity()
  }

  /**
    * Removes the item by id.
    *
    * @param itemId the item id
    * @return the item
    */
  def removeItemById(itemId: String): Item = {
    val cartItem = itemMap.remove(itemId)
    if (cartItem == null) null
    else {
      itemList.remove(cartItem)
      cartItem.item
    }
  }

  /**
    * Increment quantity by item id.
    *
    * @param itemId the item id
    */
  def incrementQuantityByItemId(itemId: String): Unit = {
    val cartItem = itemMap.get(itemId)
    cartItem.incrementQuantity()
  }

  def setQuantityByItemId(itemId: String, quantity: Int): Unit = {
    val cartItem = itemMap.get(itemId)
    cartItem.setQuantity(quantity)
  }

  // immutable version
  def setQuantityByItemId2(itemId: String, quantity: Int): Map[String, CartItem] = {
    itemMap2
      .get(itemId)
      .map{ item =>
        itemMap2.updated(itemId, item.setQuantity(quantity))
      }
      .getOrElse(itemMap2)
  }

  /**
    * Gets the sub total.
    *
    * @return the sub total
    */
  def getSubTotal: BigDecimal = {
    var subTotal = BigDecimal(0)
    val items = getAllCartItems
    while ( {
      items.hasNext
    }) {
      val cartItem = items.next
      val item = cartItem.item
      val listPrice = item.listPrice
      val quantity = BigDecimal(String.valueOf(cartItem.quantity))
      subTotal = subTotal.+(listPrice.*(quantity))
    }
    subTotal
  }
}