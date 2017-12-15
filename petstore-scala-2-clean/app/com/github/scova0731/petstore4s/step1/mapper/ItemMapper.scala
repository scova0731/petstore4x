package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.Item


trait ItemMapper {

  def updateInventoryQuantity(param: java.util.Map[String, Any]): Unit

  def getInventoryQuantity(itemId: String): Int

  def getItemListByProduct(productId: String): java.util.List[Item]

  def getItem(itemId: String): Item

}
