package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * 主要负责维护一级缓存和二级缓存，并提供事务管理的相关操作，它会将数据库相关操作委托给 StatementHandler完成。
 */
public interface Executor {

  // 空 ResultHandler 对象的枚举
  ResultHandler NO_RESULT_HANDLER = null;

  // 更新 or 插入 or 删除，由传入的 MappedStatement 的 SQL 所决定
  int update(MappedStatement ms, Object parameter) throws SQLException;

  // 数据查询操作，返回结果为列表形式 // 查询，带 ResultHandler + CacheKey + BoundSql
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
                    CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   * 执行查询操作 数据查询操作，返回结果为列表形式
   *
   * @param ms            映射语句对象
   * @param parameter     参数对象
   * @param rowBounds     翻页限制
   * @param resultHandler 结果处理器
   * @param <E>           输出结果类型
   * @return 查询结果
   * @throws SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler)
    throws SQLException;

  // 数据查询操作，返回结果为游标形式
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  // 清理缓存
  List<BatchResult> flushStatements() throws SQLException;

  // 提交事务
  void commit(boolean required) throws SQLException;

  // 回滚事务
  void rollback(boolean required) throws SQLException;

  // 创建当前查询的缓存键值
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  // 本地缓存是否有指定值
  boolean isCached(MappedStatement ms, CacheKey key);

  // 清理本地缓存
  void clearLocalCache();

  // 懒加载
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  // 获取事务
  Transaction getTransaction();

  // 关闭执行器
  void close(boolean forceRollback);

  // 判断执行器是否关闭
  boolean isClosed();

  // 设置执行器包装
  void setExecutorWrapper(Executor executor);

}
