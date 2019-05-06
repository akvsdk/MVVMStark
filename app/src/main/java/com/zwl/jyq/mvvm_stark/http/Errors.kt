package com.zwl.jyq.mvvm_stark.http

sealed class Errors : Throwable() {
    object NetworkError : Errors()
    object EmptyInputError : Errors()
    object EmptyResultsError : Errors()
    object ReLoginError : Errors()
    object SingleError : Errors()
}