package com.github.scova0731.petstore4s.step2.service

import scala.collection.JavaConverters._

import javax.inject.Inject

import com.github.scova0731.petstore4s.step2.domain.{Category, Item, Product}
import com.github.scova0731.petstore4s.step2.mapper.{CategoryMapper, ItemMapper, ProductMapper}

class CatalogService @Inject()(
  categoryMapper:CategoryMapper,
  itemMapper: ItemMapper,
  productMapper: ProductMapper
) {

  def getCategoryList: Seq[Category] =
    categoryMapper.getCategoryList

  def getCategory(categoryId: String): Category =
    categoryMapper.getCategory(categoryId)

  def getProduct(productId: String): Product =
    productMapper.getProduct(productId)

  def getProductListByCategory(categoryId: String): Seq[Product] =
    productMapper.getProductListByCategory(categoryId).asScala.toList

  /**
    * Search product list.
    *
    * @param keywords the keywords
    * @return the list
    */
  def searchProductList(keywords: String): Seq[Product] = {
    keywords.split("\\s+").flatMap { keyword =>
      Option(productMapper.searchProductList("%" + keyword.toLowerCase + "%").asScala.toList)
        .getOrElse(List.empty)
    }
  }

  def getItemListByProduct(productId: String): Seq[Item] =
    itemMapper.getItemListByProduct(productId).asScala

  def getItem(itemId: String): Item =
    itemMapper.getItem(itemId)

  def isItemInStock(itemId: String): Boolean =
    itemMapper.getInventoryQuantity(itemId) > 0
}
