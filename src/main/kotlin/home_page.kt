package org.example

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK

fun homePage(request: Request): Response {
    val page = renderTemplate(HomePage)

    return Response(OK).body(page)
}
