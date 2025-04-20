package org.example

import org.http4k.core.Filter
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import java.time.Clock
import java.time.ZoneId

val renderTemplate = HandlebarsTemplates().CachingClasspath()

object View : ViewModel

val printRequest = Filter { next ->
    { request ->
        val london = ZoneId.of("Europe/London")
        val timeNow = Clock.systemDefaultZone().instant().atZone(london)

        println("$timeNow ${request.method} ${request.uri}")

        next(request)
    }
}

var countOfClicks = 0

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
        }
    )

fun main() {
    printRequest.then(router)
        .asServer(Jetty(8000))
        .start()
}
