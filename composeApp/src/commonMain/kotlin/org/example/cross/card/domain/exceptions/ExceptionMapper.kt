package org.example.cross.card.domain.exceptions

interface ExceptionMapper {
    fun map(throwable: Throwable): Throwable
}