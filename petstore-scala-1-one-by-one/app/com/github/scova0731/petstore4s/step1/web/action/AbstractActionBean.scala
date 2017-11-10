package com.github.scova0731.petstore4s.step1.web.action

import play.api.Logger
import play.api.i18n.{Lang, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{InjectedController, Request, Result, Session}
import com.github.scova0731.petstore4s.step1.domain.{Account, Cart, Order}
import net.sourceforge.stripes.action.{ActionBean, ActionBeanContext, SimpleMessage}
import com.github.scova0731.petstore4s.step1.views.html

/**
  * The Class AbstractActionBean.
  *
  * @author Eduardo Macarron
  */
abstract class AbstractActionBean extends InjectedController with ActionBean {
  def messagesApi: MessagesApi

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  protected val ERROR = "/WEB-INF/jsp/common/Error.jsp"
  protected var context: ActionBeanContext = null


  // NOTE これはいったい？
  protected def setMessage(value: String): Unit = {
    context.getMessages.add(new SimpleMessage(value))
  }

  override def getContext: ActionBeanContext = context

  override def setContext(context: ActionBeanContext): Unit = {
    this.context = context
  }

  protected def extractAccount()(implicit req: Request[_]): Option[Account] = {
    req.session.get("accountBean")
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

  protected def extractOrNewCart()(implicit req: Request[_]): Cart = {
    req.session.get("cart")
      .map(jsonString =>
        Cart.reads.reads(Json.parse(jsonString)).fold(
          error => {
            Logger.error(s"Cart JSON parse error: ${error.toString()}")
            Cart()
          },
          valid => valid
        )
      )
      .getOrElse(Cart())
  }

  protected def extractOrder()(implicit req: Request[_]): Option[Order] = {
    req.session.get("order")
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

  protected val accountKeys =
    Seq("accountBean",
      "accountBean_authenticated",
      "accountBean_account_firstName",
      "accountBean_account_bannerName")

  protected def withAccount(account: Account): Seq[(String, String)] =
    Seq("accountBean"-> Account.writes.writes(account).toString,
      "accountBean_authenticated" -> "true",
      "accountBean_account_firstName" -> account.firstName,
      "accountBean_account_bannerName" -> account.bannerName)

  protected def withCart(cart: Cart): (String, String) =
    "cart" -> Cart.writes.writes(cart).toString()

  protected def withOrder(order: Order): (String, String) =
    "order" -> Order.writes.writes(order).toString()


  protected def renderError[A](message: String)(implicit req: Request[A]) =
    BadRequest(html.common.Error(message))
}
