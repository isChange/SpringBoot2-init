# Spring Boot 初始化项目模板

> 一个开箱即用的 Spring Boot 项目脚手架，集成了常用的技术栈和最佳实践，帮助快速启动新项目开发。

## 项目特性

- ✅ Spring Boot 2.7.6 + Java 8
- ✅ MyBatis-Plus ORM 框架 + 分页插件
- ✅ Sa-Token 权限认证框架
- ✅ Redis + Caffeine 两级缓存架构
- ✅ Knife4j (Swagger) API 文档自动生成
- ✅ MapStruct 对象转换
- ✅ 全局异常处理
- ✅ 统一响应格式封装
- ✅ AOP 权限拦截
- ✅ Hutool 工具库集成
- ✅ 逻辑删除支持
- ✅ 请求参数校验

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.7.6 | 核心框架 |
| MyBatis-Plus | 3.5.9 | ORM 框架 |
| Sa-Token | 1.39.0 | 权限认证 |
| Redis | - | 分布式缓存 + Session |
| Caffeine | - | 本地缓存 |
| Knife4j | 4.4.0 | API 文档 |
| MapStruct | 1.5.0 | 对象转换 |
| Lombok | - | 代码生成 |
| Hutool | 5.8.16 | 工具类库 |

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 3.0+

### 配置数据库

1. 创建数据库：
```sql
CREATE DATABASE your_database CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改配置文件 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
    username: root
    password: your_password
  redis:
    host: localhost
    port: 6379
    password: your_password
```

### 构建运行

```bash
# 清理构建（跳过测试）
mvn clean package -Dmaven.test.skip=true

# 运行应用
java -jar target/spring-boot-init-0.0.1-SNAPSHOT.jar

# 或直接使用 Maven 运行
mvn spring-boot:run
```

### 访问应用

- 应用地址: http://localhost:8080/api
- API 文档: http://localhost:8080/api/doc.html

## 项目结构

```
com.ly.yunpicture/
├── common/                    # 通用代码
│   ├── annotation/           # 自定义注解
│   │   └── AuthCheck.java   # 权限检查注解
│   ├── aop/                  # AOP 切面
│   │   └── AuthInterceptor.java  # 权限拦截器
│   ├── cache/                # 缓存实现
│   │   ├── CaffeineCache.java   # 本地缓存
│   │   └── RedisCache.java      # 分布式缓存
│   ├── config/               # 配置类
│   │   ├── CacheConfig.java     # 缓存配置
│   │   ├── MybatisPlusConfig.java  # MyBatis-Plus 配置
│   │   ├── RedisConfig.java     # Redis 配置
│   │   ├── SaTokenConfigure.java  # Sa-Token 配置
│   │   └── ...
│   ├── constant/             # 常量定义
│   │   ├── UserConstant.java
│   │   ├── RedisConstant.java
│   │   └── SysConstant.java
│   ├── enums/error/          # 错误枚举
│   │   ├── ErrorCode.java
│   │   ├── UserEnum.java
│   │   └── ...
│   ├── exception/            # 异常类
│   │   ├── BaseException.java
│   │   └── AssertException.java
│   ├── handler/              # 处理器
│   │   ├── GlobalExceptionHandler.java  # 全局异常处理
│   │   └── MyBatisPlusMetaObjectHandler.java  # 自动填充
│   ├── manager/auth/         # 认证管理
│   │   ├── StpKit.java      # Sa-Token 工具
│   │   ├── HttpRequestWrapperFilter.java  # 请求包装
│   │   └── ...
│   ├── model/                # 通用模型
│   │   ├── Result.java      # 统一响应对象
│   │   ├── PageRequest.java # 分页请求
│   │   └── DeleteRequest.java  # 删除请求
│   └── units/                # 工具类
│       ├── AssertUtil.java
│       ├── DateUtil.java
│       ├── PasswordUtil.java
│       └── ...
├── controller/               # 控制器层
│   └── UserController.java  # 用户控制器（示例）
├── service/                  # 服务层
│   ├── UserService.java
│   └── impl/
│       └── UserServiceImpl.java
├── mapper/                   # 数据访问层
│   └── UserMapper.java
└── domain/                   # 领域模型
    ├── entity/               # 实体类
    │   └── User.java        # 用户实体（示例）
    ├── dto/                  # 数据传输对象
    │   └── user/
    │       ├── UserLoginRequest.java
    │       ├── UserRegisterRequest.java
    │       └── ...
    ├── vo/                   # 视图对象
    │   └── user/
    │       └── SafeUserVO.java
    ├── enums/                # 枚举
    │   └── UserRoleEnum.java
    └── cover/                # 对象转换器
        └── UserCovert.java
```

## 核心功能说明

### 1. 统一响应格式

所有 API 响应使用 `Result<T>` 统一封装：

```java
{
  "code": 0,        // 状态码
  "message": "ok",  // 消息
  "data": { }       // 数据
}
```

### 2. 权限认证

使用 `@AuthCheck` 注解进行权限校验：

```java
@GetMapping("/admin/list")
@AuthCheck(mustRole = "admin")
public Result<List<User>> adminList() {
    // 仅管理员可访问
}
```

### 3. 全局异常处理

`GlobalExceptionHandler` 统一处理所有异常并格式化响应。

### 4. 两级缓存

- **L1 - Caffeine**: 本地内存缓存，快速访问
- **L2 - Redis**: 分布式缓存，跨实例共享

### 5. 逻辑删除

实体类添加 `isDelete` 字段即可启用逻辑删除：

```java
@TableLogic
private Integer isDelete;  // 0-未删除, 1-已删除
```

### 6. 自动填充时间戳

创建时间、更新时间自动填充，无需手动设置：

```java
@TableField(fill = FieldFill.INSERT)
private Date createTime;

@TableField(fill = FieldFill.INSERT_UPDATE)
private Date updateTime;
```

### 7. 对象转换

使用 MapStruct 进行对象转换，避免手动赋值：

```java
@Mapper(componentModel = "spring")
public interface UserCovert {
    UserCovert INSTANCE = Mappers.getMapper(UserCovert.class);

    SafeUserVO entityToVO(User user);
    User voToEntity(SafeUserVO vo);
}
```

## 开发指南

### 添加新的业务模块

1. **创建实体类** (`domain/entity/`)
2. **创建 Mapper** (`mapper/`)
3. **创建 Service** (`service/` 和 `service/impl/`)
4. **创建 Controller** (`controller/`)
5. **创建 DTO/VO** (`domain/dto/` 和 `domain/vo/`)
6. **创建转换器** (`domain/cover/`)

### 示例：添加用户模块

参考项目中已有的 `User` 相关代码作为示例。

## 注意事项

1. **数据库字段命名**: 配置了 `map-underscore-to-camel-case: false`，需要保持字段名一致
2. **密码安全**: 使用 `PasswordUtil` 进行密码加密
3. **参数校验**: DTO 使用 `@Validated` 注解进行参数校验
4. **Redis 配置**: 生产环境请修改 Redis 密码
5. **API 文档**: Knife4j 默认启用，生产环境建议关闭

## 常见问题

### 1. 如何修改端口？

修改 `application.yml` 中的 `server.port` 配置。

### 2. 如何禁用 API 文档？

在 `application.yml` 中设置：
```yaml
knife4j:
  enable: false
```

### 3. 如何添加新的缓存？

使用 `CaffeineCache` 或 `RedisCache` 工具类：

```java
@Autowired
private CaffeineCache caffeineCache;

// 设置缓存
caffeineCache.put("key", value);

// 获取缓存
Object value = caffeineCache.get("key");
```

## 许可证

本项目采用 MIT 许可证。

## 贡献

欢迎提交 Issue 和 Pull Request！