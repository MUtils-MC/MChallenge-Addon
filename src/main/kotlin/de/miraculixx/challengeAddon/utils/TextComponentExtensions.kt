package de.miraculixx.challengeAddon.utils

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

val prefix = cmp("MAddon", NamedTextColor.BLUE) + cmp(" >>", NamedTextColor.DARK_GRAY) + cmp(" ")

fun Component.decorate(bold: Boolean? = null, italic: Boolean? = null, strikethrough: Boolean? = null, underlined: Boolean? = null): Component {
    var finalComponent = this
    if (bold != null) finalComponent = finalComponent.decoration(TextDecoration.BOLD, bold)
    if (italic != null) finalComponent = finalComponent.decoration(TextDecoration.ITALIC, italic)
    if (strikethrough != null) finalComponent = finalComponent.decoration(TextDecoration.STRIKETHROUGH, strikethrough)
    if (underlined != null) finalComponent = finalComponent.decoration(TextDecoration.UNDERLINED, underlined)
    return finalComponent
}

fun Component.lore(): Component {
    return decoration(TextDecoration.ITALIC, false)
}

fun emptyComponent(): Component {
    return Component.text(" ")
}

fun cmp(text: String, color: TextColor = NamedTextColor.GRAY, bold: Boolean = false, italic: Boolean = false, strikethrough: Boolean = false, underlined: Boolean = false): Component {
    return Component.text(text).color(color)
        .decorations(
            mapOf(
                TextDecoration.BOLD to TextDecoration.State.byBoolean(bold),
                TextDecoration.ITALIC to TextDecoration.State.byBoolean(italic),
                TextDecoration.STRIKETHROUGH to TextDecoration.State.byBoolean(strikethrough),
                TextDecoration.UNDERLINED to TextDecoration.State.byBoolean(underlined)
            )
        )
}

fun Component.addHover(display: Component): Component {
    return hoverEvent(asHoverEvent().value(display))
}

fun commandSuggestions(prefix: String, vararg commands: String): Component {
    val base = cmp("")
    commands.forEachIndexed { index, s ->
        base + cmp(s, NamedTextColor.BLUE)
            .clickEvent(ClickEvent.suggestCommand("$prefix $s"))
            .hoverEvent(HoverEvent.showText(cmp("Click to run command\n") + cmp("$prefix $s", NamedTextColor.BLUE)))
        if (index != commands.size - 1) base + cmp(" - ")
    }
    return base
}

operator fun Component.plus(other: Component): Component {
    return append(other)
}

fun Audience.title(main: Component, sub: Component, fadeIn: Duration = Duration.ZERO, stay: Duration = 5.seconds, fadeOut: Duration = Duration.ZERO) {
    showTitle(Title.title(main, sub, Title.Times.times(fadeIn.toJavaDuration(), stay.toJavaDuration(), fadeOut.toJavaDuration())))
}