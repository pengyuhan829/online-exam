# 在线考试系统

一个基于Spring Boot + Thymeleaf + MySQL的在线考试管理系统，支持题库管理、试卷管理、在线考试、自动判分等功能。

## 📋 目录

- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [系统架构](#系统架构)
- [功能模块](#功能模块)
- [使用指南](#使用指南)
- [常见问题](#常见问题)
- [开发说明](#开发说明)

---

## ✨ 功能特性

### 用户角色
- **管理员**：拥有所有权限，管理用户、题库、试卷
- **教师**：管理题库、创建试卷、批改试卷、查看统计
- **学生**：参加考试、查看成绩、申诉复议

### 核心功能
- ✅ 用户登录注册、权限管理
- ✅ 题库管理（增删改查、搜索筛选）
- ✅ 试卷管理（固定卷、随机卷）
- ✅ 在线考试（倒计时、防作弊、自动保存）
- ✅ 自动判分（客观题）
- ✅ 成绩查询、考试记录
- 🚧 主观题批改（待开发）
- 🚧 成绩统计分析（待开发）
- 🚧 申诉复议功能（待开发）

---

## 🛠 技术栈

### 后端
- **框架**：Spring Boot 3.5.9
- **安全**：Spring Security
- **ORM**：Spring Data JPA + Hibernate
- **数据库**：MySQL 8.0+
- **模板引擎**：Thymeleaf

### 前端
- **UI框架**：Bootstrap 5.3.0
- **图标**：Bootstrap Icons
- **JavaScript**：原生JS（无框架依赖）

### 开发工具
- **构建工具**：Maven
- **Java版本**：JDK 17
- **IDE推荐**：IntelliJ IDEA
- **数据库工具**：DataGrip / Navicat

---

## 🚀 快速开始

### 1. 环境要求

- JDK 17 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6+
- IDE（推荐IntelliJ IDEA）

### 2. 数据库配置

#### 步骤1：创建数据库
```sql
CREATE DATABASE IF NOT EXISTS online_exam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 步骤2：执行初始化脚本

**方式一：全新安装**
```bash
# 在MySQL客户端或DataGrip中执行
source src/main/resources/sql/init-db.sql
```

**方式二：已有数据库升级**
```bash
# 不会删除现有数据
source src/main/resources/sql/update-db.sql
```

#### 步骤3：配置数据库连接

编辑 `src/main/resources/application.properties`：

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/online_exam?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8
spring.datasource.username=你的数据库用户名
spring.datasource.password=你的数据库密码

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# 服务器配置
server.port=8081
```

### 3. 启动应用

#### Windows系统
```bash
mvnw.cmd spring-boot:run
```

#### Linux/Mac系统
```bash
./mvnw spring-boot:run
```

### 4. 访问系统

打开浏览器访问：http://localhost:8081

### 5. 默认账号

所有账号的默认密码都是：`password123`

| 用户名 | 角色 | 说明 |
|--------|------|------|
| admin | 管理员 | 拥有所有权限 |
| teacher | 教师 | 管理题库、试卷、判分 |
| student | 学生 | 参加考试、查看成绩 |

---

## 🏗 系统架构

```
online-exam/
├── src/
│   ├── main/
│   │   ├── java/com/example/online_exam/
│   │   │   ├── config/              # 配置类
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/          # 控制器
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ExamController.java
│   │   │   │   ├── PaperController.java
│   │   │   │   ├── QuestionController.java
│   │   │   │   └── GradingController.java
│   │   │   ├── entity/              # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── Question.java
│   │   │   │   ├── Paper.java
│   │   │   │   ├── ExamRecord.java
│   │   │   │   └── ...
│   │   │   ├── repository/          # 数据访问层
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── QuestionRepository.java
│   │   │   │   └── ...
│   │   │   └── service/             # 业务逻辑层
│   │   │       └── UserDetailsServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── sql/
│   │       │   ├── init-db.sql      # 数据库初始化脚本
│   │       │   └── update-db.sql    # 数据库更新脚本
│   │       └── templates/           # Thymeleaf模板
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── exam/
│   │           ├── paper/
│   │           ├── question/
│   │           └── grading/
│   └── test/                        # 测试代码
├── pom.xml                          # Maven配置
└── README.md                        # 项目文档
```

---

## 📦 功能模块

### 1. 用户管理模块

#### 功能
- 用户登录/注册
- 角色权限控制（管理员、教师、学生）
- 密码加密存储（BCrypt）

#### 路由
- `GET /login` - 登录页面
- `POST /login` - 登录处理
- `GET /register` - 注册页面
- `POST /register` - 注册处理
- `GET /logout` - 退出登录

---

### 2. 题库管理模块

#### 功能
- 题目增删改查
- 支持题型：单选题、多选题、填空题、主观题
- 题目搜索和筛选（按题型、难度、科目）
- 题目难度分级（1-5级）

#### 路由
- `GET /questions` - 题库列表
- `GET /questions/add` - 添加题目页面
- `POST /questions/add` - 保存题目
- `GET /questions/edit/{id}` - 编辑题目页面
- `POST /questions/edit/{id}` - 更新题目
- `GET /questions/delete/{id}` - 删除题目

#### 题目数据结构
```java
Question {
    Long id;                    // 题目ID
    String type;                // 题型：SINGLE/MULTIPLE/FILL/SUBJECTIVE
    String content;             // 题目内容
    String options;             // 选项（JSON格式）
    String answer;              // 答案
    Integer difficulty;         // 难度（1-5）
    Long subjectId;            // 科目ID
    Long knowledgePointId;     // 知识点ID
}
```

---

### 3. 试卷管理模块

#### 功能
- 创建试卷（固定卷、随机卷）
- 为试卷添加题目
- 设置试卷总分、时长
- 启用/禁用试卷

#### 路由
- `GET /paper` - 试卷列表
- `GET /paper/create` - 创建试卷页面
- `POST /paper/create` - 保存试卷
- `GET /paper/{id}` - 试卷详情
- `GET /paper/{id}/add-questions` - 添加题目页面
- `POST /paper/{id}/add-questions` - 保存题目到试卷

#### 试卷数据结构
```java
Paper {
    Long id;                    // 试卷ID
    String title;               // 试卷标题
    String description;         // 试卷描述
    Integer totalScore;         // 总分
    Integer duration;           // 时长（分钟）
    Boolean isActive;           // 是否启用
    LocalDateTime createdTime;  // 创建时间
}
```

---

### 4. 在线考试模块

#### 功能
- 查看可用考试列表
- 开始考试（倒计时）
- 答题（支持所有题型）
- 自动保存答案（本地存储）
- 防作弊检测（切屏警告）
- 提交试卷

#### 路由
- `GET /exam/list` - 考试列表
- `GET /exam/start/{paperId}` - 开始考试
- `POST /exam/submit/{paperId}` - 提交试卷
- `GET /exam/result` - 考试结果
- `GET /exam/records` - 考试记录

#### 考试特性
- ⏱️ 倒计时功能（时间到自动提交）
- 💾 自动保存答案（每30秒）
- 🔒 防作弊检测（切屏警告）
- 🔀 题目和选项随机打乱（防作弊）
- ⚠️ 离开页面警告

---

### 5. 成绩管理模块

#### 功能
- 查看考试记录
- 查看成绩详情
- 自动判分（客观题）
- 主观题批改（待开发）

#### 路由
- `GET /grading/dashboard` - 判分仪表盘
- `GET /grading/auto-grade` - 自动判分
- `GET /grading/report` - 成绩报告

---

## 📖 使用指南

### 教师端操作流程

#### 1. 创建题目
1. 登录教师账号
2. 点击"题库管理"
3. 点击"添加题目"
4. 填写题目信息：
   - **题目内容**：输入题目描述
   - **题型**：选择单选/多选/填空/主观
   - **难度**：选择1-5级
   - **选项**（选择题）：JSON格式，例如：
     ```json
     ["A.选项1","B.选项2","C.选项3","D.选项4"]
     ```
   - **答案**：填写正确答案
5. 点击"保存题目"

#### 2. 创建试卷
1. 点击"试卷管理"
2. 点击"创建试卷"
3. 填写试卷信息：
   - 标题：例如"期末考试"
   - 描述：例如"2026年Java期末考试"
   - 总分：例如100
   - 时长：例如120（分钟）
4. 点击"保存"

#### 3. 为试卷添加题目
1. 在试卷列表中点击"查看"
2. 点击"添加题目"
3. 选择要添加的题目（可多选）
4. 点击"添加到试卷"
5. 系统会自动分配分值

#### 4. 启用试卷
1. 在试卷详情页确认题目无误
2. 确保试卷状态为"启用"
3. 学生即可看到并参加考试

---

### 学生端操作流程

#### 1. 查看考试列表
1. 登录学生账号
2. 点击"参加考试"
3. 查看可用的考试列表

#### 2. 参加考试
1. 点击"开始考试"按钮
2. 阅读试卷信息（总分、时长）
3. 开始答题：
   - **单选题**：点击选项单选
   - **多选题**：勾选多个选项
   - **填空题**：在输入框中填写答案
   - **主观题**：在文本域中输入答案
4. 系统会自动保存答案（每30秒）
5. 完成后点击"提交试卷"

#### 3. 查看成绩
1. 提交后自动跳转到结果页面
2. 点击"考试记录"查看历史成绩
3. 查看详细的答题情况

---

## ❓ 常见问题

### Q1: 启动时报错 "Access denied for user"
**A:** 检查 `application.properties` 中的数据库用户名和密码是否正确。

### Q2: 页面显示404
**A:** 确保应用已完全启动，查看控制台是否有错误信息。

### Q3: 创建题目失败
**A:** 
1. 确保已执行数据库初始化脚本
2. 检查 `question` 表的字段类型是否正确（content和options应为TEXT类型）
3. 查看IDEA控制台的错误日志

### Q4: 添加题目到试卷时报错
**A:** 
1. 确保 `paper_question` 表存在
2. 检查是否至少选择了一道题目
3. 重启应用并清除浏览器缓存

### Q5: 考试页面只显示一道题
**A:** 
1. 检查数据库中 `paper_question` 表的记录数
2. 查看IDEA控制台的日志，确认题目加载数量
3. 检查题目的 `options` 字段格式是否正确

### Q6: 选项格式错误
**A:** 选项必须是JSON数组格式，例如：
```json
["A.选项1","B.选项2","C.选项3","D.选项4"]
```
注意：
- 必须有方括号 `[]`
- 每个选项必须用双引号 `"` 包裹
- 选项之间用逗号 `,` 分隔（逗号后不要有空格）

### Q7: 如何修改端口
**A:** 在 `application.properties` 中修改：
```properties
server.port=8082
```

### Q8: 忘记密码怎么办
**A:** 在数据库中直接修改：
```sql
-- 重置为 password123
UPDATE users SET password = '$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAtepbyJscDnZYib8YiQB3GRB2WC' 
WHERE username = '你的用户名';
```

---

## 🔧 开发说明

### 数据库表结构

#### users - 用户表
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) DEFAULT 'STUDENT',
  real_name VARCHAR(100),
  email VARCHAR(100),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### question - 题目表
```sql
CREATE TABLE question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(50) DEFAULT 'SINGLE',
  content TEXT,
  options TEXT,
  answer VARCHAR(500),
  difficulty INT DEFAULT 1,
  subject_id BIGINT NOT NULL DEFAULT 1,
  knowledge_point_id BIGINT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### paper - 试卷表
```sql
CREATE TABLE paper (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  total_score INT DEFAULT 100,
  duration INT DEFAULT 120,
  is_active TINYINT(1) DEFAULT 1,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  creator_id BIGINT
);
```

#### paper_question - 试卷题目关联表
```sql
CREATE TABLE paper_question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paper_id BIGINT NOT NULL,
  question_id BIGINT NOT NULL,
  order_num INT DEFAULT 1,
  score INT DEFAULT 5,
  FOREIGN KEY (paper_id) REFERENCES paper(id) ON DELETE CASCADE,
  FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);
```

#### exam_record - 考试记录表
```sql
CREATE TABLE exam_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paper_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL DEFAULT 1,
  start_time DATETIME,
  end_time DATETIME,
  score INT DEFAULT 0,
  status VARCHAR(20) DEFAULT 'ONGOING',
  answers TEXT
);
```

---

### 添加新功能

#### 1. 添加新的控制器
```java
@Controller
@RequestMapping("/your-module")
public class YourController {
    // 你的代码
}
```

#### 2. 添加新的实体类
```java
@Entity
@Table(name = "your_table")
@Data
public class YourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 其他字段
}
```

#### 3. 添加新的Repository
```java
public interface YourRepository extends JpaRepository<YourEntity, Long> {
    // 自定义查询方法
}
```

#### 4. 添加新的模板页面
在 `src/main/resources/templates/` 目录下创建HTML文件。

---

### 代码规范

- 使用Lombok简化代码（@Data, @RequiredArgsConstructor等）
- 控制器方法返回视图名称或重定向URL
- 使用RESTful风格的URL设计
- 异常处理使用try-catch或全局异常处理器
- 数据库操作使用JPA Repository
- 前端使用Bootstrap 5组件
- JavaScript代码放在页面底部

---

## 📝 待开发功能

### 高优先级
- [ ] 实现考试次数限制
- [ ] 实现完整的自动判分功能
- [ ] 实现考试记录保存和查询
- [ ] 添加考试倒计时自动提交

### 中优先级
- [ ] 题目分页功能
- [ ] 试卷预览功能
- [ ] 主观题批改功能
- [ ] 成绩统计和分析
- [ ] 导出成绩报表

### 低优先级
- [ ] 题目导入导出（Excel）
- [ ] 富文本编辑器
- [ ] 图片上传功能
- [ ] 考试防作弊增强
- [ ] 移动端适配

---

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

---

## 👥 贡献

欢迎提交Issue和Pull Request！

---

## 📧 联系方式

如有问题或建议，请通过以下方式联系：

- 提交Issue：[GitHub Issues](https://github.com/your-repo/issues)
- 邮箱：your-email@example.com

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Bootstrap](https://getbootstrap.com/)
- [MySQL](https://www.mysql.com/)

---

**最后更新时间：2026年1月**
