package org.example

import org.http4k.core.*
import org.http4k.lens.contentType
import java.util.concurrent.ConcurrentLinkedDeque

class LatestMessage(
    private val messages: ConcurrentLinkedDeque<String>
) : HttpHandler {
    private var latestMessageRequests = 0

    override fun invoke(request: Request): Response {
        latestMessageRequests++

        if (latestMessageRequests > 40)
            return Response(Status(286, null))

        val latestMessage = messages.removeFirst()

        return Response(Status.OK)
            .contentType(ContentType.TEXT_HTML)
            .body(renderTemplate(Message(latestMessage)))

    }
}
