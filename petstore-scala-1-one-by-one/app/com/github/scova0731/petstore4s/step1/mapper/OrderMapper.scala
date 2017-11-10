package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.FlatOrder


trait OrderMapper {

  def getOrdersByUsername(username: String): List[FlatOrder]

  def getOrder(orderId: Int): FlatOrder

  def insertOrder(order: FlatOrder): Unit

  def insertOrderStatus(order: FlatOrder): Unit

}
