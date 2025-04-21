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
        val response =
            if (latestMessageRequests > 40) Response(Status(286, null))
            else {
                val latestMessage = messages.removeFirst()

                Response(Status.OK)
                    .contentType(ContentType.TEXT_HTML)
                    .body(renderTemplate(Message(latestMessage)))
            }
        return response
    }
}