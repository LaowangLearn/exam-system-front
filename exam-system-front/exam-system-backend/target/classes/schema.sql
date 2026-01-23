DROP TABLE IF EXISTS t_exam_question;
DROP TABLE IF EXISTS t_exam;
DROP TABLE IF EXISTS t_module;

CREATE TABLE t_module (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_name VARCHAR(100) NOT NULL,
    module_desc VARCHAR(500),
    module_path VARCHAR(200)
);

CREATE TABLE t_exam (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    create_user VARCHAR(50) NOT NULL,
    status INT DEFAULT 0 COMMENT '0-草稿，1-已提交',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE t_exam_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    question_content TEXT NOT NULL,
    option_a VARCHAR(500),
    option_b VARCHAR(500),
    option_c VARCHAR(500),
    option_d VARCHAR(500),
    correct_answer CHAR(1) NOT NULL,
    FOREIGN KEY (exam_id) REFERENCES t_exam(id) ON DELETE CASCADE
);

INSERT INTO t_module (module_name, module_desc, module_path) VALUES
('考试管理', '创建、编辑和管理考试', '/exams');
