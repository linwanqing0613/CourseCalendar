# 課程表管理系統

這是一個基於 Spring Boot 的課程表(以下簡稱課表)，
提供課程的新增、查詢、更新和刪除功能，
講師的新增、查詢、更新和刪除功能，
課表的新增、查詢、更新、刪除和查詢功能。

## 功能

- **課程管理**：新增、查詢、更新和刪除課程資訊。
- **講師管理**：新增、查詢、更新和刪除講師資訊。
- **課表管理**：新增、查詢、更新和刪除課表資訊。
- **條件查詢**：支持根據講師、課表和課表日期等條件進行查詢。

## 技術棧

- **後端技術**：
  - Spring Boot
  - Spring Data JPA
  - H2 Database
  - Maven
- **測試**：
  - Spring Test

## 數據庫設計
### 會員表

以下是 SQL 建表語句：

```sql
CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    hire_date DATE NOT NULL,
    subject VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE class_schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    course_id INT,
    teacher_id INT,
    schedule_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```
## Restful API 文檔
## 以下為講師範例
### 1. 新增講師

- **HTTP 方法**: `POST`
- **URL**: `/api/class-schedule`
- **RequestBody**:
    ```json
    {
        "className": "Math Class A",
        "courseId": 1,
        "teacherId": 1,
        "scheduleDate": "2024-11-06",
        "startTime": "09:00:00",
        "endTime": "10:30:00",
        "classroom": "Room 101"
    }
    ```

### 2. 查詢所有講師
- **HTTP 方法**: `GET`
- **URL**: `/api/class-schedule`


### 3. 根據 ID 獲取講師
- **HTTP 方法**: `GET`
- **URL**: `/api/class-schedule/{id}`

### 4. 更新講師
- **HTTP 方法**: `PUT`
- **URL**: `/api/class-schedule/{id}`
- **RequestBody**:
    ```json
    {
        "className": "Math Class A",
        "courseId": 1,
        "teacherId": 1,
        "scheduleDate": "2024-11-06",
        "startTime": "09:00:00",
        "endTime": "10:30:00",
        "classroom": "Room 101"
    }
    ```

### 5. 刪除講師
- **HTTP 方法**: `DELETE`
- **URL**: `/api/class-schedule/{id}`


### 6. 根據條件查詢課表
- **HTTP 方法**: `GET`
- **URL**: `/api/class-schedule/search`
    - teacherName (可選): 講師姓名
    - courseName (可選): 課表
    - scheduleDate (可選): 課表日期
    - **範例**
        - **查詢教師名為 "John Doe" 的課程排程**:`/search?teacherName=John%20Doe`
        - **查詢教師名為 "John Doe" 和日期為 2024 年 11 月 6 日的排程**:`/search?teacherName=John%20Doe&scheduleDate=2024-11-06`
        - **查詢特定日期的排程，不指定教師或課程**:`/search?scheduleDate=2024-11-06`