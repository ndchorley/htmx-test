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
        { messages.addLast(aRandomMessage()) }, 0, 1, SECONDS
    )
}

private val seenIndicies = mutableSetOf<Int>()

private fun aRandomMessage(): String {
    if (seenIndicies.size >= 0.8 * messagesToChooseFrom.size) seenIndicies.clear()

    var randomIndex = Random.nextInt(0, messagesToChooseFrom.size)
    
    while (seenIndicies.contains(randomIndex))
        randomIndex = Random.nextInt(0, messagesToChooseFrom.size)
    
    seenIndicies.add(randomIndex)
    
    val message = messagesToChooseFrom[randomIndex]

    return message
}

private val messagesToChooseFrom =
    listOf(
        "Hello",
        "Goodbye",
        "This is a test",
        "It's ${currentDayOfTheWeek()}",
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
        "What would your dream treehouse look like?",
        "The Earth revolves around the Sun.",
        "Water boils at 100 degrees Celsius.",
        "Humans have 206 bones in their bodies.",
        "The Pacific Ocean is the largest ocean.",
        "Lightning is hotter than the surface of the Sun.",
        "Mount Everest is the tallest mountain on Earth.",
        "Honey never spoils.",
        "Sharks have been around longer than trees.",
        "Bananas are berries, but strawberries are not.",
        "Octopuses have three hearts.",
        "The Eiffel Tower can grow taller in summer.",
        "Venus is the hottest planet in our solar system.",
        "A group of flamingos is called a flamboyance.",
        "The human brain uses about 20% of the body’s energy.",
        "The Great Wall of China is over 13,000 miles long.",
        "Sound travels faster in water than in air.",
        "The speed of light is approximately 299,792 kilometers per second.",
        "Ants can lift objects 50 times their body weight.",
        "The moon has no atmosphere.",
        "The Amazon rainforest produces 20% of the world’s oxygen.",
        "A day on Venus is longer than its year.",
        "Koalas sleep up to 22 hours a day.",
        "The human nose can detect over 1 trillion scents.",
        "Penguins can’t fly but they are excellent swimmers.",
        "The heart beats about 100,000 times a day.",
        "The Sahara is the largest hot desert in the world.",
        "Jellyfish have existed for over 500 million years.",
        "The blue whale is the largest animal ever known.",
        "The human body is about 60% water.",
        "Mars has the tallest volcano in the solar system.",
        "A bolt of lightning can reach 30,000°C.",
        "The average adult has about 5 liters of blood.",
        "Bamboo can grow up to 91 cm in a day.",
        "The speed of sound is about 343 meters per second.",
        "Sloths can hold their breath longer than dolphins.",
        "The Milky Way galaxy contains over 100 billion stars.",
        "The cheetah is the fastest land animal.",
        "A sneeze can travel up to 100 miles per hour.",
        "The Earth’s core is as hot as the Sun’s surface.",
        "Butterflies taste with their feet.",
        "The longest river in the world is the Nile.",
        "Cats have five toes on their front paws and four on the back.",
        "The human eye can distinguish about 10 million colors.",
        "Polar bears have black skin under their white fur.",
        "The deepest part of the ocean is the Mariana Trench.",
        "A crocodile cannot stick its tongue out.",
        "The average cloud weighs over a million pounds.",
        "The tongue is the strongest muscle relative to its size.",
        "The moon is slowly drifting away from Earth.",
        "The shortest war in history lasted 38 minutes.",
        "The human stomach gets a new lining every few days.",
        "The coldest temperature ever recorded was −89.2°C in Antarctica.",
        "A giraffe’s neck has the same number of vertebrae as a human’s.",
        "The Earth’s rotation is gradually slowing down.",
        "The oldest known living tree is over 4,800 years old.",
        "The human body has over 600 muscles.",
        "The largest volcano on Earth is Mauna Loa in Hawaii.",
        "The average lifespan of a taste bud is about 10 days.",
        "The sun makes up 99.8% of the solar system’s mass.",
        "The human heart can create enough pressure to squirt blood 30 feet.",
        "The longest recorded flight of a chicken is 13 seconds.",
        "The Earth is not a perfect sphere.",
        "A day on Jupiter lasts about 10 hours.",
        "The human skeleton renews itself every 10 years.",
        "The largest living structure is the Great Barrier Reef.",
        "The human brain has about 86 billion neurons.",
        "The coldest place on Earth is the East Antarctic Plateau.",
        "The average person blinks about 15–20 times per minute.",
        "The moon has moonquakes.",
        "The human liver can regenerate itself.",
        "The deepest lake in the world is Lake Baikal.",
        "The average lifespan of a human hair is 2–7 years.",
        "The Earth’s atmosphere is composed mostly of nitrogen.",
        "The loudest animal is the sperm whale.",
        "The human body produces about 1 to 1.5 liters of saliva daily.",
        "The largest insect is the giant weta from New Zealand.",
        "The human brain is about 75% water.",
        "The hottest temperature ever recorded was 56.7°C in Death Valley.",
        "The average person walks about 100,000 miles in a lifetime.",
        "The moon is about 384,400 kilometers from Earth.",
        "The human body contains about 37.2 trillion cells.",
        "The largest land carnivore is the polar bear.",
        "The Earth’s magnetic field protects us from solar radiation.",
        "The average human has about 100,000 hairs on their head.",
        "The human eye blinks about 4 million times a year.",
        "The largest bird is the ostrich.",
        "The human brain generates about 20 watts of power.",
        "The smallest bone in the body is in the ear.",
        "The Earth’s crust is made of tectonic plates.",
        "The average person has about 2 million sweat glands.",
        "The human body sheds about 600,000 skin particles per hour.",
        "The largest mammal migration is by bats in Zambia.",
        "The human heart pumps about 2,000 gallons of blood daily.",
        "The oldest known animal lived for over 500 years.",
        "The human body can survive without food for weeks.",
        "The average person spends about 6 years dreaming.",
        "The Earth’s circumference is about 40,075 kilometers.",
        "The human brain can store about 2.5 petabytes of data.",
        "The tallest tree ever recorded was over 115 meters tall.",
        "The human body has about 100,000 miles of blood vessels."
    )

private fun currentDayOfTheWeek() =
    LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
