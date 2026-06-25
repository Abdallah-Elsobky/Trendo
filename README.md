<h1 align="center">
  <br>
  📰 Trendo
  <br>
</h1>

<p align="center">
  <strong>A modern, offline-first news app built with Kotlin Multiplatform</strong><br>
  Sharing 100% of business logic and UI across Android &amp; iOS
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.4.0-7F52FF?style=flat-square&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Compose%20Multiplatform-1.11.1-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Platform-Android%20%7C%20iOS-brightgreen?style=flat-square" />
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-orange?style=flat-square" />
  <img src="https://img.shields.io/badge/Min%20SDK-24-blue?style=flat-square&logo=android" />
</p>

---

## ✨ Features

- 🔍 **Live Search** — Search trending stories in real-time with instant feedback
- 🗂️ **Category Filtering** — Browse articles by topic (Technology, Sports, Business, and more)
- 🌍 **Region Selector** — Filter news by country from a rich bottom sheet picker
- 💾 **Offline-First** — Articles are cached locally with Room so the app works without internet
- ❤️ **Favorites** — Bookmark any article and read it from the Saved tab anytime
- 📄 **Article Detail** — Full-screen bottom sheet with image, metadata, and a "Read Full Article" link
- 🔄 **Pull-to-Refresh** — Swipe down to fetch the latest headlines
- 🧼 **Empty & Error States** — Graceful UI for no results and network failures

---

## 🏛️ Architecture

Trendo follows **Clean Architecture** with a strict separation of concerns across three layers:

```
shared/
└── commonMain/
    ├── data/
    │   ├── remote/          # Ktor HTTP client, DTOs, API service, SafeApiCall wrapper
    │   ├── local/           # Room database, DAOs, entities
    │   ├── repository/      # NewsRepositoryImpl (offline-first strategy)
    │   └── mapper/          # DTO ↔ Entity ↔ Domain model mappers
    ├── domain/
    │   ├── model/           # Article, pure Kotlin data classes
    │   ├── repository/      # NewsRepository interface (contract)
    │   └── usecase/         # 13 single-responsibility use cases
    ├── presentation/
    │   ├── theme/           # TrendoTheme — Material 3 color scheme
    │   ├── navigation/      # NavigationController, Screen sealed interface
    │   ├── components/      # ArticleCard, SearchBar, EmptyState, ErrorState, BottomSheet, AsyncImage
    │   └── ui/
    │       ├── home/        # HomeScreen, HomeViewModel, HomeUiState, HomeUiEvent
    │       ├── favorite/    # FavoriteScreen, FavoriteViewModel, ...
    │       └── details/     # DetailsDialog, DetailsViewModel, ...
    └── di/                  # Koin modules (network, database, data, repository, usecase, presentation)
```

### Data Flow

```
UI (Composable)
    ↕  events / state
ViewModel (StateFlow)
    ↕  use cases
Repository Interface
    ↕
RepositoryImpl ──► Remote (Ktor) → cache in Room
              └──► Local (Room)  → emit via Flow
```

> The repository always reads from the **local Room database** and refreshes from the network in the background,
> giving an instant first render even with no connectivity.

---

## 🧩 Use Cases

| Use Case | Description |
|---|---|
| `GetNewsUseCase` | Observe paginated articles as a `Flow` |
| `RefreshNewsUseCase` | Force-fetch articles from the network |
| `SearchNewsUseCase` | Observe search results as a `Flow` |
| `RefreshSearchUseCase` | Fetch fresh search results from the API |
| `GetNewsByIdUseCase` | Observe a single article by ID |
| `GetFavoriteNewsUseCase` | Observe all bookmarked articles |
| `ToggleFavoriteUseCase` | Save or unsave an article |
| `GetCategoriesUseCase` | Observe available news categories |
| `RefreshCategoriesUseCase` | Sync categories from the remote |
| `GetRegionsUseCase` | Observe available region codes |
| `RefreshRegionsUseCase` | Sync regions from the remote |
| `ClearExpiredCacheUseCase` | Purge stale cache entries |
| `ClearCacheUseCase` | Wipe all cached data |

---

## 🛠️ Tech Stack

| Layer | Library | Version |
|---|---|---|
| **Language** | Kotlin Multiplatform | 2.4.0 |
| **UI** | Compose Multiplatform | 1.11.1 |
| **UI Components** | Material 3 | 1.11.0-alpha07 |
| **Networking** | Ktor Client | 3.2.1 |
| **Local DB** | Room (KMP) | 2.8.4 |
| **Dependency Injection** | Koin | 4.0.0 |
| **Serialization** | kotlinx.serialization | 1.9.0 |
| **Logging** | Kermit | 2.0.4 |
| **Build Config** | BuildKonfig | 0.21.2 |
| **Android HTTP Engine** | OkHttp | via Ktor |
| **iOS HTTP Engine** | Darwin | via Ktor |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Iguana** or later (with KMP plugin)
- Xcode **15+** (for iOS target)
- JDK **17+**
- A valid API key from [thenewsapi.com](https://www.thenewsapi.com)

### 1. Clone the Repository

```bash
git clone https://github.com/Abdallah-Elsobky/Trendo.git
cd Trendo
```

### 2. Add Your API Key

Create (or open) `local.properties` in the project root and add:

```properties
API_KEY=your_api_key_here
```

> The key is injected at build time via **BuildKonfig** and is never committed to source control.

### 3. Run on Android

```bash
./gradlew :androidApp:assembleDebug
```

Or use the **Run** widget in Android Studio and select the `androidApp` configuration.

### 4. Run on iOS

Open the `iosApp/` directory in Xcode and press **▶ Run**.
The shared KMP framework is compiled automatically as a Gradle task before the Xcode build.

---

## 📁 Project Structure

```
Trendo/
├── androidApp/              # Android application entry point
├── iosApp/                  # Xcode project & Swift entry point
├── shared/                  # Shared KMP module (business logic + UI)
│   ├── src/
│   │   ├── commonMain/      # 100% shared Kotlin + Compose code
│   │   ├── androidMain/     # Android-specific engine setup (OkHttp, Koin Android)
│   │   └── iosMain/         # iOS MainViewController (Darwin engine)
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml   # Version catalog
└── local.properties          # API key — not committed
```

---

## 🔀 Git Branch Strategy

| Branch | Purpose |
|---|---|
| `main` | Stable, production-ready releases |
| `feature/networking-remote-data-layer` | Ktor client + remote DTOs + SafeApiCall |
| `feature/offline-cache-room` | Room database + local data source |
| `chore/di-setup` | Koin dependency injection wiring |
| `feature/domain-layer` | Clean Architecture domain + presentation layer |

---

## 🧪 Running Tests

```bash
# Android unit tests
./gradlew :shared:testAndroidHostTest

# iOS simulator tests
./gradlew :shared:iosSimulatorArm64Test
```

---

## 🗺️ Roadmap

- [ ] Dark mode support
- [ ] Article sharing intent
- [ ] Push notifications for breaking news
- [ ] Pagination / infinite scroll
- [ ] Multiple language support (i18n)

---

## 📄 License

```
MIT License — feel free to use, fork, and contribute.
```

---

<p align="center">
  Built with ❤️ using <a href="https://www.jetbrains.com/kotlin-multiplatform/">Kotlin Multiplatform</a>
</p>
