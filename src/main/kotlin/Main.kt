package org.example

import org.http4k.core.ContentType
import org.http4k.core.Filter
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.lens.contentType
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.random.Random

fun main() {
    addMessagesAtRegularIntervals()

    printRequest.then(router)
        .asServer(Jetty(8000))
        .start()
}

val renderTemplate = HandlebarsTemplates().CachingClasspath()

object View : ViewModel
data class Message(val message: String) : ViewModel

val printRequest = Filter { next ->
    { request ->
        val london = ZoneId.of("Europe/London")
        val timeNow = Clock.systemDefaultZone().instant().atZone(london)

        println("$timeNow ${request.method} ${request.uri}")

        next(request)
    }
}

var countOfClicks = 0

val messages = ConcurrentLinkedDeque<String>()

val router =
    routes(
        "/" bind GET to { _ ->
            val page = renderTemplate(View)
            Response(OK).body(page)
        },

        "/clicked" bind POST to { _ ->
            countOfClicks++

            val timeOrTimes =
                if (countOfClicks == 1) "time" else "times"

            Response(OK).body("Clicked $countOfClicks $timeOrTimes")
        },

        "/latest-message" bind GET to { _ ->
            val latestMessage = messages.removeFirst()

            Response(OK)
                .contentType(ContentType.TEXT_HTML)
                .body(renderTemplate(Message(latestMessage)))
        }
    )

val random = Random(Clock.systemDefaultZone().millis())

fun addRandomMessage() {
    val messagesToAdd = listOf(
        "Hello",
        "Goodbye",
        "This is a test",
        "It's ${LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)}",
        "This app was written with http4k and htmx",
        "The quick brown fox jumped over the lazy dog",
        "Lorem ipsum",
        "Ich komme aus London",
        "J'irai en France cette année",
        "Am Samstagvormittag lerne ich Deutsch",
        "My very easy method just speeds up naming planets",
        "José Raúl Capablanca was world champion from 1921 to 1927"
    )

    val message = messagesToAdd[random.nextInt(0, messagesToAdd.size)]

    messages.addLast(message)
}

fun addMessagesAtRegularIntervals() {
    val scheduler = Executors.newScheduledThreadPool(1)

    scheduler.scheduleAtFixedRate(
        ::addRandomMessage, 5, 3, SECONDS
    )
}
