package com.github.scova0731.petstore4s.step1.web.action

import java.util.Iterator

import com.github.scova0731.petstore4s.step1.domain.{Cart, CartItem, Item}
import com.github.scova0731.petstore4s.step1.service.CatalogService
import net.sourceforge.stripes.action.{ForwardResolution, Resolution, SessionScope}

/**
  * The Class CartActionBean.
  *
  * @author Eduardo Macarron
  */
@SessionScope
object CartActionBean {
  private val VIEW_CART: String = "/WEB-INF/jsp/cart/Cart.jsp"
  private val CHECK_OUT: String = "/WEB-INF/jsp/cart/Checkout.jsp"
}

@SessionScope
class CartActionBean extends AbstractActionBean {
//  @SpringBean
  private val catalogService: CatalogService = null
  private var cart: Cart = null //new Cart
  private var workingItemId: String = null

  def getCart: Cart = cart

  def setCart(cart: Cart): Unit = {
    this.cart = cart
  }

  def setWorkingItemId(workingItemId: String): Unit = {
    this.workingItemId = workingItemId
  }

  /**
    * Adds the item to cart.
    *
    * @return the resolution
    */
  def addItemToCart: Resolution = {
    if (cart.containsItemId(workingItemId)) cart.incrementQuantityByItemId(workingItemId)
    else { // isInStock is a "real-time" property that must be updated
      // every time an item is added to the cart, even if other
      // item details are cached.
      val isInStock: Boolean = catalogService.isItemInStock(workingItemId)
      val item: Item = catalogService.getItem(workingItemId)
      cart.addItem(item, isInStock)
    }
    new ForwardResolution(CartActionBean.VIEW_CART)
  }

  /**
    * Removes the item from cart.
    *
    * @return the resolution
    */
  def removeItemFromCart: Resolution = {
    val item: Item = cart.removeItemById(workingItemId)
    if (item == null) {
      setMessage("Attempted to remove null CartItem from Cart.")
      new ForwardResolution(ERROR)
    }
    else new ForwardResolution(CartActionBean.VIEW_CART)
  }

  /**
    * Update cart quantities.
    *
    * @return the resolution
    */
  def updateCartQuantities: Resolution = {
    val request = context.getRequest
    val cartItems: Iterator[CartItem] = getCart.getAllCartItems
    while ( {
      cartItems.hasNext
    }) {
      val cartItem: CartItem = cartItems.next
      val itemId: String = cartItem.item.itemId
      try {
        val quantity: Int = request.getParameter(itemId).toInt
        getCart.setQuantityByItemId(itemId, quantity)
        if (quantity < 1) cartItems.remove()
      } catch {
        case e: Exception =>

        //ignore parse exceptions on purpose
      }
    }
    new ForwardResolution(CartActionBean.VIEW_CART)
  }

  def viewCart: ForwardResolution = new ForwardResolution(CartActionBean.VIEW_CART)

  def checkOut: ForwardResolution = new ForwardResolution(CartActionBean.CHECK_OUT)

  def clear(): Unit = {
    cart = null //new Cart
    workingItemId = null
  }
}