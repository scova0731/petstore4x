package com.github.scova0731.petstore4s.step2.web.controllers

import javax.inject.Inject

import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi
import com.github.scova0731.petstore4s.step2.domain.Account
import com.github.scova0731.petstore4s.step2.service.{AccountService, CatalogService}
import com.github.scova0731.petstore4s.step2.web.views.html

/**
  * The Class AccountController.
  *
  * @author Eduardo Macarron
  */
object AccountController {

  val LANGUAGE_LIST: Seq[String] = Seq("english", "japanese")
  val CATEGORY_LIST: Seq[String] = Seq("FISH", "DOGS", "REPTILES", "CATS", "BIRDS")

  case class LogonForm(username: String, password: String)

  val logonForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LogonForm.apply)(LogonForm.unapply)
  )

  // NOTE Originally, though only firstName and password was required in addition to username and password,
  //      all attributes except for password is required to simplify
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

class AccountController @Inject()(
  accountService: AccountService,
  catalogService: CatalogService,
  override val cacheApi: SyncCacheApi,
  override val messagesApi: MessagesApi
) extends AbstractController {

  import AccountController._


  def newAccountForm() = Action { implicit req =>
    Ok(html.account.NewAccountForm())
  }


  def newAccount() = Action { implicit req =>
    accountForm.bindFromRequest().fold(
      error => renderError(s"Error details: ${error.toString}"),
      form => {
        accountService.insertAccount(form)
        val account = accountService.getAccount(form.username)
        val myList = catalogService.getProductListByCategory(account.favouriteCategoryId)

        cache(withAccount(account))
        cache(withMyList(myList))

        Redirect(routes.CatalogController.main())
      }
    )

  }


  /**
    * Edits the account form.
    *
    * @return the resolution
    */
  def editAccountForm() = Action { implicit req =>
    val account = extractAccount().get

    Ok(html.account.EditAccountForm(account))
  }


  /**
    * Edits the account.
    *
    * @return the resolution
    */
  def editAccount() = Action { implicit req =>
    accountForm.bindFromRequest().fold(
      error => renderError(s"Error details: ${error.toString}"),
      form => {
        accountService.updateAccount(form)
        val account = accountService.getAccount(form.username)
        val myList = catalogService.getProductListByCategory(account.favouriteCategoryId)

        cache(withAccount(account))
        cache(withMyList(myList))

        Redirect(routes.CatalogController.main())
      }
    )
  }


  /**
    * Signon form.
    *
    * @return the resolution
    */
  def signonForm() = Action { implicit req =>

    Ok(html.account.SignonForm())
  }

  /**
    * Signon.
    *
    * @return the resolution
    */
  def signon() = Action { implicit req =>
    logonForm.bindFromRequest().fold(
      _ =>
        Ok(html.account.SignonForm())
          .flashing("message"->"Invalid username or password.  Signon failed."),

      form => {
        accountService.getAccount(form.username, form.password) match {
          case Some(account) =>
            val myList = catalogService.getProductListByCategory(account.favouriteCategoryId)

            cache(withAccount(account))
            cache(withMyList(myList))

            Redirect(routes.CatalogController.main())

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
  def signoff() = Action { implicit req =>

    removeAllCache()
    Redirect(routes.CatalogController.main())
  }

}