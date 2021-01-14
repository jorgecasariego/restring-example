package dev.b3nedikt.restring.example

data class Example(
    val type: String,
    val configCode: String,
    val category: String,
    val data: List<ExampleKey>
)

data class ExampleKey(
    val type: String,
    val key: String,
    val value: String?,
    val description: String,
    val values: List<ArrayValues>?
)

data class ArrayValues(
    val type: String,
    val key: String,
    val value: String
)