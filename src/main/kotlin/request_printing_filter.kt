package org.example

import org.http4k.core.Filter
import java.time.Clock
import java.time.ZoneId

val printRequest = Filter { next ->
    { request ->
        println("${timeNow()} ${request.method} ${request.uri}")

        next(request)
    }
}

private val london = ZoneId.of("Europe/London")

private fun timeNow() =
    Clock.systemDefaultZone().instant().atZone(london)
