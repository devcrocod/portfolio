@file:OptIn(ExperimentalWasmJsInterop::class)

package io.github.devcrocod.platform

import kotlinx.browser.window

actual fun openExternalUrl(url: String) {
    window.open(url, "_blank")
}

actual fun copyToClipboard(text: String) {
    runCatching { window.navigator.clipboard.writeText(text) }
}
