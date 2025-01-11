# mybatis-都有哪些-executor-执行器-它们之间的区别是什么?
- SimpleExecutor: 每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象
- ResultExecutor: 执行update或select，以SQL作为key查找Statement对象，存在就使用。不存在就创建，用完后，不关闭Statement对象，
  而是放置于Map<String,Statemen>内，供下一次使用。简而言之就是：重复只用Statement对象。
- BatchExecutor: 执行update（没有select，JDBC批处理不支持select），将所有SQL都添加到批处理中（addBatch()），
  等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕之后,
  等待逐一执行executeBatch()批处理。与JDBC批处理相同。
作用范围：Executor 的这些特点，都严格限制在SqlSession声明周期范围内。