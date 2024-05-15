package com.jkontodos.cabifystore.data.exception

sealed class Failure {
    data object ServerError : Failure()
    data object UnauthorizedError : Failure()
    class ServerErrorWithException(val exception: Exception) : Failure()

}