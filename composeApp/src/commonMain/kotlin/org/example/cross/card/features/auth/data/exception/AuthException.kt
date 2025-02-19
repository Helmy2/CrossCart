package org.example.cross.card.features.auth.data.exception


import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.io.IOException
import org.example.cross.card.core.domain.exceptions.ExceptionMapper

sealed class AuthException(
    message: String, cause: Throwable? = null
) : Exception(message, cause) {

    // Input validation errors
    class InvalidEmailException(
        message: String = "Invalid email format", cause: Throwable? = null
    ) : AuthException(message, cause)

    class WeakPasswordException(
        message: String = "Password is too weak", cause: Throwable? = null
    ) : AuthException(message, cause)

    // Authentication failures
    class InvalidCredentialsException(
        message: String = "Invalid credentials", cause: Throwable? = null
    ) : AuthException(message, cause)

    class UserNotFoundException(message: String = "User not found", cause: Throwable? = null) :
        AuthException(message, cause)

    class UserAlreadyExistsException(
        message: String = "User already exists", cause: Throwable? = null
    ) : AuthException(message, cause)

    class EmailNotConfirmedException(
        message: String = "Email not confirmed", cause: Throwable? = null
    ) : AuthException(message, cause)

    // Session-related errors
    class SessionExpiredException(message: String = "Session expired", cause: Throwable? = null) :
        AuthException(message, cause)

    class SessionNotFoundException(
        message: String = "Session not found", cause: Throwable? = null
    ) : AuthException(message, cause)

    // Network and rate-limiting errors
    class NetworkException(message: String = "Network error", cause: Throwable? = null) :
        AuthException(message, cause)

    class RateLimitExceededException(
        message: String = "Rate limit exceeded", cause: Throwable? = null
    ) : AuthException(message, cause)

    // Provider-specific errors
    class ProviderDisabledException(
        message: String = "Provider is disabled", cause: Throwable? = null
    ) : AuthException(message, cause)

    class OAuthProviderNotSupportedException(
        message: String = "OAuth provider not supported", cause: Throwable? = null
    ) : AuthException(message, cause)

    // General or unknown errors
    class AuthenticationException(
        message: String = "Authentication failed", cause: Throwable? = null
    ) : AuthException(message, cause)

    class UnknownException(
        message: String = "An unknown error occurred", cause: Throwable? = null
    ) : AuthException(message, cause)
}

class AuthExceptionMapper : ExceptionMapper {

    override fun map(throwable: Throwable): Throwable {
        return when (throwable) {
            is AuthException -> throwable
            is AuthRestException -> mapSupabaseAuthError(throwable)
            is IOException -> AuthException.NetworkException("Network error", throwable)
            else -> {
                // Fallback for other exceptions
                AuthException.UnknownException("Unknown error: ${throwable.message}", throwable)
            }
        }
    }

    private fun mapSupabaseAuthError(exception: AuthRestException): AuthException {
        // Extract the AuthErrorCode from the exception
        val errorCode = exception.errorCode ?: return AuthException.UnknownException(
            "An unknown authentication error occurred", exception
        )

        return when (errorCode) {
            AuthErrorCode.EmailExists -> AuthException.UserAlreadyExistsException(
                "User already exists", exception
            )

            AuthErrorCode.UserNotFound -> AuthException.UserNotFoundException(
                "User not found", exception
            )

            AuthErrorCode.InvalidCredentials -> AuthException.InvalidCredentialsException(
                "Invalid credentials", exception
            )

            AuthErrorCode.SessionExpired -> AuthException.SessionExpiredException(
                "Session expired", exception
            )

            AuthErrorCode.SessionNotFound -> AuthException.SessionNotFoundException(
                "Session not found", exception
            )

            AuthErrorCode.WeakPassword -> AuthException.WeakPasswordException(
                "Password is too weak", exception
            )

            AuthErrorCode.EmailNotConfirmed -> AuthException.EmailNotConfirmedException(
                "Email not confirmed", exception
            )

            AuthErrorCode.OverRequestRateLimit -> AuthException.RateLimitExceededException(
                "Rate limit exceeded", exception
            )

            AuthErrorCode.ValidationFailed -> AuthException.InvalidEmailException(
                "Validation failed", exception
            )

            AuthErrorCode.PhoneExists -> AuthException.UserAlreadyExistsException(
                "Phone number already exists", exception
            )

            AuthErrorCode.ProviderDisabled -> AuthException.ProviderDisabledException(
                "Provider is disabled", exception
            )

            AuthErrorCode.OauthProviderNotSupported -> AuthException.OAuthProviderNotSupportedException(
                "OAuth provider not supported", exception
            )

            // Add more mappings as needed
            else -> AuthException.AuthenticationException(
                "Authentication failed: ${errorCode.value}", exception
            )
        }
    }
}


