package com.jkontodos.cabifystore.data.exception

/** * Sealed class to organize error types from service calls. */
sealed class Failure {
    data object ServerError : Failure()
    data object UnauthorizedError : Failure()
    class Exception(val exception: java.lang.Exception) : Failure()

}