package com.github.scova0731.petstore4s.step2.service

import javax.inject.Inject

import com.github.scova0731.petstore4s.step2.domain.Account
import com.github.scova0731.petstore4s.step2.repository.AccountRepository

class AccountService @Inject()(
  repo: AccountRepository
) {

  def getAccount(username: String): Account =
    repo.getAccountByUsername(username)

  def getAccount(username: String, password: String): Option[Account] =
    Option(repo.getAccountByUsernameAndPassword(username, password))

  /**
    * Insert account.
    *
    * @param account the account
    */
  def insertAccount(account: Account): Unit = {
    repo.insertAccount(account)
    repo.insertProfile(account)
    repo.insertSignon(account)
  }

  /**
    * Update account.
    *
    * @param account the account
    */
  def updateAccount(account: Account): Unit = {
    repo.updateAccount(account)
    repo.updateProfile(account)
    if (!account.isPasswordEmpty)
      repo.updateSignon(account)
  }
}
