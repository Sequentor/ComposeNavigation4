package ru.altum.composione.internal

import kotlinx.serialization.json.Json
import ru.altum.composione.destination.Destination

internal val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

internal fun encodeDestination(destination: Destination): String = json.encodeToString(
    DestinationSerializer(),
    destination
)

@PublishedApi
internal fun decodeDestination(jsonString: String): Destination = json.decodeFromString(
    DestinationSerializer(),
    jsonString
)