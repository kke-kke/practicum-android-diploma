package ru.practicum.android.diploma.util

enum class Currency(val currency: String, val symbol: String) {
    // https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-dictionaries
    RUB("RUB", "₽"),
    RUR("RUR", "₽"),    // Российский рубль (RUR/RUB)
    BYR("BYR", "Br"),   // Белорусский рубль (BYR)
    USD("USD", "$"),    // Доллар (USD)
    EUR("EUR", "€"),    // Евро (EUR)
    KZT("KZT", "₸"),    // Казахстанский тенге (KZT)
    UAH("UAH", "₴"),    // Украинская гривна (UAH)
    AZN("AZN", "₼"),    // Азербайджанский манат (AZN)
    UZS("UZS", "Soʻm"), // Узбекский сум (UZS)
    GEL("GEL", "₾"),    // Грузинский лари (GEL)
    KGS("KGS", "с")     // Киргизский сом (KGS)
}
