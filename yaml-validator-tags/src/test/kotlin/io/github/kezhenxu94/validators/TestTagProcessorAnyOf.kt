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

package io.github.kezhenxu94.validators

import io.github.kezhenxu94.YamlValidator
import io.github.kezhenxu94.exceptions.ValidateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.yaml.snakeyaml.Yaml

@Execution(ExecutionMode.CONCURRENT)
internal class TestTagProcessorAnyOf {
    @Test
    internal fun `should pass when any of`() {
        val toValidate1 = Yaml().loadAs(
            """
            grades:
              - subject: math
                grade: 61
              - subject: English
                grade: 89 
            """.trimIndent(),
            Map::class.java
        )
        YamlValidator.from(yamlInputStream)
            .ignoreMissing()
            .build()
            .validate(toValidate1)

        val toValidate2 = Yaml().loadAs(
            """
            grades:
              - subject: math
                grade: 59
              - subject: English
                grade: 91
            """.trimIndent(),
            Map::class.java
        )
        YamlValidator.from(yamlInputStream)
            .ignoreMissing()
            .build()
            .validate(toValidate2)
    }

    @Test
    internal fun `should pass when simple any of`() {
        YamlValidator.from(
            """
            simple-key: !any
              - abc
            """.trimIndent()
        )
            .build()
            .validate(
                """
                simple-key:
                  - def
                  - abc
                """.trimIndent()
            )
    }

    @Test
    internal fun `should pass when any of is the top level section`() {
        val validator = YamlValidator.from(
            """
            !anyOf
            - subject: !nn
              grade: !gt 60
        """.trimIndent()
        ).ignoreMissing().build()

        validator.validate(
            Yaml().load(
                """
                - subject: math
                  grade: 59
                - subject: English
                  grade: 89 
                """.trimIndent()
            )
        )

        assertThrows<ValidateException> {
            validator.validate(
                Yaml().load(
                    """
                    - subject: math
                      grade: 59
                    - subject: English
                      grade: 59
                    """.trimIndent()
                )
            )
        }
    }

    @Test
    internal fun `should fail when none of`() {
        assertThrows<ValidateException> {
            val toValidate = Yaml().loadAs(
                """
                grades:
                  - name: math
                    grade: 59
                  - name: English
                    grade: 89
                """.trimIndent(),
                Map::class.java
            )
            YamlValidator.from(yamlInputStream)
                .ignoreMissing()
                .build()
                .validate(toValidate)
        }
    }

    @Test
    internal fun `should fail when any list size lt 1`() {
        assertThrows<IllegalArgumentException> {
            YamlValidator.from(
                """
                !any
                - whatever1
                - whatever2
                """.trimIndent()
            )
                .ignoreMissing()
                .build()
                .validate("- a")
        }
    }

    private companion object {
        private val yamlInputStream get() = TestTagProcessorAnyOf::class.java.getResourceAsStream("/anyOf.v.yaml")
    }
}
