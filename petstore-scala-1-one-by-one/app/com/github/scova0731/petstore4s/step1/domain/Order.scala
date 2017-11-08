package com.github.scova0731.petstore4s.step1.domain

import java.time.OffsetDateTime


/**
  * The Class Order.
  */
case class Order(
  var orderId: Int,
  var username : String, 
  var orderDate : OffsetDateTime,
  var shipAddress1 : String, 
  var shipAddress2 : String, 
  var shipCity : String, 
  var shipState : String, 
  var shipZip : String, 
  var shipCountry : String, 
  var billAddress1 : String, 
  var billAddress2 : String, 
  var billCity : String, 
  var billState : String, 
  var billZip : String, 
  var billCountry : String, 
  var courier : String, 
  var totalPrice : BigDecimal,
  var billToFirstName : String, 
  var billToLastName : String, 
  var shipToFirstName : String, 
  var shipToLastName : String, 
  var creditCard : String, 
  var expiryDate : String, 
  var cardType : String, 
  var locale : String, 
  var status : String, 
  var lineItems : List[LineItem]
) {


  /**
    * Inits the order.
    *
    * @param account the account
    * @param cart    the cart
    */
  def initOrder(account: Account, cart: Cart): Unit = {
    username = account.username
    orderDate = OffsetDateTime.now()
    shipToFirstName = account.firstName
    shipToLastName = account.lastName
    shipAddress1 = account.address1
    shipAddress2 = account.address2
    shipCity = account.city
    shipState = account.state
    shipZip = account.zip
    shipCountry = account.country
    billToFirstName = account.firstName
    billToLastName = account.lastName
    billAddress1 = account.address1
    billAddress2 = account.address2
    billCity = account.city
    billState = account.state
    billZip = account.zip
    billCountry = account.country
    totalPrice = cart.getSubTotal
    creditCard = "999 9999 9999 9999"
    expiryDate = "12/03"
    cardType = "Visa"
    courier = "UPS"
    locale = "CA"
    status = "P"
    val i = cart.getAllCartItems
    while ( {
      i.hasNext
    }) {
      val cartItem = i.next
      addLineItem(cartItem)
    }
  }

  def addLineItem(cartItem: CartItem): Unit = {
    val lineItem = new LineItem(lineItems.size + 1, cartItem)
    addLineItem(lineItem)
  }

  def addLineItem(lineItem: LineItem): Unit = {
    lineItems = lineItems :+ lineItem
  }
}
