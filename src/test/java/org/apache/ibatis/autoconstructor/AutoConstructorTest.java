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
package org.apache.ibatis.autoconstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AutoConstructorTest {
  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create a SqlSessionFactory
    // 创建 SqlSessionFactory 对象, 基于 mybatis-config.xml

    // Resources.getResourceAsReader
    // 这段代码通过指定路径加载 XML 文件，并返回一个 Reader 对象。它的作用是将配置文件内容以字符流的形式提供给后续处理步骤。
    // 此时 reader 对象仅仅是文件内容的读取器。

    // SqlSessionFactoryBuilder.build(reader)
    // 这里是关键点。SqlSessionFactoryBuilder 的 build 方法会接收 reader，
    // 然后利用 MyBatis 的 XML 解析器（例如基于 DOM 或 SAX 的解析器）将配置文件中的 XML 内容解析为 Java 对象。
    // 具体包括解析数据库环境配置、映射文件配置等，并最终构建出 SqlSessionFactory。

    // 总结：reader 只是一个载体，用于读取 XML 配置文件的内容，
    // 它本身并不负责将 XML 转为 Java 对象。实际的 XML 解析和对象转换是在 SqlSessionFactoryBuilder.build() 中完成的。
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/autoconstructor/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    // 初始化数据到内存数据库，基于 CreateDB.sql SQL 文件。
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
        "org/apache/ibatis/autoconstructor/CreateDB.sql");
  }

  @Test
  void fullyPopulatedSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      final Object subject = mapper.getSubject(1);
      assertNotNull(subject);
    }
  }

  @Test
  void primitiveSubjects() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      assertThrows(PersistenceException.class, mapper::getSubjects);
    }
  }

  @Test
  void annotatedSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      verifySubjects(mapper.getAnnotatedSubjects());
    }
  }

  @Test
  void badMultipleAnnotatedSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      final PersistenceException ex = assertThrows(PersistenceException.class, mapper::getBadAnnotatedSubjects);
      final ExecutorException cause = (ExecutorException) ex.getCause();
      assertEquals("@AutomapConstructor should be used in only one constructor.", cause.getMessage());
    }
  }

  @Test
  void badSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      assertThrows(PersistenceException.class, mapper::getBadSubjects);
    }
  }

  @Test
  void extensiveSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      verifySubjects(mapper.getExtensiveSubjects());
    }
  }

  private void verifySubjects(final List<?> subjects) {
    assertNotNull(subjects);
    Assertions.assertThat(subjects.size()).isEqualTo(3);
  }
}
