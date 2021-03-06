/**
 * Copyright 2020 yaml-validator
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.kezhenxu94.validators.math

import io.github.kezhenxu94.core.Referable
import io.github.kezhenxu94.core.Validatable
import io.github.kezhenxu94.exceptions.ValidateException

internal abstract class MathValidator(protected val expected: Number = 0.0) : Validatable, Referable<Any> {
    protected abstract val tag: String

    override var reference: Any? = null

    override fun validate(candidate: Any?) {
        val actual = when (candidate) {
            is Number -> candidate.toDouble()
            is String -> candidate.toDouble()
            else -> throw ValidateException("$tag validator cannot be applied to non-number values, actual: $candidate")
        }

        try {
            if (reference == null) {
                validateAnchor(actual)
            } else {
                validateAlias(actual)
            }
        } finally {
            reference = candidate
        }
    }

    protected abstract fun validateAnchor(anchor: Number)

    protected abstract fun validateAlias(alias: Number)
}
