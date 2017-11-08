package com.github.scova0731.petstore4s.step1.service

import javax.inject.Inject

import com.github.scova0731.petstore4s.step1.domain.{Order, Sequence}
import com.github.scova0731.petstore4s.step1.mapper.{ItemMapper, LineItemMapper, OrderMapper, SequenceMapper}

class OrderService @Inject()(
  itemMapper: ItemMapper,
  orderMapper: OrderMapper,
  sequenceMapper: SequenceMapper,
  lineItemMapper: LineItemMapper
) {


  /**
    * Insert order.
    *
    * @param order the order
    */
  //  @Transactional
  def insertOrder(order: Order): Unit = {
    val newOrder = order.copy(orderId = getNextId("ordernum"))
    var i = 0

    newOrder.lineItems.foreach { lineItem =>
      itemMapper.updateInventoryQuantity(Map(
        "itemId" -> lineItem.itemId,
        "increment" -> new Integer(lineItem.quantity)
      ))
    }

    orderMapper.insertOrder(order)
    orderMapper.insertOrderStatus(order)

    order.lineItems.foreach { lineItem =>
      val newLineItem = lineItem.copy(orderId = newOrder.orderId)
      lineItemMapper.insertLineItem(newLineItem)
    }
  }

  /**
    * Gets the order.
    *
    * @param orderId the order id
    * @return the order
    */
  //  @Transactional
  def getOrder(orderId: Int): Order = {
    val order = orderMapper
      .getOrder(orderId)
      .copy(lineItems = lineItemMapper.getLineItemsByOrderId(orderId))

    val newOrder = order.copy(lineItems = order.lineItems.map { lineItem =>
      val item = itemMapper.getItem(lineItem.itemId)
      val newItem = item.copy(quantity = itemMapper.getInventoryQuantity(lineItem.itemId))
      lineItem.copy(item = newItem)
    })

    newOrder
  }

  /**
    * Gets the orders by username.
    *
    * @param username the username
    * @return the orders by username
    */
  def getOrdersByUsername(username: String): List[Order] = orderMapper.getOrdersByUsername(username)

  /**
    * Gets the next id.
    *
    * @param name the name
    * @return the next id
    */
  def getNextId(name: String): Int = {
    var sequence = new Sequence(name, -1)
    sequence = sequenceMapper.getSequence(sequence)
    if (sequence == null) throw new RuntimeException(
      "Error: A null sequence was returned from the database (could not get next " + name + " sequence).")
    val parameterObject = new Sequence(name, sequence.nextId + 1)
    sequenceMapper.updateSequence(parameterObject)
    sequence.nextId
  }
}
