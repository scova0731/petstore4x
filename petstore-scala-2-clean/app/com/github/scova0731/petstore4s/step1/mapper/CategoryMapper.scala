package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.Category


trait CategoryMapper {

  def getCategoryList: Seq[Category]

  def getCategory(categoryId: String): Category

}
