package com.github.scova0731.petstore4s.step1.domain

import java.time.OffsetDateTime

import play.api.libs.json.Json


/**
  * The Class Order. (Updated)
  */
case class Order(
  orderId: Int = 0,  // Temporary ID
  username : String,
  orderDate : OffsetDateTime,
  shipAddress : OrderAddress,
  billAddress : OrderAddress,
  totalPrice : BigDecimal,
  creditCard : String = "999 9999 9999 9999",
  expiryDate : String = "12/03",
  cardType : String = "Visa",
  courier : String = "UPS",
  locale : String = "CA",
  status : String = "P",
  lineItems : Seq[LineItem]
) {

  def toFlatOrder: FlatOrder = FlatOrder(
    username = username,
    orderDate = orderDate,
    shipAddress1 = shipAddress.address1,
    shipAddress2 = shipAddress.address2,
    shipCity = shipAddress.city,
    shipState = shipAddress.state,
    shipZip = shipAddress.zip,
    shipCountry = shipAddress.country,
    billAddress1 = billAddress.address1,
    billAddress2 = billAddress.address2,
    billCity = billAddress.city,
    billState = billAddress.state,
    billZip = billAddress.zip,
    billCountry = billAddress.country,
    totalPrice = totalPrice,
    billToFirstName = billAddress.toFirstName,
    billToLastName = billAddress.toLastName,
    shipToFirstName = shipAddress.toFirstName,
    shipToLastName = shipAddress.toLastName,
    lineItems = lineItems
  )
}

object Order {

  implicit val reads = Json.reads[Order]
  implicit val writes = Json.writes[Order]

  /**
    * Inits the order.
    *
    * @param account the account
    * @param cart    the cart
    */
  def initOrder(account: Account, cart: Cart): Order =
    Order(
      username = account.username,
      orderDate = OffsetDateTime.now(),
      shipAddress = OrderAddress(
        toFirstName = account.firstName,
        toLastName = account.lastName,
        address1 = account.address1,
        address2 = account.address2,
        city = account.city,
        state = account.state,
        zip = account.zip,
        country = account.country
      ),
      billAddress = OrderAddress(
        toFirstName = account.firstName,
        toLastName = account.lastName,
        address1 = account.address1,
        address2 = account.address2,
        city = account.city,
        state = account.state,
        zip = account.zip,
        country = account.country
      ),
      totalPrice = cart.subTotal,
      lineItems = cart.items
        .zipWithIndex
        .map{ case (cartItem, i) => new LineItem(i + 1, cartItem) }
    )
}