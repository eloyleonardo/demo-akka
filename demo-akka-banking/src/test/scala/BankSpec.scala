package com.weiglewilczek.demo.akka
package banking

import Account._
import Bank._

import org.specs.Specification
import se.scalablesolutions.akka.actor.Actor._

class BankSpec extends Specification {

  "Calling Bank.transfer with an amount of 10" should {
    """result in an amount of -10 for the "from" Account and an amount of 10 for the "to" Account.""" in {
      val bank = actorOf[Bank].start
      val from = actorOf[Account].start
      val to   = actorOf[Account].start
      try {
        bank ! Transfer(10, from, to)

        Thread sleep 1000 // Let's wait until the transfer is finished!

        val fromBalance = from !! GetBalance
        fromBalance mustEqual Some(Balance(-10))
        val toBalance = to !! GetBalance
        toBalance mustEqual Some(Balance(10))
      }
      finally {
        from.stop
        to.stop
        bank.stop
      }
    }
  }

  "Calling Bank.transfer with an amount of 20" should {
    """result in an amount of 0 for the "from" Account and an amount of 0 for the "to" Account.""" in {
      val bank = actorOf[Bank].start
      val from = actorOf[Account].start
      val to   = actorOf[Account].start
      try {
        bank ! Transfer(20, from, to)

        Thread sleep 1000 // Let's wait until the transfer is finished!

        val fromBalance = from !! GetBalance
        fromBalance mustEqual Some(Balance(0))
        val toBalance = to !! GetBalance
        toBalance mustEqual Some(Balance(0))
      }
      finally {
        from.stop
        to.stop
        bank.stop
      }
    }
  }
}
