package org.example.cross.card.core.domain.exceptions

interface ExceptionMapper {
    fun map(throwable: Throwable): Throwable
}