package ru.altum.composione.internal.extension

import android.os.Bundle
import ru.altum.composione.destination.Destination
import ru.altum.composione.internal.encodeDestination

internal fun Destination.toBundle() = Bundle().apply {
    putString(key, encodeDestination(this@toBundle))
}