package de.larmic.rest.rest.dto

import java.util.*

class TweetDto(val id: String = UUID.randomUUID().toString(), val message: String)