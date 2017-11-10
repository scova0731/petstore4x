package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi

import com.github.scova0731.petstore4s.step1.service.CatalogService
import com.github.scova0731.petstore4s.step1.views.html

/**
  * The Class CartActionBean.
  *
  * @author Eduardo Macarron
  */
object CartActionBean {

  case class CartQuantityUpdate(
    keys: List[String],
    values: List[Int]
  ) {

    def list: List[(String, Int)] = keys.zip(values)
  }

  val quantityForm = Form(
    mapping(
      "keys" -> list(text),
      "values" -> list(number)
    )(CartQuantityUpdate.apply)(CartQuantityUpdate.unapply)
  )

}

class CartActionBean @Inject()(
  catalogService: CatalogService,
  override val messagesApi: MessagesApi
) extends AbstractActionBean {

  import CartActionBean._

  /**
    * Adds the item to cart.
    *
    * @return the resolution
    */
  def addItemToCart(itemId: String) = Action { implicit req =>
    val cart = extractOrNewCart()

    val newCart =
      if (cart.containsItemId(itemId))
        cart.incrementQuantityByItemId(itemId)
      else {
        val isInStock = catalogService.isItemInStock(itemId)
        val item = catalogService.getItem(itemId)
        cart.addItem(item, isInStock)
      }

    Ok(html.cart.Cart(newCart)).addingToSession(withCart(newCart))
  }

  /**
    * Removes the item from cart.
    *
    * @return the resolution
    */
  def removeItemFromCart(itemId: String) = Action { implicit req =>
    val cart = extractOrNewCart()

    cart.removeItemById(itemId) match {
      case Some(newCart) =>
          Ok(html.cart.Cart(newCart)).addingToSession(withCart(newCart))
      case None =>
        renderError("Attempted to remove null CartItem from Cart.")
    }
  }

  /**
    * Update cart quantities.
    *
    * @return the resolution
    */
  def updateCartQuantities() = Action { implicit req =>
    val cart = extractOrNewCart()

    quantityForm.bindFromRequest.fold(
      _ => {
        renderError("Hmm, form cannot be parsed.")
      },
      data => {
        req.body.asFormUrlEncoded.foreach(println)
        println(data)
        val newCart = data.list.foldLeft(cart) { case (cart, (itemId, quantity)) =>
          if (quantity >=1)
            cart.setQuantityByItemId(itemId, quantity)
          else
            cart.removeItemById(itemId).getOrElse(cart)
        }
        Ok(html.cart.Cart(newCart)).addingToSession(withCart(newCart))
      }
    )
  }

  def viewCart() = Action { implicit req =>
    Ok(html.cart.Cart(extractOrNewCart()))
  }

//  def checkOut: ForwardResolution = new ForwardResolution(CartActionBean.CHECK_OUT)

}