package org.example

import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel

val renderTemplate = HandlebarsTemplates().CachingClasspath()

object HomePage : ViewModel
data class Message(val message: String) : ViewModel
