package org.example

import org.http4k.core.Filter
import java.time.Clock
import java.time.ZoneId

private val london = ZoneId.of("Europe/London")

val printRequest = Filter { next ->
    { request ->
        val timeNow = Clock.systemDefaultZone().instant().atZone(london)

        println("$timeNow ${request.method} ${request.uri}")

        next(request)
    }
}
