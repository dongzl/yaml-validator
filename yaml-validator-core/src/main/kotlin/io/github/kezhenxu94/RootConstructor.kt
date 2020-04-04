/**
 * Copyright 2020 kezhenxu94
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

package io.github.kezhenxu94

import io.github.kezhenxu94.annotations.TagProcessor
import org.reflections.Reflections
import org.yaml.snakeyaml.constructor.Construct
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.nodes.Node
import org.yaml.snakeyaml.nodes.Tag

internal object RootConstructor : Constructor() {
  init {
    val tagClasses = Reflections(Package::class.java.`package`.name).getTypesAnnotatedWith(TagProcessor::class.java)

    tagClasses.forEach { klass ->
      klass.getAnnotation(TagProcessor::class.java).apply {
        val instance = construct.java.newInstance()

        tags.forEach {
          yamlConstructors[Tag(it)] = instance
        }

        prefixes.forEach {
          yamlMultiConstructors[it] = instance
        }
      }
    }
  }

  fun constructor(node: Node): Construct = getConstructor(node)

  val constructs: Map<Tag, Construct> get() = yamlConstructors
}
