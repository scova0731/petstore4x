package com.github.scova0731.petstore4s.step2.domain

import java.util.Date

/**
  * The Class Order.
  */
case class FlatOrder(
  orderId: Int,
  username : String,
  orderDate : Date,
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
  creditCard : String, // = "999 9999 9999 9999",
  expiryDate : String, // = "12/03",
  cardType : String, // = "Visa",
  courier : String, // = "UPS",
  locale : String, // = "CA",
  status : String, // = "P",
  lineItems : Seq[LineItem]
) {

  // NOTE Constructor for MyBatis (for getOrder/getOrdersByUserName)
  def this(
    billAddress1 : String,
    billAddress2 : String,
    billCity : String,
    billCountry : String,
    billState : String,
    billToFirstName : String,
    billToLastName : String,
    billZip : String,
    shipAddress1 : String,
    shipAddress2 : String,
    shipCity : String,
    shipCountry : String,
    shipState : String,
    shipToFirstName : String,
    shipToLastName : String,
    shipZip : String,
    cardType : String,
    courier : String,
    creditCard : String,
    expiryDate : String,
    locale : String,
    orderDate : java.sql.Date,
    orderId: java.lang.Integer,
    totalPrice : java.math.BigDecimal,
    username : String,
    status : String
  ) = this(
    orderId, username, orderDate,
    shipAddress1, shipAddress2, shipCity, shipState, shipZip, shipCountry,
    billAddress1, billAddress2, billCity, billState, billZip, billCountry,
    totalPrice,
    billToFirstName, billToLastName,
    shipToFirstName, shipToLastName,
    creditCard, expiryDate, cardType, courier, locale, status, Seq.empty
  )


  // NOTE Java conversion for MyBatis
  def getTotalPrice(): java.math.BigDecimal =
    totalPrice.underlying()

  def toOrder: Order = Order(
    orderId = orderId,
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
}
