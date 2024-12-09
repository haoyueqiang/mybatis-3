/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * 枚举类，表示 JDBC 支持的不同事务隔离级别。
 * 这些隔离级别控制了一个事务对其他并发事务所做更改的可见性。
 * 枚举常量对应 {@link java.sql.Connection} 中定义的隔离级别。
 */
public enum TransactionIsolationLevel {

  /**
   * 无隔离级别。事务之间没有隔离，可能会导致脏读、不可重复读和幻读。
   */
  NONE(Connection.TRANSACTION_NONE),

  /**
   * 读已提交。确保没有脏读，即事务只能读取已提交的数据。
   * 这是最常见的隔离级别，事务间会有隔离，但仍然可能出现不可重复读的情况。
   */
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

  /**
   * 读未提交。允许脏读，即一个事务可以读取其他事务修改但尚未提交的数据。
   * 这种隔离级别通常不推荐使用，因为它可能读取到不一致的数据。
   */
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

  /**
   * 可重复读。确保事务内的查询结果不会发生变化，即使其他事务对数据进行了修改。
   * 这种隔离级别避免了脏读和不可重复读，但仍可能出现幻读。
   */
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

  /**
   * 序列化。事务完全隔离，确保事务之间不会相互干扰。
   * 这是最严格的隔离级别，但会影响系统性能，因为事务执行的顺序是串行的。
   */
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

  private final int level;

  /**
   * 构造函数，初始化事务隔离级别。
   * @param level 对应的 JDBC 事务隔离级别常量
   */
  TransactionIsolationLevel(int level) {
    this.level = level;
  }

  /**
   * 获取该隔离级别对应的整数值。
   * @return 事务隔离级别的整数值
   */
  public int getLevel() {
    return level;
  }
}
