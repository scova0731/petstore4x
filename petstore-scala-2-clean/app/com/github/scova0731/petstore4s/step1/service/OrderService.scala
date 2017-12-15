package com.github.scova0731.petstore4s.step1.service

import javax.inject.Inject

import scala.collection.JavaConverters._

import com.github.scova0731.petstore4s.step1.domain.{FlatOrder, Order, Sequence}
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
  def insertOrder(order: FlatOrder): Int = {
    val newOrderId = getNextId("ordernum")
    val newOrder = order.copy(
      orderId = newOrderId,
      lineItems = order.lineItems.map(
        _.copy(orderId = newOrderId)
      )
    )

    newOrder.lineItems.foreach { lineItem =>
      itemMapper.updateInventoryQuantity(Map(
        "itemId" -> lineItem.itemId,
        "increment" -> lineItem.quantity
      ).asJava)
    }

    orderMapper.insertOrder(newOrder)
    orderMapper.insertOrderStatus(newOrder)

    newOrder.lineItems.foreach { lineItem =>
      lineItemMapper.insertLineItem(lineItem)
    }

    newOrder.orderId
  }

  /**
    * Gets the order.
    *
    * @param orderId the order id
    * @return the order
    */
  def getOrder(orderId: Int): Order = {
    val order = orderMapper
      .getOrder(orderId)
      .copy(
        lineItems = lineItemMapper.getLineItemsByOrderId(orderId).asScala
      )

    val newOrder = order.copy(
      lineItems = order.lineItems.map { lineItem =>
        lineItem.copy(
          item = itemMapper.getItem(lineItem.itemId).copy(
            quantity = itemMapper.getInventoryQuantity(lineItem.itemId)
          )
        )
      }
    )

    newOrder.toOrder
  }

  /**
    * Gets the orders by username.
    *
    * @param username the username
    * @return the orders by username
    */
  def getOrdersByUsername(username: String): Seq[Order] =
    orderMapper.getOrdersByUsername(username).asScala.map(_.toOrder)

  /**
    * Gets the next id.
    *
    * @param name the name
    * @return the next id
    */
  def getNextId(name: String): Int = {
    var sequence = Sequence(name, -1)
    sequence = sequenceMapper.getSequence(sequence)

    if (sequence == null)
      throw new RuntimeException(
        "Error: A null sequence was returned from the database (could not get next " + name + " sequence).")

    val parameterObject = new Sequence(name, sequence.nextId + 1)
    sequenceMapper.updateSequence(parameterObject)
    sequence.nextId
  }
}
