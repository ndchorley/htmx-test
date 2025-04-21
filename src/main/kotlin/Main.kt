package org.example

import org.http4k.core.Method.GET
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import java.util.concurrent.ConcurrentLinkedDeque

fun main() {
    addMessagesAtRegularIntervals(messages)

    printRequest.then(router)
        .asServer(Jetty(8000))
        .start()
}

val renderTemplate = HandlebarsTemplates().CachingClasspath()

object View : ViewModel
data class Message(val message: String) : ViewModel

val messages = ConcurrentLinkedDeque<String>()

val router =
    routes(
        "/" bind GET to ::homePage,
        "/latest-message" bind GET to LatestMessage(messages)
    )
