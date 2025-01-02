# JNDI 模块
作用：

使用 JNDI（Java Naming and Directory Interface）来管理和获取数据源。
JNDI 通常用于企业级应用中，在应用服务器（如 Tomcat、WebLogic）中通过容器提供数据源的管理。
该模块通过 JNDI 名称查找并使用容器配置的数据源，不需要在 MyBatis 中手动配置数据库连接信息。


使用的设计模式：

代理模式：通过 JNDI 接口代理底层容器提供的数据源服务。
抽象工厂模式：定义了统一的数据源创建接口，具体实现通过 JNDI 提供。