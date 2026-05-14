# devcrocod portfolio

Personal site of [devcrocod](https://github.com/devcrocod). Built with Compose Multiplatform targeting Kotlin/Wasm.

Live: https://gorgulov.com

## Stack

- Kotlin Multiplatform (Wasm/JS target only)
- Compose Multiplatform + Material 3
- [Haze](https://github.com/chrisbanes/haze) for blur effects

## Run locally

Requires JDK 21.

```sh
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Then open the URL Gradle prints (usually http://localhost:8080).

## Build

```sh
./gradlew :composeApp:wasmJsBrowserDistribution
```

Output goes to `composeApp/build/dist/wasmJs/productionExecutable`.
