package com.valutoapp.auth

import java.util.UUID
import kotlin.coroutines.CoroutineContext

data class UserContext(
    val userId: UUID,
) : CoroutineContext.Element {
    companion object KEY : CoroutineContext.Key<UserContext>

    override val key: CoroutineContext.Key<*>
        get() = KEY
}
