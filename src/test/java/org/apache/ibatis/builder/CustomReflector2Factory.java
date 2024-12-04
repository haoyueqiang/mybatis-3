// Copyright (C) 2024 Focus Media Holding Ltd. All Rights Reserved.

package org.apache.ibatis.builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;

/**
 * 自定义 CustomReflectorFactory 的实现步骤
 *
 *
 * 1. 实现 ReflectorFactory 接口：
 *
 * import org.apache.ibatis.reflection.Reflector;
 * import org.apache.ibatis.reflection.ReflectorFactory;
 *
 * import java.util.Map;
 * import java.util.concurrent.ConcurrentHashMap;
 *
 * public class CustomReflectorFactory implements ReflectorFactory {
 *     private final Map<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();
 *     private boolean classCacheEnabled = true;
 *
 *     @Override
 *     public boolean isClassCacheEnabled() {
 *         return classCacheEnabled;
 *     }
 *
 *     @Override
 *     public void setClassCacheEnabled(boolean classCacheEnabled) {
 *         this.classCacheEnabled = classCacheEnabled;
 *     }
 *
 *     @Override
 *     public Reflector findForClass(Class<?> type) {
 *         if (classCacheEnabled) {
 *             return reflectorMap.computeIfAbsent(type, Reflector::new);
 *         } else {
 *             return new Reflector(type);
 *         }
 *     }
 * }
 *
 *
 * 2. 在 MyBatis 配置中使用自定义工厂：
 * 在 SqlSessionFactory 或 XML 配置中指定你的自定义工厂：
 *
 * java
 * 复制代码
 * Configuration configuration = new Configuration();
 * configuration.setReflectorFactory(new CustomReflectorFactory());
 * 如果是 XML 配置，可以通过 <reflectorFactory> 标签配置。
 *
 * @author 郝跃强
 * @version V1.0
 * @since 2024-12-04 14:23
 **/
public class CustomReflector2Factory implements ReflectorFactory {

  private final Map<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

  /**
   * @return 是否缓存 Reflector 对象
   */
  private boolean classCacheEnabled = true;

  @Override
  public boolean isClassCacheEnabled() {
    return false;
  }

  @Override
  public void setClassCacheEnabled(boolean classCacheEnabled) {
    this.classCacheEnabled = classCacheEnabled;
  }

  @Override
  public Reflector findForClass(Class<?> type) {
    if (classCacheEnabled) {
      return reflectorMap.computeIfAbsent(type, Reflector::new);
    } else {
      return new Reflector(type);
    }
  }

  /**
   * 在 MyBatis 配置中使用自定义工厂：
   * 在 SqlSessionFactory 或 XML 配置中指定你的自定义工厂：
   *
   * java
   * 复制代码
   * Configuration configuration = new Configuration();
   * configuration.setReflectorFactory(new CustomReflector2Factory());
   */

  /**
   * 使用场景
   * 动态修改反射行为：
   *    例如，在运行时根据配置或条件切换不同的类元信息处理逻辑。
   *
   * 精细化缓存控制：
   *    如果你的项目有一些动态类加载或频繁的类变更操作，自定义 ReflectorFactory 可以确保缓存的一致性。
   *
   * 这在需要深度定制 MyBatis 的场景中非常有用，例如针对动态代理类或复杂的类层次结构的特殊处理。
   */
}
