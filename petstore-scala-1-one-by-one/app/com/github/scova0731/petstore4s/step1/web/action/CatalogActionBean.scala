package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.MessagesApi

import com.github.scova0731.petstore4s.step1.service.CatalogService
import com.github.scova0731.petstore4s.step1.views.html
import com.github.scova0731.petstore4s.step1.web.action.routes


/**
  * The Class CatalogActionBean.
  *
  * @author Eduardo Macarron
  */
object CatalogActionBean {

  case class KeywordSearch(keyword: String)

  val keywordSearchForm = Form(
    mapping(
      "keyword" -> nonEmptyText
    )(KeywordSearch.apply)(KeywordSearch.unapply)
  )
}

class CatalogActionBean @Inject()(
  catalogService: CatalogService,
  override val messagesApi: MessagesApi
) extends AbstractActionBean {

  import CatalogActionBean._

  /**
    * at DefaultHandler
    */
  def main = Action { implicit req =>
    Ok(html.catalog.Main())
  }

  /**
    * View category.
    */
  def viewCategory(categoryId: String) = Action { implicit req =>
    val productList = catalogService.getProductListByCategory(categoryId)
    val category = catalogService.getCategory(categoryId)

    Ok(html.catalog.Category(productList, category))
  }

  /**
    * View product.
    */
  def viewProduct(productId: String) = Action { implicit req =>
    val itemList = catalogService.getItemListByProduct(productId)
    val product = catalogService.getProduct(productId)

    Ok(html.catalog.Product(itemList, product))
  }

  /**
    * View item.
    */
  def viewItem(itemId: String) = Action { implicit req =>
    val item = catalogService.getItem(itemId)

    Ok(html.catalog.Item(item))
  }

  /**
    * Search products.
    */
  def searchProducts = Action { implicit req =>
    keywordSearchForm.bindFromRequest.fold(
      _ => {
        renderError("Please enter a keyword to search for, then press the search button.")
      },
      data => {
        val productList = catalogService.searchProductList(data.keyword.toLowerCase)
        Ok(html.catalog.SearchProducts(productList))
      }
    )
  }
}
