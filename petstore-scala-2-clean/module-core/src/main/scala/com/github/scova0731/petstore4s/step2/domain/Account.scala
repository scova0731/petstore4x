package com.github.scova0731.petstore4s.step2.domain

import play.api.libs.json.Json


/**
  * The Class Account.
  */
case class Account(
  username: String,
  password: String, // NOTE 更新時のみ利用される
  email: String,
  firstName: String,
  lastName: String,
  status: String,
  address1: String,
  address2: String,
  city: String,
  state: String,
  zip: String,
  country: String,
  phone: String,
  languagePreference: String,
  favouriteCategoryId: String,
  listOption: Boolean,
  bannerOption: Boolean,
  bannerName: String
) {

  // NOTE Dummy password value is added to escape null value
  def this(
    username: String,
    email: String,
    firstName: String,
    lastName: String,
    status: String,
    address1: String,
    address2: String,
    city: String,
    state: String,
    zip: String,
    country: String,
    phone: String,
    languagePreference: String,
    favouriteCategoryId: String,
    listOption: java.lang.Integer, // for MyBatis
    bannerOption: java.lang.Integer, // for MyBatis
    bannerName: String
  ) =
  this(username, Account.dummyPassword, email, firstName, lastName, status, address1, address2,
    city, state, zip, country, phone, languagePreference, favouriteCategoryId,
    listOption != 0, bannerOption != 0, bannerName)

  def isPasswordEmpty: Boolean = password == Account.dummyPassword

  // NOTE getter for MyBatis (See AccountMapper.xml)
  def isListOption(): Boolean = listOption

  // NOTE getter for MyBatis (See AccountMapper.xml)
  def isBannerOption(): Boolean = bannerOption

}


object Account {

  private val dummyPassword = "DUMMY"

  implicit val reads = Json.reads[Account]
  implicit val writes = Json.writes[Account]
  
  // NOTE Null handling for Java
  def applyForForm(
    username: String,
    password: Option[String],
    email: String,
    firstName: String,
    lastName: String,
    status: String,
    address1: String,
    address2: String,
    city: String,
    state: String,
    zip: String,
    country: String,
    phone: String,
    languagePreference: String,
    favouriteCategoryId: String,
    listOption: Boolean,
    bannerOption: Boolean
  ) =
    new Account(username, password.orNull, email, firstName, lastName, status, address1, address2,
      city, state, zip, country, phone, languagePreference, favouriteCategoryId,
      listOption, bannerOption, null)

  // NOTE Null handling for Java
  def unapplyForForm(a: Account): Option[(
    String, Option[String], String, String, String, String, String, String, String, String,
      String, String, String, String, String, Boolean, Boolean)] =
    Some((a.username, Option(a.password), a.email, a.firstName, a.lastName, a.status, a.address1, a.address2,
      a.city, a.state, a.zip, a.country, a.phone, a.languagePreference, a.favouriteCategoryId,
      a.listOption, a.bannerOption))
}
