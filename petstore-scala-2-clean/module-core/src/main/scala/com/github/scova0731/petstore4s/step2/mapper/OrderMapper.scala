package com.github.scova0731.petstore4s.step2.mapper

import com.github.scova0731.petstore4s.step2.domain.FlatOrder


trait OrderMapper {

  def getOrdersByUsername(username: String): java.util.List[FlatOrder]

  def getOrder(orderId: Int): FlatOrder

  def insertOrder(order: FlatOrder): Unit

  def insertOrderStatus(order: FlatOrder): Unit

}
