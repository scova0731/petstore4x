package com.github.scova0731.petstore4s.step1.domain

import java.time.OffsetDateTime

/**
  * The Class Order.
  */
case class FlatOrder(
  orderId: Int = 0,  // Temporary ID
  username : String,
  orderDate : OffsetDateTime,
  shipAddress1 : String,
  shipAddress2 : String,
  shipCity : String,
  shipState : String,
  shipZip : String,
  shipCountry : String,
  billAddress1 : String,
  billAddress2 : String,
  billCity : String,
  billState : String,
  billZip : String,
  billCountry : String,
  totalPrice : BigDecimal,
  billToFirstName : String,
  billToLastName : String,
  shipToFirstName : String,
  shipToLastName : String,
  creditCard : String = "999 9999 9999 9999",
  expiryDate : String = "12/03",
  cardType : String = "Visa",
  courier : String = "UPS",
  locale : String = "CA",
  status : String = "P",
  lineItems : Seq[LineItem]
) {

  def toOrder: Order = Order(
    username = username,
    orderDate = orderDate,
    shipAddress = OrderAddress(
      toFirstName = shipToFirstName,
      toLastName = shipToLastName,
      address1 = shipAddress1,
      address2 = shipAddress2,
      city = shipCity,
      state = shipState,
      zip = shipZip,
      country = shipCountry
    ),
    billAddress = OrderAddress(
      toFirstName = billToFirstName,
      toLastName = billToLastName,
      address1 = billAddress1,
      address2 = billAddress2,
      city = billCity,
      state = billState,
      zip = billZip,
      country = billCountry
    ),
    totalPrice = totalPrice,
    lineItems = lineItems
  )
  

//  def addLineItem(cartItem: CartItem): Unit = {
//    val lineItem = new LineItem(lineItems.size + 1, cartItem)
//    addLineItem(lineItem)
//  }
//
//  def addLineItem(lineItem: LineItem): Unit = {
//    lineItems = lineItems :+ lineItem
//  }
}
