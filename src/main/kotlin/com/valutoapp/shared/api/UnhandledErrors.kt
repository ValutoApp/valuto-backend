package com.valutoapp.shared.api

import com.valutoapp.shared.domain.CorruptedDataException
import io.github.oshai.kotlinlogging.KLogger
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.ContentTransformationException
import kotlinx.serialization.SerializationException

fun Throwable.toApiError(): ApiError = when (this) {
    is BadRequestException if isJsonBodyIncorrect() -> SharedErrors.invalidBody()
    else -> SharedErrors.internalError()
}

fun KLogger.logUnhandled(cause: Throwable) = when (cause) {
    is CorruptedDataException -> error { "corrupted ${cause.entity}.${cause.field}, id=${cause.entityId}" }
    is BadRequestException -> Unit
    else -> error(cause) { "unhandled ${cause::class.simpleName}" }
}

private fun BadRequestException.isJsonBodyIncorrect(): Boolean = generateSequence(this as Throwable?) { it.cause }
    .any { it is ContentTransformationException || it is SerializationException }
