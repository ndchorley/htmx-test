package org.example

import java.util.concurrent.ConcurrentLinkedDeque

fun main() {
    val messages = ConcurrentLinkedDeque<String>()

    addMessagesAtRegularIntervals(messages)

    App(messages).server.start()
}
