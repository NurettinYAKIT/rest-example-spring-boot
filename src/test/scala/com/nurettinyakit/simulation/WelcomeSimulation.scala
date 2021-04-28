package com.nurettinyakit.simulation

import com.nurettinyakit.simulation.common.Common
import com.nurettinyakit.simulation.common.Common._
import com.nurettinyakit.simulation.requests.Welcome
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.HeaderValues._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

/**
 * Running this load test simulation:
 *
 * LOCAL
 * mvn gatling:test -Dgatling.simulationClass=com.nurettinyakit.simulation.WelcomeSimulation
 *
 * DEV
 * mvn clean gatling:test -Dgatling.simulationClass=com.nurettinyakit.simulation.WelcomeSimulation -Denvironment=DEV
 *
 * TEST
 * mvn clean gatling:test -Dgatling.simulationClass=com.nurettinyakit.simulation.WelcomeSimulation -Denvironment=TEST
 *
 */
class WelcomeSimulation extends Simulation {
    println("===> WelcomeSimulation <===")
    println("Values for WelcomeSimulation")
    println(Common)

    val feeder: BatchableFeederBuilder[String]#F = csv("gatling-data/feeders/gatling-welcome-feed.csv").circular

    val httpConf: HttpProtocolBuilder = http
        .baseUrl(baseURL)
        .disableCaching
        .acceptHeader(ApplicationJson)
        .userAgentHeader("Performance Tests - WelcomeSimulation")


    var scnReqWelcome: ScenarioBuilder = scenario("Get Welcome Scenario")
        .feed(feeder)
        .exec(Welcome.request)

    setUp(
        scnReqWelcome.inject(
            incrementUsersPerSec(usersToIncrementPerIteration.toInt)
                .times(iterations.toInt)
                .eachLevelLasting(iterationsLengthInSeconds.toInt seconds)
                .startingFrom(1)
        ))
        .assertions(global.responseTime.percentile3.lte(200)) // 95th percentile
        .assertions(global.successfulRequests.percent.gte(95)) // 98 percent of request should succeed
        .maxDuration(maxDurationInSeconds.toInt seconds)
        .protocols(httpConf)

}
