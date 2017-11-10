package com.github.scova0731.petstore4s.step1.domain

import play.api.libs.json.Json
//import net.sourceforge.stripes.validation.Validate


/**
  * The Class Account.
  */
case class Account(
  username: String,
//  password: String,
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
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.String,
  // java.lang.Integer,
  // java.lang.Integer,
  // java.lang.String]]

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
  this(username, email, firstName, lastName, status, address1, address2,
    city, state, zip, country, phone, languagePreference, favouriteCategoryId,
    listOption != 0, bannerOption != 0, bannerName)

//  val firstName: String = setFirstName()
//  val lastName: String = setLastName()

//  @Validate(required = true, on = Array("newAccount", "editAccount"))
//  def setFirstName(): String = {
//    this._firstName
//  }
//
//  @Validate(required = true, on = Array("newAccount", "editAccount"))
//  def setLastName(): String = {
//    this._lastName
//  }
}


object Account {

  implicit val reads = Json.reads[Account]
  implicit val writes = Json.writes[Account]
}
