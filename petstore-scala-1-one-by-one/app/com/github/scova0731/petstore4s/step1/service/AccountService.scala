package com.github.scova0731.petstore4s.step1.service

import javax.inject.Inject

import com.github.scova0731.petstore4s.step1.domain.Account
import com.github.scova0731.petstore4s.step1.mapper.AccountMapper

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
  //  @Transactional
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
  //  @Transactional
  def updateAccount(account: Account): Unit = {
    accountMapper.updateAccount(account)
    accountMapper.updateProfile(account)
    // TODO コレは何？
    //    if (account.password != null && account.password.length > 0)
    //      accountMapper.updateSignon(account)
  }
}
