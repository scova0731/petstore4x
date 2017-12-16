package com.github.scova0731.petstore4s.step2.repository

import com.github.scova0731.petstore4s.step2.domain.Item


trait ItemRepository {

  def updateInventoryQuantity(param: java.util.Map[String, Any]): Unit

  def getInventoryQuantity(itemId: String): Int

  def getItemListByProduct(productId: String): java.util.List[Item]

  def getItem(itemId: String): Item

}
