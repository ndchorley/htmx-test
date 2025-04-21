package org.example

import org.http4k.core.Method.GET
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.util.concurrent.ConcurrentLinkedDeque

fun main() {
    val messages = ConcurrentLinkedDeque<String>()

    addMessagesAtRegularIntervals(messages)

    class App {
        val router =
            routes(
                "/" bind GET to ::homePage,
                "/latest-message" bind GET to LatestMessage(messages)
            )

        val server = printRequest.then(router)
            .asServer(Jetty(8000))
    }

    App().server.start()
}
