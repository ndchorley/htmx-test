package org.example

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.random.Random

fun addMessagesAtRegularIntervals(messages: ConcurrentLinkedDeque<String>) {
    val scheduler = Executors.newScheduledThreadPool(1)

    scheduler.scheduleAtFixedRate(
        { addRandomMessage(messages) }, 5, 1, SECONDS
    )
}

private val messagesToChooseFrom =
    listOf(
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

private fun addRandomMessage(messages: ConcurrentLinkedDeque<String>) {
    val randomIndex = Random.nextInt(0, messagesToChooseFrom.size)
    val message = messagesToChooseFrom[randomIndex]

    messages.addLast(message)
}

