package com.github.scova0731.petstore4s.step1.web.action

import play.api.Logger
import play.api.i18n.{Lang, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{InjectedController, Request}
import com.github.scova0731.petstore4s.step1.domain.Cart
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

  protected def withCart(cart: Cart): (String, String) =
    "cart" -> Cart.writes.writes(cart).toString()


  protected def renderError[A](message: String)(implicit req: Request[A]) =
    BadRequest(html.common.Error(req.session, message))
}
