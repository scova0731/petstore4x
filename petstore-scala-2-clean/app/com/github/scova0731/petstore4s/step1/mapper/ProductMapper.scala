package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain
import com.github.scova0731.petstore4s.step1.domain.Product


trait ProductMapper {

  def getProductListByCategory(categoryId: String): java.util.List[domain.Product]

  def getProduct(productId: String): domain.Product

  def searchProductList(keywords: String): java.util.List[Product]

}
