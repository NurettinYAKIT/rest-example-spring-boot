package com.nurettinyakit.simulation.common

object Common {

    val environments =
        Map(
            "LOCAL" -> "http://localhost:8080",
            "DEV" -> "http://localhost:8080"
        )

    val environment: String = Option(System.getProperty("environment")) getOrElse "LOCAL"
    val baseURL: String = Option(System.getProperty("baseURL")) getOrElse environments(environment)
    val usersToIncrementPerIteration: String = Option(System.getProperty("usersToIncrementPerIteration")) getOrElse "5"
    val iterations: String = Option(System.getProperty("iterations")) getOrElse "5"
    val iterationsLengthInSeconds: String = Option(System.getProperty("iterationsLengthInSeconds")) getOrElse "10"
    var maxDurationInSeconds: String = Option(System.getProperty("maxDurationInSeconds")) getOrElse "60"

    override def toString = s"Common(baseURL=$baseURL, usersToIncrementPerIteration=$usersToIncrementPerIteration, iterations=$iterations, iterationsLengthInSeconds=$iterationsLengthInSeconds, maxDurationInSeconds=$maxDurationInSeconds)"
}
