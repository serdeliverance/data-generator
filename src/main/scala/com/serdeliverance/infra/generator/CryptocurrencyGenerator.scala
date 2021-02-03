package com.serdeliverance.infra.generator

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.javadsl.SlickSession
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future

object CryptocurrencyGenerator extends App {

  implicit val system = ActorSystem("CryptocurrencyGenerator")

  implicit val session = SlickSession.forConfig("slick-crypto")

  implicit val executionContext = system.dispatcher

  val config           = ConfigFactory.load()
  val userRecords      = config.getInt("crypto.generator.number-of-users")
  val portfolioRecords = config.getInt("crypto.generator.number-of-portfolios")

  // generate data
  val result = for {
    _ <- generateUsers()
    - <- getCryptocurrencies()
    _ <- generatePortfolio()
  } yield Done

  /**
    * TODO implement
    *
    * should generate users passing userRecords as a parameter (so, actual generateUsers on generator.entities.UserGenerator
    * should be refactor)
    *
    * @return
    */
  private def generateUsers(): Future[Done] = ???

  /**
    * TODO implement
    *
    * should get crypto currencies from crypto market cap API
    *
    * @return
    */
  private def getCryptocurrencies(): Future[Done] = ???

  /**
    * TODO implement
    *
    * should generate portfolio using generated users and crypto currencies.
    * @return
    */
  private def generatePortfolio(): Future[Done] = ???

  result.onComplete { _ =>
    system.log.info("Cryptocurrency data generation stream completed. Shutting down system...")
    system.terminate()
  }
}
