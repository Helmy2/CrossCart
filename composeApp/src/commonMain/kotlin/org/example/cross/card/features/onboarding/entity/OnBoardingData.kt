package org.example.cross.card.features.onboarding.entity

import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_favourite_back
import crosscart.composeapp.generated.resources.ic_favourite_front
import crosscart.composeapp.generated.resources.ic_love_it_back
import crosscart.composeapp.generated.resources.ic_love_it_front
import crosscart.composeapp.generated.resources.ic_shopping_back
import crosscart.composeapp.generated.resources.ic_shopping_front
import crosscart.composeapp.generated.resources.ic_start_back
import crosscart.composeapp.generated.resources.ic_start_front
import crosscart.composeapp.generated.resources.ic_welcome_back
import crosscart.composeapp.generated.resources.ic_welcome_front
import org.jetbrains.compose.resources.DrawableResource

data class OnBoardingData(
    val title: String,
    val description: String,
    val frontImage: DrawableResource,
    val backImage: DrawableResource
)


val onBoardingPages = listOf(
    OnBoardingData(
        title = "Welcome to CrossCart!",
        description = "Your go-to shopping app across Android, iOS, and Desktop." +
                "\nDiscover a seamless and real-time shopping experience.",
        frontImage = Res.drawable.ic_welcome_front,
        backImage = Res.drawable.ic_welcome_back
    ),
    OnBoardingData(
        title = "Browse & Discover",
        description = "Find Anything You Need!" +
                "\nBrowse a wide range of products with smart filtering, sorting, and powerful search.",
        frontImage = Res.drawable.ic_shopping_front,
        backImage = Res.drawable.ic_shopping_back
    ),
    OnBoardingData(
        title = "Add to Favorites",
        description = "Save Your Favorites!" +
                "\nAdd items to your favorites for quick access.",
        frontImage = Res.drawable.ic_favourite_front,
        backImage = Res.drawable.ic_favourite_back
    ),
    OnBoardingData(
        title = "Cart & Checkout",
        description = "Manage Your Cart Effortlessly!" +
                "\nAdd items to your cart, review your order, and checkout with ease.",
        frontImage = Res.drawable.ic_love_it_front,
        backImage = Res.drawable.ic_love_it_back
    ),
    OnBoardingData(
        title = "Let’s Get Started!",
        description = "Start shopping now!" +
                "\nDiscover a world of possibilities with CrossCart.",
        frontImage = Res.drawable.ic_start_front,
        backImage = Res.drawable.ic_start_back
    )
)