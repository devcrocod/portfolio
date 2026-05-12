package io.github.devcrocod.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import io.github.devcrocod.theme.CodePalette

private val KOTLIN_KEYWORDS = setOf(
    "fun", "val", "var", "class", "object", "interface", "suspend",
    "return", "if", "else", "when", "for", "while", "import", "package",
    "data", "enum", "sealed", "open", "abstract", "override", "private",
    "public", "internal", "by", "companion", "this", "null", "true", "false",
    "is", "as", "in",
)

private enum class TokenType { Plain, Comment, StringLit, Number, Keyword, Type, Ident, Punct }

private data class Token(val type: TokenType, val text: String)

private val PUNCT_CHARS =
    setOf('{', '}', '(', ')', '.', ',', ':', ';', '=', '<', '>', '[', ']', '+', '-', '*', '/', '!', '?', '&', '|', '@')

private fun isIdentStart(c: Char) = c.isLetter() || c == '_'
private fun isIdentCont(c: Char) = c.isLetterOrDigit() || c == '_'

private fun tokenizeLine(line: String): List<Token> {
    val out = mutableListOf<Token>()
    var i = 0
    while (i < line.length) {
        val c = line[i]
        // line comment
        if (c == '/' && i + 1 < line.length && line[i + 1] == '/') {
            out.add(Token(TokenType.Comment, line.substring(i)))
            return out
        }
        // string
        if (c == '"') {
            val sb = StringBuilder().append(c)
            var j = i + 1
            while (j < line.length) {
                val cj = line[j]
                sb.append(cj)
                if (cj == '\\' && j + 1 < line.length) {
                    sb.append(line[j + 1]); j += 2; continue
                }
                if (cj == '"') {
                    j++; break
                }
                j++
            }
            out.add(Token(TokenType.StringLit, sb.toString()))
            i = j; continue
        }
        // number
        if (c.isDigit()) {
            val sb = StringBuilder()
            while (i < line.length && (line[i].isDigit() || line[i] == '.')) {
                sb.append(line[i]); i++
            }
            out.add(Token(TokenType.Number, sb.toString()))
            continue
        }
        // identifier / keyword
        if (isIdentStart(c)) {
            val sb = StringBuilder()
            while (i < line.length && isIdentCont(line[i])) {
                sb.append(line[i]); i++
            }
            val w = sb.toString()
            val type = when {
                w in KOTLIN_KEYWORDS -> TokenType.Keyword
                w.first().isUpperCase() -> TokenType.Type
                else -> TokenType.Ident
            }
            out.add(Token(type, w))
            continue
        }
        // whitespace / punctuation run
        if (c.isWhitespace() || c in PUNCT_CHARS) {
            val sb = StringBuilder()
            while (i < line.length && (line[i].isWhitespace() || line[i] in PUNCT_CHARS)) {
                sb.append(line[i]); i++
            }
            out.add(Token(TokenType.Punct, sb.toString()))
            continue
        }
        // fallback single char
        out.add(Token(TokenType.Plain, c.toString()))
        i++
    }
    return out
}

private fun tokenize(source: String): List<List<Token>> =
    source.split("\n").map { tokenizeLine(it) }

fun kotlinTokens(snippet: String, palette: CodePalette): AnnotatedString = buildAnnotatedString {
    val lines = tokenize(snippet)
    lines.forEachIndexed { idx, line ->
        for (i in line.indices) {
            val t = line[i]
            val color: Color
            var style: FontStyle? = null
            when (t.type) {
                TokenType.Comment -> {
                    color = palette.comment; style = FontStyle.Italic
                }

                TokenType.StringLit -> color = palette.string
                TokenType.Number -> color = palette.number
                TokenType.Keyword -> color = palette.keyword
                TokenType.Type -> color = palette.type
                TokenType.Ident -> {
                    val next = line.getOrNull(i + 1)
                    color = if (next != null && next.type == TokenType.Punct && next.text.trimStart().startsWith("("))
                        palette.fn else palette.fg
                }

                TokenType.Punct -> color = palette.punct
                TokenType.Plain -> color = palette.fg
            }
            withStyle(SpanStyle(color = color, fontStyle = style)) {
                append(t.text)
            }
        }
        if (idx < lines.size - 1) append("\n")
    }
}
