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

package io.github.kezhenxu94.annotations

import kotlin.reflect.KClass
import org.yaml.snakeyaml.constructor.AbstractConstruct

/**
 * Declares a validator with tags in YAML, YAML rules with these tags can perform the validations that
 * are specified in the construct class, rules with prefixes can be used along with other tags, such as `!not.` prefix,
 * for more example, please refer to `io.github.kezhenxu94.validators.not.NotValidator`.
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class TagProcessor(
    /**
     * The tags that should be registered to the YAML parser.
     */
    val tags: Array<String> = [],
    /**
     * The prefixes that should be registered to the YAML parser.
     */
    val prefixes: Array<String> = [],
    /**
     * The constructor class that constructs a real validator from a node.
     */
    val construct: KClass<out AbstractConstruct>
)
