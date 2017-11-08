package com.github.scova0731.petstore4s.step1.domain

/**
  * The Class Item.
  */
case class Item(
  itemId: String,
  productId: String,
  listPrice: BigDecimal,
  unitCost: BigDecimal,
  supplierId: Int,
  status: String,
  attribute1: String,
  attribute2: String,
  attribute3: String,
  attribute4: String,
  attribute5: String,
  product: Product,
  quantity: Int
)