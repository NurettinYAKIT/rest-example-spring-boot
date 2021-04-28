package com.nurettinyakit.simulation.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Welcome {

    val headerRequest = Map(
        "user-id" -> "1234",
        "Accept" -> "application/json;charset=UTF-8"
    )

    val request: HttpRequestBuilder = http("Welcome")
        .get("/welcome")
        .queryParam("name", "${name}")
        .headers(headerRequest)
        .check(status.in(200))
}
