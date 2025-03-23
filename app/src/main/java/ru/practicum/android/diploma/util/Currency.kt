package ru.practicum.android.diploma.util

enum class Currency(val currency: String, val symbol: String) {
    // Все валюты: https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-dictionaries
    RUB("RUB", "₽"),
    RUR("RUR", "₽"),
    BYR("BYR", "Br"),
    USD("USD", "$"),
    EUR("EUR", "€"),
    KZT("KZT", "₸"),
    UAH("UAH", "₴"),
    AZN("AZN", "₼"),
    UZS("UZS", "Soʻm"),
    GEL("GEL", "₾"),
    KGS("KGS", "с")
}
