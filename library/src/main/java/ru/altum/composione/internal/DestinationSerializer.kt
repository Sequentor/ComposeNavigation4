package ru.altum.composione.internal

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer
import ru.altum.composione.destination.Destination

@PublishedApi
@OptIn(InternalSerializationApi::class)
internal class DestinationSerializer<T : Destination> : KSerializer<T> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(serialName = "ru.altum.composione.destination.Destination") {
            element(elementName = "type", descriptor = serialDescriptor<String>())
            element(
                elementName = "value",
                descriptor = buildClassSerialDescriptor(serialName = "Any"),
            )
        }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): T {
        return decoder.decodeStructure(descriptor) {
            val className = decodeStringElement(descriptor, decodeElementIndex(descriptor))
            val serializer = Class.forName(className).kotlin.serializer()
            decodeSerializableElement(descriptor, decodeElementIndex(descriptor), serializer) as T
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeStructure(descriptor) {
            val className = value::class.java.name
            encodeStringElement(descriptor, index = 0, className)
            val serializer = value::class.serializer() as KSerializer<T>
            encodeSerializableElement(descriptor, index = 1, serializer, value)
        }
    }
}