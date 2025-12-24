# online-exam
在线考试系统 - 期末大作业

## 技术栈
- Java + Spring Boot
- Maven 构建
- HTML + Thymeleaf 模板

## 如何运行
1. 克隆项目
   ```bash
   git clone https://github.com/pengyuhan829/online-exam.git
启动 MySQL 服务（确保 3306 端口可用）
<img width="770" height="339" alt="image" src="https://github.com/user-attachments/assets/c119559d-2b07-4736-a3fa-0a4912602305" />
<img width="1250" height="568" alt="image" src="https://github.com/user-attachments/assets/c80b13d5-3f95-4fa4-8318-b4739c617632" />
telnet localhost 3306

netstat -ano | findstr :3306    # Windows

本项目配置了自动初始化 SQL 脚本（见 src/main/resources/sql/），无需手动创建数据库

需要修改application.properties文件里的密码：spring.datasource.password=你的mysql密码

2.进入目录并运行
cd online-exam

mvn spring-boot:run  或者

.\mvnw spring-boot:run  
<img width="376" height="53" alt="image" src="https://github.com/user-attachments/assets/503bbe24-e479-43e6-8ed2-53e238fe8330" />


3.访问 http://localhost:8080//questions
