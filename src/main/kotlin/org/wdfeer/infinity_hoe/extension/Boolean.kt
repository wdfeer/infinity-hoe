package org.wdfeer.infinity_hoe.extension

fun <T> Boolean.ifElse(yes: T, no: T): T = if (this) yes else no