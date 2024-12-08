/*
 *    Copyright 2009-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.apache.ibatis.builder;

import org.apache.ibatis.reflection.DefaultReflectorFactory;

/**
 * CustomReflectorFactory 是一种自定义工厂，它通常与 MyBatis 框架一起使用，用来自定义或扩展 ReflectorFactory 的行为。
 * <p>
 * 在 MyBatis 中，ReflectorFactory 是一个接口，用于缓存类的元信息（例如字段、方法等反射数据）。
 * 默认实现是 DefaultReflectorFactory，但你可以通过实现 ReflectorFactory
 * 接口创建自己的 CustomReflectorFactory 来满足特定需求。
 * <p>
 *
 * <br/><br/>
 *
 * 为什么要自定义 ReflectorFactory？
 * 优化性能：
 * 默认的 DefaultReflectorFactory 通过缓存类的反射信息来提高性能。如果你的需求与默认实现不完全一致，例如需要动态更新缓存，可以自定义实现。
 * <p>
 * 特定行为扩展：
 * 你可能需要在反射过程中加入额外的逻辑，例如动态修改字段、方法访问权限，或者在某些特殊条件下调整反射操作。
 */
public class CustomReflectorFactory extends DefaultReflectorFactory {

}
