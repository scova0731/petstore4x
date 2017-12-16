package com.github.scova0731.petstore4s.step2.service

import javax.inject.Inject

import scala.collection.JavaConverters._

import com.github.scova0731.petstore4s.step2.domain.{FlatOrder, Order, Sequence}
import com.github.scova0731.petstore4s.step2.repository.{ItemRepository, LineItemRepository, OrderRepository, SequenceRepository}

class OrderService @Inject()(
  repo: OrderRepository,
  itemRepo: ItemRepository,
  sequenceRepo: SequenceRepository,
  lineItemRepo: LineItemRepository
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
      itemRepo.updateInventoryQuantity(Map(
        "itemId" -> lineItem.itemId,
        "increment" -> lineItem.quantity
      ).asJava)
    }

    repo.insertOrder(newOrder)
    repo.insertOrderStatus(newOrder)

    newOrder.lineItems.foreach { lineItem =>
      lineItemRepo.insertLineItem(lineItem)
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
    val order = repo
      .getOrder(orderId)
      .copy(
        lineItems = lineItemRepo.getLineItemsByOrderId(orderId).asScala
      )

    val newOrder = order.copy(
      lineItems = order.lineItems.map { lineItem =>
        lineItem.copy(
          item = itemRepo.getItem(lineItem.itemId).copy(
            quantity = itemRepo.getInventoryQuantity(lineItem.itemId)
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
    repo.getOrdersByUsername(username).asScala.map(_.toOrder)

  /**
    * Gets the next id.
    *
    * @param name the name
    * @return the next id
    */
  def getNextId(name: String): Int = {
    var sequence = Sequence(name, -1)
    sequence = sequenceRepo.getSequence(sequence)

    if (sequence == null)
      throw new RuntimeException(
        "Error: A null sequence was returned from the database (could not get next " + name + " sequence).")

    val parameterObject = Sequence(name, sequence.nextId + 1)
    sequenceRepo.updateSequence(parameterObject)
    sequence.nextId
  }
}
