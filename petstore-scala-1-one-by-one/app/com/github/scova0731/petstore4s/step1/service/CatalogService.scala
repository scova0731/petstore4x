package com.github.scova0731.petstore4s.step1.service

import scala.collection.JavaConverters._

import javax.inject.Inject

import com.github.scova0731.petstore4s.step1.domain
import com.github.scova0731.petstore4s.step1.domain.{Category, Item, Product}
import com.github.scova0731.petstore4s.step1.mapper.{CategoryMapper, ItemMapper, ProductMapper}

class CatalogService @Inject()(
  categoryMapper:CategoryMapper,
  itemMapper: ItemMapper,
  productMapper: ProductMapper
) {

  def getCategoryList: Seq[Category] =
    categoryMapper.getCategoryList

  def getCategory(categoryId: String): Category =
    categoryMapper.getCategory(categoryId)

  def getProduct(productId: String): domain.Product =
    productMapper.getProduct(productId)

  def getProductListByCategory(categoryId: String): Seq[domain.Product] =
    productMapper.getProductListByCategory(categoryId).asScala.toList

  /**
    * Search product list.
    *
    * @param keywords the keywords
    * @return the list
    */
  def searchProductList(keywords: String): List[Product] = {
    keywords.split("\\s+").flatMap { keyword =>
      Option(productMapper.searchProductList("%" + keyword.toLowerCase + "%").asScala.toList)
        .getOrElse(List.empty)
    }.toList
  }

  def getItemListByProduct(productId: String): List[Item] =
    itemMapper.getItemListByProduct(productId).asScala.toList

  def getItem(itemId: String): Item =
    itemMapper.getItem(itemId)

  def isItemInStock(itemId: String): Boolean =
    itemMapper.getInventoryQuantity(itemId) > 0
}
