package com.github.scova0731.petstore4s.step2.domain

import play.api.libs.json.Json

/**
  * The Class Item.
  */
case class Item(
  itemId: String,
  listPrice: BigDecimal,
  unitCost: BigDecimal,
  supplierId: Int,
  status: String,
  attribute1: Option[String],
  attribute2: Option[String],
  attribute3: Option[String],
  attribute4: Option[String],
  attribute5: Option[String],
  product: Product,
  quantity: Int
) {

  /**
    * itemMapper.xml - getItemListByProduct/getItem
    */
  def this(
    itemId: String,
    listPrice: java.math.BigDecimal,
    unitCost: java.math.BigDecimal,
    supplierId: java.lang.Integer,
    productId: String,
    productName: String,
    productDescription: String,
    productCategoryId: String,
    status: String,
    attribute1: String,
    attribute2: String,
    attribute3: String,
    attribute4: String,
    attribute5: String,
    quantity: java.lang.Integer
  ) = this(
    itemId,
    listPrice,
    unitCost,
    supplierId,
    status,
    Option(attribute1),
    Option(attribute2),
    Option(attribute3),
    Option(attribute4),
    Option(attribute5),
    Product(
      productId = productId,
      categoryId = productCategoryId,
      name = productName,
      description = productDescription
    ),
    quantity
  )
}

object Item {

  /**
    * JSON deserializer for session cache
    */
  implicit val reads = Json.reads[Item]

  /**
    * JSON serializer for session cache
    */
  implicit val writes = Json.writes[Item]
}
