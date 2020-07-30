package com.nexters.travelbudget.data.remote.model.enums

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

/**
 * _______
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.29
 */
enum class RetrofitQualifiers : Qualifier {
    AUTH,
    DEFAULT;

    override val value: QualifierValue
        get() = when (this) {
            AUTH -> "auth"
            DEFAULT -> "default"
        }
}