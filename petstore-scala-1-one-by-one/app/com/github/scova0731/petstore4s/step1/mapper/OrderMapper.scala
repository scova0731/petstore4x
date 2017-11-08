package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.Order


trait OrderMapper {

  def getOrdersByUsername(username: String): List[Order]

  def getOrder(orderId: Int): Order

  def insertOrder(order: Order): Unit

  def insertOrderStatus(order: Order): Unit

}
