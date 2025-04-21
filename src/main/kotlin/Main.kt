package org.example

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
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

var latestMessageRequests = 0
val messages = ConcurrentLinkedDeque<String>()

val router =
    routes(
        "/" bind GET to { _ ->
            val page = renderTemplate(View)
            Response(OK).body(page)
        },

        "/latest-message" bind GET to { _ ->
            latestMessageRequests++

            val response =
                if (latestMessageRequests > 40) Response(Status(286, null))
                else {
                    val latestMessage = messages.removeFirst()

                    Response(OK)
                        .contentType(ContentType.TEXT_HTML)
                        .body(renderTemplate(Message(latestMessage)))
                }

            response
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
        "José Raúl Capablanca was world champion from 1921 to 1927",
        "Did you know the Eiffel Tower can grow taller in summer?",
        "Have you ever tried stargazing during a meteor shower?",
        "Why do cats always land on their feet?",
        "Imagine living on a floating city in the middle of the ocean.",
        "Do you think time travel will ever be possible?",
        "I just learned that honey never spoils—ancient jars are still edible!",
        "What’s your favorite constellation in the night sky?",
        "Have you ever wondered how chameleons change their color?",
        "Did you know octopuses have three hearts and blue blood?",
        "Would you visit a ghost town for the thrill of it?",
        "Have you heard of bioluminescent beaches that glow at night?",
        "The hummingbird is the only bird that can fly backwards—amazing, right?",
        "What would you name a new planet if you discovered one?",
        "Did you know the Amazon rainforest produces 20% of the world's oxygen?",
        "Ever thought about how deep the ocean really is?",
        "Can you imagine life before the internet—what did people do?",
        "How cool would it be to have your own robot assistant?",
        "I heard there are more stars in the universe than grains of sand on Earth.",
        "Do you believe there’s intelligent life beyond our planet?",
        "What would your dream treehouse look like?"
    )

    val message = messagesToAdd[random.nextInt(0, messagesToAdd.size)]

    messages.addLast(message)
}

fun addMessagesAtRegularIntervals() {
    val scheduler = Executors.newScheduledThreadPool(1)

    scheduler.scheduleAtFixedRate(
        ::addRandomMessage, 5, 1, SECONDS
    )
}
