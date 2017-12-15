package com.github.scova0731.petstore4s.step2.web.action

import javax.inject.Inject

import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi
import com.github.scova0731.petstore4s.step2.domain.{Order, OrderAddress}
import com.github.scova0731.petstore4s.step2.service.OrderService
import com.github.scova0731.petstore4s.step2.views.html

/**
  * The Class OrderActionBean.
  *
  * @author Eduardo Macarron
  */
object OrderActionBean {
  private val CARD_TYPE_LIST = Seq("Visa", "MasterCard", "American Express")
  
  case class OrderBillAddressForm(
    cardType : String,
    creditCard : String,
    expiryDate : String,
    billToFirstName : String,
    billToLastName : String,
    billAddress1 : String,
    billAddress2 : String,
    billCity : String,
    billState : String,
    billZip : String,
    billCountry : String,
    shippingAddressRequired: Boolean
  ) {
    
    def mergeToOrder(order: Order): Order =
      order.copy(
        cardType = cardType,
        creditCard = creditCard,
        expiryDate = expiryDate,
        billAddress = OrderAddress(
          toFirstName = billToFirstName,
          toLastName = billToLastName,
          address1 = billAddress1,
          address2 = billAddress2,
          city = billCity,
          state = billState,
          zip = billZip,
          country = billCountry
        )
      )
  }

  case class OrderShipAddressForm(
    shipToFirstName : String,
    shipToLastName : String,
    shipAddress1 : String,
    shipAddress2 : String,
    shipCity : String,
    shipState : String,
    shipZip : String,
    shipCountry : String
  ) {

    def mergeToOrder(order: Order): Order =
      order.copy(
        shipAddress = OrderAddress(
          toFirstName = shipToFirstName,
          toLastName = shipToLastName,
          address1 = shipAddress1,
          address2 = shipAddress2,
          city = shipCity,
          state = shipState,
          zip = shipZip,
          country = shipCountry
        )
      )
  }

  case class ConfirmationForm(confirmed: Boolean)

  val orderBillAddressForm = Form(
    mapping(
      "cardType" -> text,
      "creditCard" -> text,
      "expiryDate" -> text,
      "billToFirstName" -> text,
      "billToLastName" -> text,
      "billAddress1" -> text,
      "billAddress2" -> text,
      "billCity" -> text,
      "billState" -> text,
      "billZip" -> text,
      "billCountry" -> text,
      "shippingAddressRequired" -> boolean
    )(OrderBillAddressForm.apply)(OrderBillAddressForm.unapply)
  )

  val orderShipAddressForm = Form(
    mapping(
      "shipToFirstName" -> text,
      "shipToLastName" -> text,
      "shipAddress1" -> text,
      "shipAddress2" -> text,
      "shipCity" -> text,
      "shipState" -> text,
      "shipZip" -> text,
      "shipCountry" -> text
    )(OrderShipAddressForm.apply)(OrderShipAddressForm.unapply)
  )

  val confirmationForm = Form(
    mapping(
      "confirmed" -> boolean
    )(ConfirmationForm.apply)(ConfirmationForm.unapply)
  )
}

class OrderActionBean @Inject()(
  orderService: OrderService,
  override val cacheApi: SyncCacheApi,
  override val messagesApi: MessagesApi
) extends AbstractActionBean {

  import OrderActionBean._

  /**
    * List orders.
    *
    * @return the resolution
    */
  def listOrders() = Action { implicit req =>
    val account = extractAccount().get
    val orderList = orderService.getOrdersByUsername(account.username)

    Ok(html.order.ListOrders(orderList))
  }

  /**
    * New order form.
    *
    * @return the resolution
    */
  def newOrderForm() = Action { implicit req =>

    (extractAccount(), extractOrNewCart()) match {
      case (None, _) =>
        Redirect(routes.AccountActionBean.signonForm())
          .flashing("message" ->"You must sign on before attempting to check out.  Please sign on and try checking out again.")

      case (Some(account), cart) if cart.nonEmpty =>
        val order = Order.initOrder(account, cart)
        cache(withOrder(order))
        Ok(html.order.NewOrderForm(order, CARD_TYPE_LIST))

      case _=>
        renderError("An order could not be created because a cart could not be found.")
    }
  }

  /**
    * New order.
    *
    * @return the resolution
    */
  def newOrder(confirmed: Option[Boolean] = None) = Action { implicit req =>
    val order = extractOrder().get

    orderBillAddressForm.bindFromRequest.value match {
      case Some(billAddress) =>
        val updOrder = billAddress.mergeToOrder(order)
        cache(withOrder(updOrder))
        if (billAddress.shippingAddressRequired)
          Redirect(routes.OrderActionBean.shippingForm())
        else
          Redirect(routes.OrderActionBean.confirmOrder())

      case None =>
        orderShipAddressForm.bindFromRequest.value match {
          case Some(shipAddress) =>
            val updOrder = shipAddress.mergeToOrder(order)
            cache(withOrder(updOrder))
            Redirect(routes.OrderActionBean.confirmOrder())

          case None =>
            if (confirmationForm.bindFromRequest.value.exists(_.confirmed)) {
              val newOrderId = orderService.insertOrder(order.toFlatOrder)
              removeCache("order")
              removeCache("cart")
              Redirect(routes.OrderActionBean.viewOrder(newOrderId))
            }
            else
              renderError("Order was not processed successfully")
        }
    }
  }

  def shippingForm = Action { implicit req =>
    val order = extractOrder().get

    Ok(html.order.ShippingForm(order))
  }

  def confirmOrder = Action { implicit req =>
    val order = extractOrder().get

    Ok(html.order.ConfirmOrder(order))
  }

  /**
    * View order.
    *
    * @return the resolution
    */
  def viewOrder(orderId: Int) = Action { implicit req =>
    val order = orderService.getOrder(orderId)
    val account = extractAccount().get

    if (account.username == order.username) {
      Ok(html.order.ViewOrder(order))
        .removingFromSession("order", "cart")
        .flashing("message" -> "Thank you, your order has been submitted.")

    } else
      renderError("You may only view your own orders.")
  }
}