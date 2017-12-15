package com.github.scova0731.petstore4s.step2.web.action

import java.util.UUID

import play.api.cache.SyncCacheApi
import play.api.Logger
import play.api.i18n.{Lang, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{InjectedController, Request}
import com.github.scova0731.petstore4s.step2.domain.{Account, Cart, Order, Product}
import com.github.scova0731.petstore4s.step2.views.html

case class MyList(products: Seq[Product])

object MyList {

  implicit val reads = Json.reads[MyList]
  implicit val writes = Json.writes[MyList]
}



/**
  * The Class AbstractActionBean.
  *
  * @author Eduardo Macarron
  */
abstract class AbstractActionBean extends InjectedController with StateHandler {
  def cacheApi: SyncCacheApi
  def messagesApi: MessagesApi

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  protected def renderError[A](message: String)(implicit req: Request[A]) =
    BadRequest(html.common.Error(message))

}


trait StateHandler {
  def cacheApi: SyncCacheApi

  // NOTE implicitly pass account to views
  implicit def implicitlyExtractAccount: Option[Account] = {
    extractAccount()
  }

  private def getOrNewSessionId(): String = {
    cacheApi.get[String]("sessionId").getOrElse {
      val sessionId = generateUUID()
      cacheDirectly("sessionId" -> sessionId)
      sessionId
    }
  }

  protected def extractAccount(): Option[Account] = {
    cacheApi.get[String](s"${getOrNewSessionId()}/account")
      .flatMap(jsonString =>
        Account.reads.reads(Json.parse(jsonString)).fold(
          error => {
            Logger.error(s"Account JSON parse error: ${error.toString()}")
            None
          },
          valid =>
            Some(valid)
        )
      )
  }

  protected def extractOrNewCart(): Cart = {
    cacheApi.get[String](s"${getOrNewSessionId()}/cart")
      .map(jsonString =>
        Cart.reads.reads(Json.parse(jsonString)).fold(
          error => {
            Logger.error(s"Cart JSON parse error: ${error.toString()}")
            Cart()
          },
          valid => valid
        )
      )
  }.getOrElse(Cart())

  protected def extractOrder(): Option[Order] = {
    cacheApi.get[String](s"${getOrNewSessionId()}/order")
      .flatMap(jsonString =>
        Order.reads.reads(Json.parse(jsonString)).fold(
          error => {
            Logger.error(s"Order JSON parse error: ${error.toString()}")
            None
          },
          valid =>
            Some(valid)
        )
      )
  }

  protected def extractMyList(): Option[Seq[Product]] = {
    cacheApi.get[String](s"${getOrNewSessionId()}/myList")
      .flatMap { jsonString =>
        MyList.reads.reads(Json.parse(jsonString)).fold(
          error => {
            Logger.error(s"MyList JSON parse error: ${error.toString()}")
            None
          },
          valid =>
            Some(valid.products)
        )
      }
  }

  protected def withAccount(account: Account): (String, String) =
    "account"-> Account.writes.writes(account).toString

  protected def withCart(cart: Cart): (String, String) =
    "cart" -> Cart.writes.writes(cart).toString()

  protected def withOrder(order: Order): (String, String) =
    "order" -> Order.writes.writes(order).toString()

  protected def withMyList(products: Seq[Product]): (String, String) =
    "myList" -> MyList.writes.writes(MyList(products)).toString()

  // NOTE TTL should be set in production
  protected def cache(keyValues: (String, String)*): Unit = {
    val sessionId = getOrNewSessionId()
    keyValues.foreach { case (k, v) =>
      cacheDirectly(s"$sessionId/$k" -> v)
    }
  }

  // NOTE TTL should be set in production
  protected def cacheDirectly(keyValue: (String, String)): Unit = {
    Logger.debug(s"Caching: ${keyValue._1} -> ${keyValue._2}")
    cacheApi.set(keyValue._1, keyValue._2)
  }

  protected def removeAllCache(): Unit = {
    val sessionId = getOrNewSessionId()
    keys.foreach { key =>
      cacheApi.remove(s"$sessionId/$key")
    }
    cacheApi.remove("sessionId")
  }

  protected def removeCache(key: String): Unit = {
    val sessionId = getOrNewSessionId()
    cacheApi.remove(s"$sessionId/$key")
  }

  private val keys =
    Seq("account", "cart", "order", "myList")

  private def generateUUID() =
    UUID.randomUUID().toString


}