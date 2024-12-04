/*
 *    Copyright 2009-2023 the original author or authors.
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

package org.apache.ibatis.reflection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.util.MapUtil;

public class DefaultReflectorFactory implements ReflectorFactory {

  /**
   * 是否缓存 Reflector 对象
   */
  private boolean classCacheEnabled = true;

  /**
   * Reflector 的缓存映射
   * <p>
   * KEY：类
   * VALUE：Reflector 对象
   */
  private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

  public DefaultReflectorFactory() {
  }

  /**
   * @return 是否缓存 Reflector 对象
   */
  @Override
  public boolean isClassCacheEnabled() {
    return classCacheEnabled;
  }

  /**
   * 设置是否缓存 Reflector 对象
   *
   * @param classCacheEnabled 是否缓存
   */
  @Override
  public void setClassCacheEnabled(boolean classCacheEnabled) {
    this.classCacheEnabled = classCacheEnabled;
  }

  /**
   * 获取 Reflector 对象
   *
   * @param type 指定类
   * @return 对象
   */
  @Override
  public Reflector findForClass(Class<?> type) {
    if (classCacheEnabled) {
      // synchronized (type) removed see issue #461
      return MapUtil.computeIfAbsent(reflectorMap, type, Reflector::new);
    }
    return new Reflector(type);
  }

}
