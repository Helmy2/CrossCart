# 🛒 CrossCart

**Android | iOS | Desktop**

A **multiplatform e-commerce app** built with Jetpack Compose Multiplatform and Supabase, showcasing
clean architecture, real-time sync, and cross-platform development.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)  
[![Supabase](https://img.shields.io/badge/Supabase-Powered-green.svg)](https://supabase.com)  
[![Platforms](https://img.shields.io/badge/Platforms-Android_iOS_Desktop-blue.svg)](https://kotlinlang.org/lp/multiplatform/)  
[![License](https://img.shields.io/badge/License-MIT-red.svg)](https://opensource.org/licenses/MIT)

---

### Core Functionality

- **Platform Support:** Android, iOS, and Desktop (fully supported)
- **Modern Architecture:** Built with Jetpack Compose for UI, leveraging Kotlin Multiplatform for
  shared logic, ensuring a consistent experience across platforms.
- **Data Persistence:** Utilizes SQLDelight for local data storage, enabling offline functionality
  and guest user features.
- **Real-time Synchronization:** Integrates with Supabase Realtime for live updates of data across
  devices, providing a dynamic and interactive experience.

### UI/UX

- **Theme:**
  - [x] Dark/Light mode switching.
- **Responsive Design:** UI adapts seamlessly to different screen sizes (mobile, tablet, desktop).
- **Navigation:** Intuitive navigation between screens using Jetpack Compose Navigation.
- **Animations:** Smooth and engaging UI transitions and animations.

### Authentication

- [x] Email/Password login.
- [ ] Google OAuth integration.
- [ ] Secure token management.

### Profile Management

- [ ] Profile picture upload and display.
- [ ] Real-time profile synchronization via Supabase Realtime.
- [ ] Guest profile persistence using SQLDelight.
- [ ] **Desktop Support:** UI and functionality optimized for desktop browsers.

### Product Catalog

- [x] Grid/List view options with lazy loading for optimal performance.
- [x] Powerful search functionality.
- [ ] Filtering by price, category, and ratings.
- [ ] Sorting options (newest, price, popularity).
- [ ] Detailed product view with image gallery (using Coil for image loading).
- [ ] Zoom functionality for product images.
- [ ] **Desktop Support:** Enhanced product browsing experience with keyboard navigation and
  larger image displays.

### Shopping Cart

- [ ] Real-time shopping cart synchronization via Supabase Realtime.
- [ ] Guest cart persistence using SQLDelight.
- [ ] Quantity adjustment and item removal.
- [ ] Clear cart functionality.
- [ ] Display of cart total.
- [ ] **Desktop Support:** Optimized for mouse and keyboard interaction.

### Checkout

- [ ] Address selection (with potential for address management).
- [ ] Secure payment processing integration (Stripe).
- [ ] Order confirmation with detailed summary.
- [ ] Order history tracking.
- [ ] **Desktop Support:** Streamlined checkout process for larger screens.

### Order Management

- [ ] Real-time order status updates via Supabase Realtime.
- [ ] Guest order persistence using SQLDelight.
- [ ] Order cancellation (with applicable conditions).
- [ ] Detailed order view with product information and tracking.
- [ ] **Desktop Support:** Comprehensive order management features, including printing or
  downloading order details.

### Future Enhancements

- [ ]  **Wishlist:**  Allow users to save products for later. Support for desktop layouts.
- [ ]  **Reviews and Ratings:** Implement a system for users to review and rate products.
- [ ]  **Promotional Offers:** Integrate support for discounts and promotions.
- [ ]  **Push Notifications:** Send notifications for order updates, and promotions (mobile
  platforms).
- [ ]  **Accessibility:**  Ensure the app is accessible to users with disabilities across all platforms.
- [ ]  **Testing:** Comprehensive unit and integration tests for all features.

---

## 🛠️ Technical Stack

- **Frontend**: Jetpack Compose Multiplatform
- **Backend**: Supabase (Auth, Database, Storage)
- **State Management**: Kotlin Flows + MVI
- **Local Caching**: SQLDelight
- **Dependency Injection**: Koin

## Run the application

### Add configuration

1. **Create `local.properties`:** In the root of your project, create a file named
   `local.properties`.
2. **Add Supabase Credentials:** Add your Supabase URL and anonymous key to `local.properties`:
   ```properties
   supabaseKey=your_actual_supabase_key
   supabaseUrl=your_actual_supabase_url
   ```
3. **Run the buildkonfig plugin:** Run the buildkonfig plugin to generate the `BuildConfig.kt` file:

- run `./gradlew generateBuildKonfig`

### Android

To run the application on android device/emulator:

- open project in Android Studio and run imported android run configuration

To build the application bundle:

- run `./gradlew :composeApp:assembleDebug`
- find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop

Run the desktop application: `./gradlew :composeApp:run`

### iOS

To run the application on iPhone device/simulator:

- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or
  use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

Built with ❤️ by **Helmy**

[LinkedIn](https://www.linkedin.com/in/mo-helmy/)

This README will be continuously updated as the app evolves.  Stay tuned for more features and improvements!
