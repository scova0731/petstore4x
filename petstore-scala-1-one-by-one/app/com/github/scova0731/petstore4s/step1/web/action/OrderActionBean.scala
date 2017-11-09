package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.i18n.MessagesApi
import com.github.scova0731.petstore4s.step1.domain.Order
import com.github.scova0731.petstore4s.step1.service.OrderService
import net.sourceforge.stripes.action.{ForwardResolution, Resolution}
import com.github.scova0731.petstore4s.step1.views.html

/**
  * The Class OrderActionBean.
  *
  * @author Eduardo Macarron
  */
object OrderActionBean {
  private val CONFIRM_ORDER: String = "/WEB-INF/jsp/order/ConfirmOrder.jsp"
  private val LIST_ORDERS: String = "/WEB-INF/jsp/order/ListOrders.jsp"
  private val NEW_ORDER: String = "/WEB-INF/jsp/order/NewOrderForm.jsp"
  private val SHIPPING: String = "/WEB-INF/jsp/order/ShippingForm.jsp"
  private val VIEW_ORDER: String = "/WEB-INF/jsp/order/ViewOrder.jsp"

  private val CARD_TYPE_LIST = Seq("Visa", "MasterCard", "American Express")
}

class OrderActionBean @Inject()(
  orderService: OrderService,
  override val messagesApi: MessagesApi
) extends AbstractActionBean {
//  @SpringBean 
//  private val orderService: OrderService = null
  private var order: Order = null //new Order
  private var shippingAddressRequired: Boolean = false
  private var confirmed: Boolean = false
  private var orderList: Seq[Order] = null

  def getOrderId: Int = order.orderId

  def setOrderId(orderId: Int): Unit = {
    //order.orderDate(orderId)
  }

  def getOrder: Order = order

  def setOrder(order: Order): Unit = {
    this.order = order
  }

  def isShippingAddressRequired: Boolean = shippingAddressRequired

  def setShippingAddressRequired(shippingAddressRequired: Boolean): Unit = {
    this.shippingAddressRequired = shippingAddressRequired
  }

  def isConfirmed: Boolean = confirmed

  def setConfirmed(confirmed: Boolean): Unit = {
    this.confirmed = confirmed
  }

  def getCreditCardTypes: Seq[String] = OrderActionBean.CARD_TYPE_LIST

  def getOrderList: Seq[Order] = orderList

  /**
    * List orders.
    *
    * @return the resolution
    */
  def listOrders: Resolution = {
    val session = context.getRequest.getSession
    val accountBean: AccountActionBean = session.getAttribute("/actions/Account.action").asInstanceOf[AccountActionBean]
    orderList = orderService.getOrdersByUsername(accountBean.getAccount.username)
    new ForwardResolution(OrderActionBean.LIST_ORDERS)
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
//    val session = context.getRequest.getSession
//    val accountBean: AccountActionBean = session.getAttribute("/actions/Account.action").asInstanceOf[AccountActionBean]
//    val cartBean: CartActionBean = session.getAttribute("/actions/Cart.action").asInstanceOf[CartActionBean]
//    clear()
    val cart = extractOrNewCart()

    if (!req.session.get("accountBean_authenticated").contains("true")) {
      // TODO error ではない。。
      renderError("You must sign on before attempting to check out.  Please sign on and try checking out again.")
      //      new ForwardResolution(classOf[AccountActionBean])
      // TODO Redirect to sign-in
    } else if (cart.nonEmpty) {
      Ok
    } else {
      renderError("An order could not be created because a cart could not be found.")
    }


//    if (accountBean == null || !accountBean.isAuthenticated) {
//      setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.")
//      new ForwardResolution(classOf[AccountActionBean])
//    }
//    else if (cartBean != null) {
//      //      order.initOrder(accountBean.getAccount, cartBean.getCart)
//      new ForwardResolution(OrderActionBean.NEW_ORDER)
//    }
//    else {
//      setMessage("An order could not be created because a cart could not be found.")
//      new ForwardResolution(ERROR)
//    }
  }

  /**
    * New order.
    *
    * @return the resolution
    */
  def newOrder: Resolution = {
    val session = context.getRequest.getSession
    if (shippingAddressRequired) {
      shippingAddressRequired = false
      new ForwardResolution(OrderActionBean.SHIPPING)
    }
    else if (!isConfirmed) new ForwardResolution(OrderActionBean.CONFIRM_ORDER)
    else if (getOrder != null) {
      orderService.insertOrder(order)
      val cartBean: CartActionBean = session.getAttribute("/actions/Cart.action").asInstanceOf[CartActionBean]
//      cartBean.clear()
      setMessage("Thank you, your order has been submitted.")
      new ForwardResolution(OrderActionBean.VIEW_ORDER)
    }
    else {
      setMessage("An error occurred processing your order (order was null).")
      new ForwardResolution(ERROR)
    }
  }

  /**
    * View order.
    *
    * @return the resolution
    */
  def viewOrder: Resolution = {
    val session = context.getRequest.getSession
    val accountBean: AccountActionBean = session.getAttribute("accountBean").asInstanceOf[AccountActionBean]
    order = orderService.getOrder(order.orderId)
    if (accountBean.getAccount.username == order.username) new ForwardResolution(OrderActionBean.VIEW_ORDER)
    else {
      order = null
      setMessage("You may only view your own orders.")
      new ForwardResolution(ERROR)
    }
  }

  /**
    * Clear.
    */
  def clear(): Unit = {
    order = null //new Order
    shippingAddressRequired = false
    confirmed = false
    orderList = null
  }
}