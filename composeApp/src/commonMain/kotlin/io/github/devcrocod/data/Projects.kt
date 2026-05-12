package io.github.devcrocod.data

import androidx.compose.ui.graphics.Color

enum class ProjectStatus { Stable, Experimental, Archived, Maintained, Active }

enum class ProjectRole(val display: String) {
    Author("Author"),
    CoAuthor("Co-author"),
    LeadMaintainer("Lead maintainer"),
    CoreContributor("Core contributor"),
    Contributor("Contributor"),
}

data class Project(
    val id: String,
    val repo: String,
    val href: String,
    val year: String,
    val title: String,
    val kind: String,
    val role: ProjectRole? = null,
    val stars: String? = null,
    val status: ProjectStatus? = null,
    val desc: String,
    val overview: String? = null,
    val accent: Color,
    val snippet: String,
    val tags: List<String> = listOf("Kotlin"),
    val num: String = "—",
    val coverLabel: String? = null,
)

val FEATURED: List<Project> = listOf(
    Project(
        id = "koog",
        repo = "JetBrains/koog",
        href = "https://github.com/JetBrains/koog",
        year = "2025",
        title = "Koog",
        kind = "Framework",
        role = ProjectRole.CoreContributor,
        stars = "4.2k",
        status = ProjectStatus.Active,
        desc = "A Kotlin Multiplatform framework for building LLM agents — graph-based workflows, parallel tool calls, persistence, and integrations with OpenAI, Anthropic, Google, and MCP.",
        overview = """Koog is an open-source JetBrains framework for building AI agents designed specifically for the JVM ecosystem. It provides a first-class development experience for both Kotlin and Java developers, featuring an idiomatic, type-safe Kotlin DSL and fluent builder-style Java APIs.

While Java developers can leverage the full power of Koog on the JVM using idiomatic APIs, Kotlin developers can also deploy agents across JS, WasmJS, Android, and iOS targets using Kotlin Multiplatform.""",
        accent = Color(0xFF7F52FF),
        snippet = """// JetBrains/koog
val agent = AIAgent(
    executor = simpleOpenAIExecutor(apiKey),
    systemPrompt = "Plan trips for the user.",
    llmModel = OpenAIModels.Chat.GPT4o
)
agent.run("Plan a 3-day trip to Lisbon")""",
        tags = listOf("Kotlin", "Multiplatform", "Agents", "LLM"),
        num = "01",
    ),
    Project(
        id = "kotlin-sdk",
        repo = "modelcontextprotocol/kotlin-sdk",
        href = "https://github.com/modelcontextprotocol/kotlin-sdk",
        year = "2024",
        title = "Kotlin SDK for MCP",
        kind = "SDK",
        role = ProjectRole.LeadMaintainer,
        stars = "1.4k",
        status = ProjectStatus.Active,
        desc = "The official Kotlin Multiplatform SDK for the Model Context Protocol — build MCP clients and servers across JVM, Native, JS, and Wasm with coroutine-friendly transports.",
        overview = """The official Kotlin SDK for the Model Context Protocol — a standardized way for applications to give LLMs context from external systems. Built on Kotlin Multiplatform, it lets you implement MCP clients and servers from a single codebase that targets JVM, Native, JS, and Wasm, with coroutine-friendly APIs and pluggable transports including stdio, Streamable HTTP, SSE, and WebSocket.

Beyond the wire protocol, the SDK ships first-class building blocks for the MCP primitives: prompts, resources, tools, sampling, completions, logging, and pagination. Server-side helpers integrate with Ktor for hosting, and the same shared APIs work for clients embedded in editors, IDEs, or backend services.""",
        accent = Color(0xFF7F52FF),
        snippet = """// modelcontextprotocol/kotlin-sdk
val client = Client(Implementation("example", "1.0.0"))
val transport = StreamableHttpClientTransport(httpClient, url)
client.connect(transport)
val tools = client.listTools().tools""",
        tags = listOf("Kotlin", "Multiplatform", "MCP", "SDK"),
        num = "02",
    ),
    Project(
        id = "multik",
        repo = "Kotlin/multik",
        href = "https://github.com/Kotlin/multik",
        year = "2023",
        title = "Multik",
        kind = "Library",
        role = ProjectRole.Author,
        stars = "723",
        status = ProjectStatus.Maintained,
        desc = "Multidimensional arrays for Kotlin — NumPy-shaped APIs across JVM, JS, native, and Wasm, with optional OpenBLAS acceleration where it matters.",
        overview = """Multik brings N-dimensional arrays to Kotlin with type-safe dimensions, math operations, linear algebra, and statistics — a familiar shape for anyone coming from NumPy, but with compile-time guarantees about array rank. It works across JVM, JS, WasmJS, iOS, and desktop native targets through Kotlin Multiplatform.

The library is split into focused modules: a pure-Kotlin implementation that runs everywhere, and an OpenBLAS-backed native engine for the JVM and desktop targets where raw throughput matters. The combined default artifact picks the best engine per platform, so user code stays the same whether you're prototyping in a notebook or running on a server.""",
        accent = Color(0xFF3D6FB8),
        snippet = """// Kotlin/multik
val a = mk.ndarray(mk[1, 2, 3, 4])
val b = a.reshape(2, 2)
val c = mk.linalg.dot(b, b.transpose())""",
        tags = listOf("Kotlin", "Multiplatform", "Data"),
        num = "03",
    ),
    Project(
        id = "kandy",
        repo = "Kotlin/kandy",
        href = "https://github.com/Kotlin/kandy",
        year = "2024",
        title = "Kandy",
        kind = "Library",
        role = ProjectRole.CoAuthor,
        stars = "743",
        status = ProjectStatus.Maintained,
        desc = "A Kotlin DSL for statistical visualization — grammar-of-graphics layered plots with Lets-Plot and ECharts engines, ready for Kotlin Notebook and the JVM.",
        overview = """Kandy is a plotting library for Kotlin built around a layered grammar-of-graphics DSL. The same idiomatic Kotlin code targets multiple engines — Lets-Plot for static and interactive HTML/Swing output, and ECharts for richer browser-based interactivity — so you can pick the rendering that fits the context without rewriting the plot.

It's designed to feel native in both notebooks and ordinary Kotlin projects: charts render inside Kotlin Notebook, Datalore, and Jupyter; export to SVG, PNG, HTML, JPG, and TIFF; and the API integrates cleanly with Kotlin DataFrame so a data-processing pipeline can flow straight into a typed, null-safe visualization.""",
        accent = Color(0xFF2E8B57),
        snippet = """// Kotlin/kandy
plot(data) {
    layout.title = "Mileage"
    points { x(mpg); y(hp) }
    line   { x(mpg); y(hp) }
}""",
        tags = listOf("Kotlin", "DataViz", "DSL"),
        num = "04",
    ),
    Project(
        id = "dataframe",
        repo = "Kotlin/dataframe",
        href = "https://github.com/Kotlin/dataframe",
        year = "2024",
        title = "DataFrame",
        kind = "Library",
        role = ProjectRole.Contributor,
        stars = "1.0k",
        status = ProjectStatus.Maintained,
        desc = "Structured data processing in Kotlin. Hierarchical, typed, and ergonomic — like pandas, but compile-time honest.",
        overview = """Kotlin DataFrame is a structured-data library that brings pandas-style ergonomics to the JVM without giving up Kotlin's static types. It models data as a hierarchy of columns — values, groups, and nested frames — and exposes a functional, immutable pipeline of transformations that read like natural language: filter, group, join, pivot, convert.

A compiler plugin generates extension properties on the fly so column access is type-safe and null-aware, and a Kotlin Notebook integration makes it equally comfortable for exploratory work. The library reads and writes JSON, CSV, Apache Arrow, and SQL, and pairs naturally with Kandy when the next step is a chart.""",
        accent = Color(0xFFE44857),
        snippet = """// Kotlin/dataframe
val df = DataFrame.read("trips.csv")
df.filter { city == "Berlin" }
  .groupBy { route }
  .mean { fare }""",
        tags = listOf("Kotlin", "Data", "Typed"),
        num = "05",
    ),
)

val OREMIF: List<Project> = listOf(
    Project(
        id = "kstats",
        repo = "Oremif/kstats",
        href = "https://github.com/Oremif/kstats",
        year = "2025",
        title = "kstats",
        kind = "Library",
        role = ProjectRole.Author,
        status = ProjectStatus.Active,
        desc = "Statistics for Kotlin Multiplatform — descriptive stats, 28 distributions, hypothesis tests, regression, and sampling across every Kotlin target.",
        overview = """kstats is a Kotlin Multiplatform statistics toolkit organized as focused, opt-in modules: descriptive stats and online aggregators in `kstats-core`, 28 probability distributions (18 continuous + 10 discrete), parametric and non-parametric hypothesis tests, correlation with simple linear regression, and sampling utilities like ranking, normalization, bootstrap, and weighted dice.

Pure Kotlin, no JVM-only dependencies — the same `data.describe()`, `tTest(...)`, or `NormalDistribution(...).pdf(x)` runs on JVM, Android, Apple, Linux, Windows, watchOS, tvOS, JS, and Wasm. Modules are versioned through a BOM so a project depends only on what it uses, and interactive Kotlin Notebooks plus JMH benchmarks against Apache Commons Math live alongside the source.""",
        accent = Color(0xFFFFC66D),
        snippet = """// Oremif/kstats
val data = doubleArrayOf(2.0, 4.0, 4.0, 5.0, 7.0, 9.0)
val s = data.describe()
s.mean              // 5.17
Normal(0.0, 1.0).pdf(0.5)""",
        tags = listOf("Kotlin", "Multiplatform", "Statistics"),
        num = "06",
    ),
    Project(
        id = "deepseek-kotlin",
        repo = "Oremif/deepseek-kotlin",
        href = "https://github.com/Oremif/deepseek-kotlin",
        year = "2025",
        title = "deepseek-kotlin",
        kind = "SDK",
        role = ProjectRole.Author,
        status = ProjectStatus.Maintained,
        desc = "Kotlin Multiplatform SDK for the DeepSeek API — coroutines, Flow-based streaming, typed errors, and automatic retries across JVM, Android, iOS, and browser.",
        overview = """A Kotlin Multiplatform SDK for the DeepSeek REST API — idiomatic, coroutine-first, built on Ktor. Every endpoint is `suspend`, streaming endpoints return `Flow<ChatCompletionChunk>`, and non-2xx responses are mapped to typed `DeepSeekException` subclasses you can catch by status code. The full API surface is covered: chat completions, the reasoning model with its separate `reasoningContent` field, Fill-In-the-Middle, JSON output mode, tool/function calling, model listing, and account balance.

A message-builder DSL keeps multi-turn conversations readable, and a layered configuration block lets you tune JSON, per-endpoint timeouts, logging predicates, or install additional Ktor plugins on top of the defaults (bearer auth, content negotiation, retries with exponential backoff that honor `Retry-After`). One SDK for JVM, Android, Apple, Linux, Windows, and Wasm.""",
        accent = Color(0xFF69C9FF),
        snippet = """// Oremif/deepseek-kotlin
val client = DeepSeekClient(apiKey)
val reply = client.chat("Explain coroutines briefly.")
println(reply.choices.first().message.content)""",
        tags = listOf("Kotlin", "Multiplatform", "LLM", "SDK"),
        num = "07",
    ),
)

val PETS: List<Project> = listOf(
    Project(
        id = "korro",
        repo = "devcrocod/korro",
        href = "https://github.com/devcrocod/korro",
        year = "2024",
        title = "korro",
        kind = "Gradle plugin",
        role = ProjectRole.Author,
        status = ProjectStatus.Maintained,
        desc = "A Gradle plugin that keeps code samples in your Markdown and MDX docs in sync with real, compiled Kotlin sources. No more rotting README snippets.",
        overview = """korro is a Gradle plugin that embeds Kotlin sample snippets into Markdown and MDX documentation. The snippets are ordinary functions in your Kotlin source tree, so they get compiled, type-checked, and tested like any other code — meanwhile `korro` rewrites the rendered docs and `korroCheck` fails CI the moment a sample drifts from what the docs claim it does.

Built on the Kotlin Analysis API (K2 standalone), the plugin runs in an isolated worker so it never collides with your project's own Kotlin version. Directives are HTML comments (`<!---FUN ...-->`) in `.md` files and JSX-expression comments (`{/*---FUN ...--*/}`) in `.mdx`, with `IMPORT`, `FUN`, `FUNS`, and `END` covering function bodies, `//SampleStart`/`//SampleEnd` regions, and glob-matched groups. Strict by default, cacheable, CI-safe.""",
        accent = Color(0xFFC711E1),
        snippet = """// devcrocod/korro
fun example() {
    //SampleStart
    println("Hello, Kotlin!")
    //SampleEnd
}
// gradle korroCheck → CI-safe docs""",
        tags = listOf("Kotlin", "Docs", "Gradle"),
        num = "08",
    ),
    Project(
        id = "octokod",
        repo = "devcrocod/octokod",
        href = "https://github.com/devcrocod/octokod",
        year = "2024",
        title = "octokod",
        kind = "Library",
        role = ProjectRole.Author,
        status = ProjectStatus.Experimental,
        desc = "A Kotlin Multiplatform client for the GitHub REST API — small, typed, and built around what I actually use day to day.",
        overview = """A Kotlin Multiplatform client for the GitHub REST API — small, typed, and shaped around the endpoints I reach for in practice instead of auto-generated from the full OpenAPI surface. The same `Octokod(token)` client targets JVM, Android, Apple, Linux, Windows, JS, and Wasm.

Still in active development — the API and scope will change as more endpoints get wrapped.""",
        accent = Color(0xFF7F52FF),
        snippet = """// devcrocod/octokod
val gh = Octokod(token)
val prs = gh.pulls("JetBrains", "koog")
    .list(state = OPEN)""",
        tags = listOf("Kotlin", "Multiplatform", "GitHub"),
        num = "09",
    ),
    Project(
        id = "simdjson-kotlin",
        repo = "devcrocod/simdjson-kotlin",
        href = "https://github.com/devcrocod/simdjson-kotlin",
        year = "2026",
        title = "simdjson-kotlin",
        kind = "Library",
        role = ProjectRole.Author,
        status = ProjectStatus.Experimental,
        desc = "Kotlin Multiplatform bindings for simdjson — SIMD-accelerated JSON parsing with a kotlinx.serialization adapter for the cases where the default parser is the bottleneck.",
        overview = """simdjson-kotlin brings simdjson — the SIMD-accelerated JSON parser — to Kotlin Multiplatform. The goal is a thin, idiomatic wrapper for native targets plus a kotlinx.serialization adapter, so it can be dropped into the spots where the default Kotlin JSON parser is the bottleneck without rewriting surrounding code.

Still in active development — currently focused on the native binding layer and the parse-then-deserialize path.""",
        accent = Color(0xFFE44857),
        snippet = """// devcrocod/simdjson-kotlin
val parser = SimdJson()
val doc = parser.parse(bytes)
val name = doc["user"]["name"].asString()""",
        tags = listOf("Kotlin", "JSON", "Native"),
        num = "10",
    ),
)

val PROJECTS: List<Project> = FEATURED + OREMIF + PETS

val HIGHLIGHTS: List<Project> = listOf("multik", "kotlin-sdk", "koog", "kstats")
    .mapNotNull(::findProject)

fun findProject(id: String): Project? = PROJECTS.firstOrNull { it.id == id }
