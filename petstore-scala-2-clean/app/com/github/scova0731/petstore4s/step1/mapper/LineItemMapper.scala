package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.LineItem


trait LineItemMapper {

  def getLineItemsByOrderId(orderId: Int): java.util.List[LineItem]

  def insertLineItem(lineItem: LineItem): Unit

}
