/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * Creates an {@link SqlSession} out of a connection or a DataSource
 *
 * @author Clinton Begin
 */
public interface SqlSessionFactory {

  /**
   * 创建一个新的 SqlSession 实例，这个实例是 MyBatis 中用于执行数据库操作的对象
   *
   * @return
   */
  SqlSession openSession();

  /**
   * 事务管理：配置为自动提交事务，或者手动控制事务的提交和回滚，通过openSession() 方法的重载版本来实现的，
   * 例如 openSession(boolean autoCommit)
   *
   * @param autoCommit 是否自动提交
   * @return
   */
  SqlSession openSession(boolean autoCommit);

  /**
   * 这个方法是 MyBatis 提供的一个重载版本，它允许你使用一个已经存在的 JDBC Connection 对象来创建一个 SqlSession 实例
   *
   * @param connection
   * @return
   */
  SqlSession openSession(Connection connection);

  /**
   * 表示 JDBC 支持的不同事务隔离级别。
   *
   * @param level 事务隔离级别
   * @return
   */
  SqlSession openSession(TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType);

  /**
   * @param execType
   * @param autoCommit 是否自动提交
   * @return
   */
  SqlSession openSession(ExecutorType execType, boolean autoCommit);

  /**
   * @param execType
   * @param level    事务隔离级别
   * @return
   */
  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType, Connection connection);

  Configuration getConfiguration();

}
