package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi
import com.github.scova0731.petstore4s.step1.domain.Account
import com.github.scova0731.petstore4s.step1.service.{AccountService, CatalogService}
//import net.sourceforge.stripes.action.{ForwardResolution, RedirectResolution, Resolution, SessionScope}
import com.github.scova0731.petstore4s.step1.views.html

/**
  * The Class AccountActionBean.
  *
  * @author Eduardo Macarron
  */
//@SessionScope
//@SerialVersionUID(5499663666155758178L)
object AccountActionBean {
//  private val NEW_ACCOUNT: String = "/WEB-INF/jsp/account/NewAccountForm.jsp"
//  private val EDIT_ACCOUNT: String = "/WEB-INF/jsp/account/EditAccountForm.jsp"
//  private val SIGNON: String = "/WEB-INF/jsp/account/SignonForm.jsp"

  val LANGUAGE_LIST: Seq[String] = Seq("english", "japanese")
  val CATEGORY_LIST: Seq[String] = Seq("FISH", "DOGS", "REPTILES", "CATS", "BIRDS")

  case class LogonForm(username: String, password: String)

  val logonForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LogonForm.apply)(LogonForm.unapply)
  )

  val accountForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> optional(text),
      "email" -> nonEmptyText,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "status" -> nonEmptyText,
      "address1" -> nonEmptyText,
      "address2" -> nonEmptyText,
      "city" -> nonEmptyText,
      "state" -> nonEmptyText,
      "zip" -> nonEmptyText,
      "country" -> nonEmptyText,
      "phone" -> nonEmptyText,
      "languagePreference" -> nonEmptyText,
      "favouriteCategoryId" -> nonEmptyText,
      "listOption" -> boolean,
      "bannerOption" -> boolean
    )(Account.applyForForm)(Account.unapplyForForm)

  )
}

//@SessionScope
class AccountActionBean @Inject()(
  accountService: AccountService,
  catalogService: CatalogService,
  override val messagesApi: MessagesApi
) extends AbstractActionBean {

  import AccountActionBean._

//  private val accountService: AccountService = null
//  private val catalogService: CatalogService = null
//  private var account: Account = null //new Account
//  private var myList: Seq[Product] = null
//  private var authenticated: Boolean = false

//  def getAccount: Account = this.account

//  def getUsername: String = account.username

//  @Validate(required = true, on = Array(Array("signon", "newAccount", "editAccount")))
//  def setUsername(username: String): Unit = {
//    account.setUsername(username)
//  }

//  def getPassword: String = account.password

//  @Validate(required = true, on = Array(Array("signon", "newAccount", "editAccount")))
//  def setPassword(password: String): Unit = {
//    account.setPassword(password)
//  }

//  def getMyList: Seq[Product] = myList

//  def setMyList(myList: Seq[Product]): Unit = {
//    this.myList = myList
//  }

//  def getLanguages: Seq[String] = AccountActionBean.LANGUAGE_LIST

//  def getCategories: Seq[String] = AccountActionBean.CATEGORY_LIST

//  def newAccountForm: Resolution = new ForwardResolution(AccountActionBean.NEW_ACCOUNT)

  def newAccountForm() = Action { implicit req =>
    Ok(html.account.NewAccountForm())
  }

//  /**
//    * New account.
//    *
//    * @return the resolution
//    */
//  def newAccount0: Resolution = {
//    accountService.insertAccount(account)
//    account = accountService.getAccount(account.username)
//    myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
//    authenticated = true
//    new RedirectResolution(classOf[CatalogActionBean])
//  }

  def newAccount() = Action { implicit req =>
    accountForm.bindFromRequest().fold(
      error => renderError(s"Error details: ${error.toString}"),
      form => {
        accountService.insertAccount(form)
        val account = accountService.getAccount(form.username)
        val myList = catalogService.getProductListByCategory(account.favouriteCategoryId) //TODO これはどこでつかう？
        Redirect(routes.CatalogActionBean.main())
          .addingToSession(withAccount(account):_*) // NOTE アカウントを更新
      }
    )

  }

  /**
    * Edits the account form.
    *
    * @return the resolution
    */
//  def editAccountForm: Resolution = new ForwardResolution(AccountActionBean.EDIT_ACCOUNT)

  def editAccountForm() = Action { implicit req =>
    val account = extractAccount().get

    Ok(html.account.EditAccountForm(account))
  }


  /**
    * Edits the account.
    *
    * @return the resolution
    */
//  def editAccount0: Resolution = {
//    accountService.updateAccount(account)
//    account = accountService.getAccount(account.username)
//    myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
//    new RedirectResolution(classOf[CatalogActionBean])
//  }

  def editAccount() = Action { implicit req =>
    accountForm.bindFromRequest().fold(
      error => renderError(s"Error details: ${error.toString}"),
      form => {
        accountService.updateAccount(form)
        val account = accountService.getAccount(form.username)
        val myList = catalogService.getProductListByCategory(account.favouriteCategoryId) //TODO これはどこでつかう？
        Redirect(routes.CatalogActionBean.main())
          .addingToSession(withAccount(account):_*) // NOTE アカウントを更新
      }
    )
  }

  /**
    * Signon form.
    *
    * @return the resolution
    */
  //  @DefaultHandler
//  def signonForm: Resolution = new ForwardResolution(AccountActionBean.SIGNON)

  def signonForm() = Action { implicit req =>

    Ok(html.account.SignonForm())
  }

  /**
    * Signon.
    *
    * @return the resolution
    */
//  def signon: Resolution = {
//    account = accountService.getAccount(getUsername, getPassword)
//    if (account == null) {
//      val value: String = "Invalid username or password.  Signon failed."
//      setMessage(value)
//      clear()
//      new ForwardResolution(AccountActionBean.SIGNON)
//    }
//    else {
//      // account.setPassword(null)
//      myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
//      authenticated = true
//      val s = context.getRequest.getSession
//      // this bean is already registered as /actions/Account.action
//      s.setAttribute("accountBean", this)
//      new RedirectResolution(classOf[CatalogActionBean])
//    }
//  }

  def signon() = Action { implicit req =>
    logonForm.bindFromRequest().fold(
      _ =>
        Ok(html.account.SignonForm())
          .flashing("message"->"Invalid username or password.  Signon failed."),

      form => {
        accountService.getAccount(form.username, form.password) match {
          case Some(account) =>
            val myList = catalogService.getProductListByCategory(account.favouriteCategoryId) // TODO これはなに？
            Redirect(routes.CatalogActionBean.main())
              .addingToSession(withAccount(account):_*)

          case None =>
            Ok(html.account.SignonForm())
              .flashing("message"->"Invalid username or password.  Signon failed.")
        }
      }
    )
  }

  /**
    * Signoff.
    *
    * @return the resolution
    */
//  def signoff: Resolution = {
//    context.getRequest.getSession.invalidate()
//    clear()
//    new RedirectResolution(classOf[CatalogActionBean])
//  }

  def signoff() = Action { implicit req =>

    Redirect(routes.CatalogActionBean.main())
      .removingFromSession(accountKeys:_*)
  }

//  /**
//    * Checks if is authenticated.
//    *
//    * @return true, if is authenticated
//    */
//  def isAuthenticated: Boolean = authenticated && account != null && account.username != null

//  /**
//    * Clear.
//    */
//  def clear(): Unit = {
//    account = null //new Account
//    myList = null
//    authenticated = false
//  }
}