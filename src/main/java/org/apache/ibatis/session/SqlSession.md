# SqlSession: 作为  MyBatis 工作的主要顶层API，表示和数据库交互的会话，完成必要数据库增删改查功能；
SqlSession是一个接口类,类似公司前台,扮门面的作用,假如我是客户找你们公司的工程师，我只需要告诉前台的美女客服SqlSession我要什么信息，要什么东西，过段时间，她会将结果给我，在这个过程中，我作为用户所关心的是1.要给SqlSession什么信息（功能和参数）2.SqlSession会返回什么结果（result）

- 默认有2个实现类：DefaultSqlSession 和 SqlSessionManager