## springboot整合模块学习
1. 本项目采用jdk11+springboot2.5.15进行开发

### 模块
#### 一. springboot-minio 
模块是springboot整合minio，实现文件上传，删除


#### 二. java8-function
模块是学习提升java8函数式编程

### 文档

- 项目文档导航: [docs/README.md](docs/README.md)
- Spring Boot + MinIO 文档: [docs/springboot-minio.md](docs/springboot-minio.md)
- Java 8 函数式示例 文档: [docs/java8-function.md](docs/java8-function.md)

### 快速开始

- 运行 springboot-minio:

```bash
mvn -pl springboot-minio -am spring-boot:run
```

- 运行某个 Java8 示例（修改成你要运行的类名）:

```bash
mvn -pl java8-function -q exec:java -Dexec.mainClass="com.asura.function.example.PredicateExample"
```