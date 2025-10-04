package org.example

import org.http4k.core.*
import org.http4k.lens.contentType
import java.util.concurrent.ConcurrentLinkedDeque

class LatestMessage(
    private val messages: ConcurrentLinkedDeque<String>
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val latestMessage = messages.removeFirst()

        val html = renderTemplate(Message(latestMessage))

        return Response(Status.OK)
            .contentType(ContentType.TEXT_HTML)
            .body(html)
    }
}
