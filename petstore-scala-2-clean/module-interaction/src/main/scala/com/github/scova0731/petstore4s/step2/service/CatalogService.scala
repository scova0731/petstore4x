package com.github.scova0731.petstore4s.step2.service

import scala.collection.JavaConverters._

import javax.inject.Inject

import com.github.scova0731.petstore4s.step2.domain.{Category, Item, Product}
import com.github.scova0731.petstore4s.step2.repository.{CategoryRepository, ItemRepository, ProductRepository}

class CatalogService @Inject()(
  repo:CategoryRepository,
  itemRepo: ItemRepository,
  productRepo: ProductRepository
) {

  def getCategoryList: Seq[Category] =
    repo.getCategoryList

  def getCategory(categoryId: String): Category =
    repo.getCategory(categoryId)

  def getProduct(productId: String): Product =
    productRepo.getProduct(productId)

  def getProductListByCategory(categoryId: String): Seq[Product] =
    productRepo.getProductListByCategory(categoryId).asScala.toList

  /**
    * Search product list.
    *
    * @param keywords the keywords
    * @return the list
    */
  def searchProductList(keywords: String): Seq[Product] = {
    keywords.split("\\s+").flatMap { keyword =>
      Option(productRepo.searchProductList("%" + keyword.toLowerCase + "%").asScala.toList)
        .getOrElse(List.empty)
    }
  }

  def getItemListByProduct(productId: String): Seq[Item] =
    itemRepo.getItemListByProduct(productId).asScala

  def getItem(itemId: String): Item =
    itemRepo.getItem(itemId)

  def isItemInStock(itemId: String): Boolean =
    itemRepo.getInventoryQuantity(itemId) > 0
}
