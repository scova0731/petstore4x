package com.github.scova0731.petstore4s.step1.web.action

import play.api.mvc.InjectedController
import net.sourceforge.stripes.action.{ActionBean, ActionBeanContext, SimpleMessage}

/**
  * The Class AbstractActionBean.
  *
  * @author Eduardo Macarron
  */
abstract class AbstractActionBean extends InjectedController with ActionBean {
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
}
