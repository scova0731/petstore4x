package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.Account

trait AccountMapper {
  def getAccountByUsername(username: String): Account

  def getAccountByUsernameAndPassword(username: String, password: String): Account

  def insertAccount(account: Account): Unit

  def insertProfile(account: Account): Unit

  def insertSignon(account: Account): Unit

  def updateAccount(account: Account): Unit

  def updateProfile(account: Account): Unit

  def updateSignon(account: Account): Unit
}
