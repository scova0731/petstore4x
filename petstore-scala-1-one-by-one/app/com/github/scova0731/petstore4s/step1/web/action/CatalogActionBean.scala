package com.github.scova0731.petstore4s.step1.web.action

import javax.inject.Inject

import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{Lang, MessagesApi}
import com.github.scova0731.petstore4s.step1.domain.{Category, Item}
import com.github.scova0731.petstore4s.step1.service.CatalogService
import net.sourceforge.stripes.action.{ForwardResolution, SessionScope}
import com.github.scova0731.petstore4s.step1.views.html


/**
  * The Class CatalogActionBean.
  *
  * @author Eduardo Macarron
  */
object CatalogActionBean {
  private val MAIN: String = "/WEB-INF/jsp/catalog/Main.jsp"
  private val VIEW_CATEGORY: String = "/WEB-INF/jsp/catalog/Category.jsp"
  private val VIEW_PRODUCT: String = "/WEB-INF/jsp/catalog/Product.jsp"
  private val VIEW_ITEM: String = "/WEB-INF/jsp/catalog/Item.jsp"
  private val SEARCH_PRODUCTS: String = "/WEB-INF/jsp/catalog/SearchProducts.jsp"

  case class KeywordSearch(keyword: String)

  val keywordSearchForm = Form(
    mapping(
      "keyword" -> nonEmptyText
    )(KeywordSearch.apply)(KeywordSearch.unapply)
  )
}

//@SessionScope
class CatalogActionBean @Inject()(
  catalogService: CatalogService,
  messagesApi: MessagesApi
) extends AbstractActionBean {

  import CatalogActionBean._

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  private var keyword: String = null
  private var categoryId: String = null
  private var category: Category = null
  private var categoryList: Seq[Category] = null
  private var productId: String = null
  private var product: Product = null
  private var productList: Seq[Product] = null
  private var itemId: String = null
  private var item: Item = null
  private var itemList: Seq[Item] = null

  def getKeyword: String = keyword

  def setKeyword(keyword: String): Unit = {
    this.keyword = keyword
  }

  def getCategoryId: String = categoryId

  def setCategoryId(categoryId: String): Unit = {
    this.categoryId = categoryId
  }

  def getProductId: String = productId

  def setProductId(productId: String): Unit = {
    this.productId = productId
  }

  def getItemId: String = itemId

  def setItemId(itemId: String): Unit = {
    this.itemId = itemId
  }

  def getCategory: Category = category

  def setCategory(category: Category): Unit = {
    this.category = category
  }

  def getProduct: Product = product

  def setProduct(product: Product): Unit = {
    this.product = product
  }

  def getItem: Item = item

  def setItem(item: Item): Unit = {
    this.item = item
  }

  def getCategoryList: Seq[Category] = categoryList

  def setCategoryList(categoryList: Seq[Category]): Unit = {
    this.categoryList = categoryList
  }

  def getProductList: Seq[Product] = productList

  def setProductList(productList: Seq[Product]): Unit = {
    this.productList = productList
  }

  def getItemList: Seq[Item] = itemList

  def setItemList(itemList: Seq[Item]): Unit = {
    this.itemList = itemList
  }

//  @DefaultHandler
// NOTE indexからの入り口
  def viewMain: ForwardResolution = new ForwardResolution(CatalogActionBean.MAIN)

  /**
    * at DefaultHandler
    */
  def main = Action { req =>
    Ok(html.catalog.Main(req.session))
  }

  /**
    * View category.
    *
    * @return the forward resolution
    */
  def viewCategory: ForwardResolution = {
    if (categoryId != null) {
      productList = catalogService.getProductListByCategory(categoryId)
      category = catalogService.getCategory(categoryId)
    }
    new ForwardResolution(CatalogActionBean.VIEW_CATEGORY)
  }

  /**
    * View product.
    *
    * @return the forward resolution
    */
  def viewProduct: ForwardResolution = {
    if (productId != null) {
      itemList = catalogService.getItemListByProduct(productId)
      product = catalogService.getProduct(productId)
    }
    new ForwardResolution(CatalogActionBean.VIEW_PRODUCT)
  }

  /**
    * View item.
    *
    * @return the forward resolution
    */
  def viewItem: ForwardResolution = {
    item = catalogService.getItem(itemId)
    product = item.product
    new ForwardResolution(CatalogActionBean.VIEW_ITEM)
  }

  /**
    * Search products.
    *
    * @return the forward resolution
    */
//  def searchProducts: ForwardResolution = if (keyword == null || keyword.length < 1) {
//    setMessage("Please enter a keyword to search for, then press the search button.")
//    new ForwardResolution(ERROR)
//  }
//  else {
//    productList = catalogService.searchProductList(keyword.toLowerCase)
//    new ForwardResolution(CatalogActionBean.SEARCH_PRODUCTS)
//  }
  def searchProducts = Action { implicit req =>
    keywordSearchForm.bindFromRequest.fold(
      errors => {
        // TODO show an error page.
        BadRequest("Please enter a keyword to search for, then press the search button.")
      },
      data => {
        val productList = catalogService.searchProductList(data.keyword.toLowerCase)
        Ok(html.catalog.SearchProducts(req.session, productList))
      }
    )

  }

  /**
    * Clear.
    */
  def clear(): Unit = {
    keyword = null
    categoryId = null
    category = null
    categoryList = null
    productId = null
    product = null
    productList = null
    itemId = null
    item = null
    itemList = null
  }
}
