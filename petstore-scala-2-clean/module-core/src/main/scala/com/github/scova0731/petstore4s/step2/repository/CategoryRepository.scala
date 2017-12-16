package com.github.scova0731.petstore4s.step2.repository

import com.github.scova0731.petstore4s.step2.domain.Category


trait CategoryRepository {

  def getCategoryList: Seq[Category]

  def getCategory(categoryId: String): Category

}
