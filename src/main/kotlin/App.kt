package org.example

import org.http4k.core.Method.GET
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.util.concurrent.ConcurrentLinkedDeque

class App(messages: ConcurrentLinkedDeque<String>) {
    private val handleRequest =
        routes(
            "/" bind GET to ::homePage,
            "/latest-message" bind GET to LatestMessage(messages)
        )

    private val server =
        printRequest.then(handleRequest).asServer(Jetty(8000))

    fun start() = server.start()
}
