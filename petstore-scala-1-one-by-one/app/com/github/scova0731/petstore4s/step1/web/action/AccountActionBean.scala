package com.github.scova0731.petstore4s.step1.web.action

import com.github.scova0731.petstore4s.step1.domain.Account
import com.github.scova0731.petstore4s.step1.service.{AccountService, CatalogService}
import net.sourceforge.stripes.action.{ForwardResolution, RedirectResolution, Resolution, SessionScope}

/**
  * The Class AccountActionBean.
  *
  * @author Eduardo Macarron
  */
@SessionScope
@SerialVersionUID(5499663666155758178L)
object AccountActionBean {
  private val NEW_ACCOUNT: String = "/WEB-INF/jsp/account/NewAccountForm.jsp"
  private val EDIT_ACCOUNT: String = "/WEB-INF/jsp/account/EditAccountForm.jsp"
  private val SIGNON: String = "/WEB-INF/jsp/account/SignonForm.jsp"
  private val LANGUAGE_LIST: Seq[String] = Seq("english", "japanese")
  private val CATEGORY_LIST: Seq[String] = Seq("FISH", "DOGS", "REPTILES", "CATS", "BIRDS")

}

@SessionScope
class AccountActionBean extends AbstractActionBean {
  private val accountService: AccountService = null
  private val catalogService: CatalogService = null
  private var account: Account = null //new Account
  private var myList: Seq[Product] = null
  private var authenticated: Boolean = false

  def getAccount: Account = this.account

  def getUsername: String = account.username

//  @Validate(required = true, on = Array(Array("signon", "newAccount", "editAccount")))
  def setUsername(username: String): Unit = {
//    account.setUsername(username)
  }

  def getPassword: String = account.password

//  @Validate(required = true, on = Array(Array("signon", "newAccount", "editAccount")))
  def setPassword(password: String): Unit = {
//    account.setPassword(password)
  }

  def getMyList: Seq[Product] = myList

  def setMyList(myList: Seq[Product]): Unit = {
    this.myList = myList
  }

  def getLanguages: Seq[String] = AccountActionBean.LANGUAGE_LIST

  def getCategories: Seq[String] = AccountActionBean.CATEGORY_LIST

  def newAccountForm: Resolution = new ForwardResolution(AccountActionBean.NEW_ACCOUNT)

  /**
    * New account.
    *
    * @return the resolution
    */
  def newAccount: Resolution = {
    accountService.insertAccount(account)
    account = accountService.getAccount(account.username)
    myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
    authenticated = true
    new RedirectResolution(classOf[CatalogActionBean])
  }

  /**
    * Edits the account form.
    *
    * @return the resolution
    */
  def editAccountForm: Resolution = new ForwardResolution(AccountActionBean.EDIT_ACCOUNT)

  /**
    * Edits the account.
    *
    * @return the resolution
    */
  def editAccount: Resolution = {
    accountService.updateAccount(account)
    account = accountService.getAccount(account.username)
    myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
    new RedirectResolution(classOf[CatalogActionBean])
  }

  /**
    * Signon form.
    *
    * @return the resolution
    */
  //  @DefaultHandler
  def signonForm: Resolution = new ForwardResolution(AccountActionBean.SIGNON)

  /**
    * Signon.
    *
    * @return the resolution
    */
  def signon: Resolution = {
    account = accountService.getAccount(getUsername, getPassword)
    if (account == null) {
      val value: String = "Invalid username or password.  Signon failed."
      setMessage(value)
      clear()
      new ForwardResolution(AccountActionBean.SIGNON)
    }
    else {
      // account.setPassword(null)
      myList = catalogService.getProductListByCategory(account.favouriteCategoryId)
      authenticated = true
      val s = context.getRequest.getSession
      // this bean is already registered as /actions/Account.action
      s.setAttribute("accountBean", this)
      new RedirectResolution(classOf[CatalogActionBean])
    }
  }

  /**
    * Signoff.
    *
    * @return the resolution
    */
  def signoff: Resolution = {
    context.getRequest.getSession.invalidate()
    clear()
    new RedirectResolution(classOf[CatalogActionBean])
  }

  /**
    * Checks if is authenticated.
    *
    * @return true, if is authenticated
    */
  def isAuthenticated: Boolean = authenticated && account != null && account.username != null

  /**
    * Clear.
    */
  def clear(): Unit = {
    account = null //new Account
    myList = null
    authenticated = false
  }
}