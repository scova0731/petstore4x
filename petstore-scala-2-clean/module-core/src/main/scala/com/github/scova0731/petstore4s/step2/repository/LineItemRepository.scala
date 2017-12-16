package com.github.scova0731.petstore4s.step2.repository

import com.github.scova0731.petstore4s.step2.domain.LineItem


trait LineItemRepository {

  def getLineItemsByOrderId(orderId: Int): java.util.List[LineItem]

  def insertLineItem(lineItem: LineItem): Unit

}
