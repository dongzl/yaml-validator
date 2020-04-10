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

package io.github.kezhenxu94.core

import io.github.kezhenxu94.exceptions.ValidateException

/**
 * Validatable is the main concept of YAML-Validator which validates the candidates based on the configured rules.
 */
interface Validatable {
    val context: Context

    /**
     * [validate]s the given object.
     *
     * @param candidate the object to validate
     */
    @Throws(ValidateException::class)
    fun validate(candidate: Any?)
}
