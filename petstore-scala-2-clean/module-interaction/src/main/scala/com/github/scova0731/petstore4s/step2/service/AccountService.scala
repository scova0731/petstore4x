package com.github.scova0731.petstore4s.step2.service

import javax.inject.Inject

import com.github.scova0731.petstore4s.step2.domain.Account
import com.github.scova0731.petstore4s.step2.mapper.AccountMapper

class AccountService @Inject()(
  accountMapper: AccountMapper
) {

  def getAccount(username: String): Account =
    accountMapper.getAccountByUsername(username)

  def getAccount(username: String, password: String): Option[Account] =
    Option(accountMapper.getAccountByUsernameAndPassword(username, password))

  /**
    * Insert account.
    *
    * @param account the account
    */
  def insertAccount(account: Account): Unit = {
    accountMapper.insertAccount(account)
    accountMapper.insertProfile(account)
    accountMapper.insertSignon(account)
  }

  /**
    * Update account.
    *
    * @param account the account
    */
  def updateAccount(account: Account): Unit = {
    accountMapper.updateAccount(account)
    accountMapper.updateProfile(account)
    if (!account.isPasswordEmpty)
      accountMapper.updateSignon(account)
  }
}
