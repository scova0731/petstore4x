package com.github.scova0731.petstore4s.step1.domain

import net.sourceforge.stripes.validation.Validate


/**
  * The Class Account.
  */
case class Account(
  username: String,
  password: String,
  email: String,
  private val _firstName: String,
  private val _lastName: String,
  status: String,
  address1: String,
  address2: String,
  city: String,
  state: String,
  zip: String,
  country: String,
  phone: String,
  favouriteCategoryId: String,
  languagePreference: String,
  listOption: Boolean,
  bannerOption: Boolean,
  bannerName: String
) {

  val firstName: String = setFirstName()
  val lastName: String = setLastName()

  @Validate(required = true, on = Array("newAccount", "editAccount")) 
  def setFirstName(): String = {
    this._firstName
  }

  @Validate(required = true, on = Array("newAccount", "editAccount")) 
  def setLastName(): String = {
    this._lastName
  }
}
