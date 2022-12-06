package day06

import readInput

private const val PACKET_MARKER_SIZE = 4
private const val MESSAGE_MARKER_SIZE = 14

fun String.findMarker(markerSize: Int) = windowedSequence(markerSize).withIndex()
    .first { it.value.toSet().size == markerSize }
    .index + markerSize

fun main() {
    check(readInput("day06/test").first().findMarker(PACKET_MARKER_SIZE) == 10)
    println(readInput("day06/input").first().findMarker(PACKET_MARKER_SIZE))

    check(readInput("day06/test").first().findMarker(MESSAGE_MARKER_SIZE) == 29)
    println(readInput("day06/input").first().findMarker(MESSAGE_MARKER_SIZE))
}
