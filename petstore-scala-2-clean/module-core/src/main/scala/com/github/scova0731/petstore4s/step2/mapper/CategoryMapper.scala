package com.github.scova0731.petstore4s.step2.mapper

import com.github.scova0731.petstore4s.step2.domain.Category


trait CategoryMapper {

  def getCategoryList: Seq[Category]

  def getCategory(categoryId: String): Category

}
