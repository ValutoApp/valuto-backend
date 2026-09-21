package com.valutoapp.config

sealed class ConfigException(
    message: String,
) : RuntimeException(message)

class MissingPropertyException(
    propertyName: String,
) : ConfigException("Missing config property: $propertyName")

class InvalidPropertyException(
    propertyName: String,
    propertyValue: String,
) : ConfigException("Invalid value '$propertyValue' for property $propertyName")
