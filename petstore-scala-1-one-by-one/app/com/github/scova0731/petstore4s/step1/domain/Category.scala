package com.github.scova0731.petstore4s.step1.domain

/**
  * The Class Category.
  */
case class Category(
  var categoryId: String = null,
  name: String,
  description: String
) {

  // TODO make immutable (consider "trim")
  def setCategoryId(categoryId: String): Unit = {
    this.categoryId = categoryId.trim
  }
}