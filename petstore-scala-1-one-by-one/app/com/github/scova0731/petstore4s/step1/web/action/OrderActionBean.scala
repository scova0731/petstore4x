package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi
import com.github.scova0731.petstore4s.step1.domain.{FlatOrder, Order, OrderAddress}
import com.github.scova0731.petstore4s.step1.service.OrderService
import com.github.scova0731.petstore4s.step1.views.html

/**
  * The Class OrderActionBean.
  *
  * @author Eduardo Macarron
  */
object OrderActionBean {
//  private val CONFIRM_ORDER: String = "/WEB-INF/jsp/order/ConfirmOrder.jsp"
//  private val LIST_ORDERS: String = "/WEB-INF/jsp/order/ListOrders.jsp"
//  private val NEW_ORDER: String = "/WEB-INF/jsp/order/NewOrderForm.jsp"
//  private val SHIPPING: String = "/WEB-INF/jsp/order/ShippingForm.jsp"
//  private val VIEW_ORDER: String = "/WEB-INF/jsp/order/ViewOrder.jsp"

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
//  @SpringBean 
//  private val orderService: OrderService = null
//  private var order: FlatOrder = null //new Order
//  private var shippingAddressRequired: Boolean = false
//  private var confirmed: Boolean = false
//  private var orderList: Seq[FlatOrder] = null
//
//  def getOrderId: Int = order.orderId
//
//  def setOrderId(orderId: Int): Unit = {
//    //order.orderDate(orderId)
//  }
//
//  def getOrder: FlatOrder = order
//
//  def setOrder(order: FlatOrder): Unit = {
//    this.order = order
//  }
//
//  def isShippingAddressRequired: Boolean = shippingAddressRequired
//
//  def setShippingAddressRequired(shippingAddressRequired: Boolean): Unit = {
//    this.shippingAddressRequired = shippingAddressRequired
//  }
//
//  def isConfirmed: Boolean = confirmed
//
//  def setConfirmed(confirmed: Boolean): Unit = {
//    this.confirmed = confirmed
//  }
//
//  def getCreditCardTypes: Seq[String] = OrderActionBean.CARD_TYPE_LIST
//
//  def getOrderList: Seq[FlatOrder] = orderList

  /**
    * List orders.
    *
    * @return the resolution
    */
//  def listOrders0: Resolution = {
//    val session = context.getRequest.getSession
//    val accountBean: AccountActionBean = session.getAttribute("/actions/Account.action").asInstanceOf[AccountActionBean]
//    orderList = orderService.getOrdersByUsername(accountBean.getAccount.username)
//    new ForwardResolution(OrderActionBean.LIST_ORDERS)
//  }

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
//  def newOrderForm: Resolution = {
//    val session = context.getRequest.getSession
//    val accountBean: AccountActionBean = session.getAttribute("/actions/Account.action").asInstanceOf[AccountActionBean]
//    val cartBean: CartActionBean = session.getAttribute("/actions/Cart.action").asInstanceOf[CartActionBean]
//    clear()
//    if (accountBean == null || !accountBean.isAuthenticated) {
//      setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.")
//      new ForwardResolution(classOf[AccountActionBean])
//    }
//    else if (cartBean != null) {
////      order.initOrder(accountBean.getAccount, cartBean.getCart)
//      new ForwardResolution(OrderActionBean.NEW_ORDER)
//    }
//    else {
//      setMessage("An order could not be created because a cart could not be found.")
//      new ForwardResolution(ERROR)
//    }
//  }


  def newOrderForm() = Action { implicit req =>

    (extractAccount(), extractOrNewCart()) match {
      case (None, _) =>
        Redirect(routes.AccountActionBean.signonForm())
          .flashing("message" ->"You must sign on before attempting to check out.  Please sign on and try checking out again.")

      case (Some(account), cart) if cart.nonEmpty =>
        val order = Order.initOrder(account, cart)
        cache(withOrder(order))
        Ok(html.order.NewOrderForm(order, CARD_TYPE_LIST))
//          .addingToSession(withOrder(order))

      case _=>
        renderError("An order could not be created because a cart could not be found.")
    }
  }

  /**
    * New order.
    *
    * @return the resolution
    */
//  def newOrder0: Resolution = {
//    val session = context.getRequest.getSession
//    if (shippingAddressRequired) {
//      shippingAddressRequired = false
//      new ForwardResolution(OrderActionBean.SHIPPING)
//
//    } else if (!isConfirmed) {
//      new ForwardResolution(OrderActionBean.CONFIRM_ORDER)
//
//    } else if (getOrder != null) {
//      orderService.insertOrder(order)
//      val cartBean: CartActionBean = session.getAttribute("/actions/Cart.action").asInstanceOf[CartActionBean]
////      cartBean.clear()
//      setMessage("Thank you, your order has been submitted.")
//      new ForwardResolution(OrderActionBean.VIEW_ORDER)
//
//    } else {
//      setMessage("An error occurred processing your order (order was null).")
//      new ForwardResolution(ERROR)
//    }
//  }

  def newOrder(confirmed: Option[Boolean] = None) = Action { implicit req =>
    val order = extractOrder().get

    orderBillAddressForm.bindFromRequest.value match {
      case Some(billAddress) =>
        val updOrder = billAddress.mergeToOrder(order)
        cache(withOrder(updOrder))
        if (billAddress.shippingAddressRequired)
          Redirect(routes.OrderActionBean.shippingForm())
//            .addingToSession(withOrder(updOrder))
        else
          Redirect(routes.OrderActionBean.confirmOrder())
//            .addingToSession(withOrder(updOrder))

      case None =>
        orderShipAddressForm.bindFromRequest.value match {
          case Some(shipAddress) =>
            val updOrder = shipAddress.mergeToOrder(order)
            cache(withOrder(updOrder))
            Redirect(routes.OrderActionBean.confirmOrder())
//              .addingToSession(withOrder(updOrder))

          case None =>
            if (confirmationForm.bindFromRequest.value.exists(_.confirmed)) {
              orderService.insertOrder(order.toFlatOrder)
              Redirect(routes.OrderActionBean.viewOrder(order.orderId))
            }
            else
              renderError("Order was not processed successfully")
        }
    }
  
//    orderBillAddressForm.bindFromRequest.fold(
//      _ =>
//        renderError("An error occurred processing your order (order was null)."),
//      form =>
//        if (form.shippingAddressRequired)
//          Redirect(routes.OrderActionBean.shippingForm())
//
//        else if (true) // (!isConfirmed)
//          Redirect(routes.OrderActionBean.confirmOrder())
//          
//        else if (order.nonEmpty) {
//          orderService.insertOrder(order.get.toFlatOrder) // TODO eliminate "get"
//          Redirect(routes.OrderActionBean.viewOrder())
//
//        } else
//          renderError("An error occurred processing your order (order was null)."),
//    )

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
//  def viewOrder0: Resolution = {
//    val session = context.getRequest.getSession
//    val accountBean: AccountActionBean = session.getAttribute("accountBean").asInstanceOf[AccountActionBean]
//    order = orderService.getOrder(order.orderId)
//    if (accountBean.getAccount.username == order.username) new ForwardResolution(OrderActionBean.VIEW_ORDER)
//    else {
//      order = null
//      setMessage("You may only view your own orders.")
//      new ForwardResolution(ERROR)
//    }
//  }

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

//  /**
//    * Clear.
//    */
//  def clear(): Unit = {
//    order = null //new Order
//    shippingAddressRequired = false
//    confirmed = false
//    orderList = null
//  }
}