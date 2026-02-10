-- OA系统数据库表结构

-- 部门表
CREATE TABLE IF NOT EXISTS department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '部门名称',
    parent_id BIGINT DEFAULT NULL COMMENT '上级部门ID',
    description VARCHAR(500) COMMENT '部门描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    department_id BIGINT COMMENT '所属部门ID',
    position VARCHAR(50) COMMENT '职位',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色：ADMIN, MANAGER, USER',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE, INACTIVE',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 公文表
CREATE TABLE IF NOT EXISTS document (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '公文标题',
    content TEXT NOT NULL COMMENT '公文内容',
    type VARCHAR(50) COMMENT '公文类型',
    status VARCHAR(50) DEFAULT 'DRAFT' COMMENT '状态：DRAFT, SUBMITTED, APPROVED, REJECTED',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES `user`(id)
);

-- 审批表
CREATE TABLE IF NOT EXISTS approval (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_id BIGINT COMMENT '关联公文ID',
    type VARCHAR(50) COMMENT '审批类型：DOCUMENT, LEAVE, REIMBURSEMENT',
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态：PENDING, APPROVED, REJECTED',
    applicant_id BIGINT NOT NULL COMMENT '申请人ID',
    approver_id BIGINT NOT NULL COMMENT '审批人ID',
    content TEXT COMMENT '审批内容',
    remark TEXT COMMENT '审批意见',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (document_id) REFERENCES document(id),
    FOREIGN KEY (applicant_id) REFERENCES `user`(id),
    FOREIGN KEY (approver_id) REFERENCES `user`(id)
);

-- 日程表
CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '日程标题',
    description TEXT COMMENT '日程描述',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    location VARCHAR(200) COMMENT '地点',
    type VARCHAR(50) COMMENT '日程类型：MEETING, TASK, EVENT',
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态：PENDING, COMPLETED, CANCELLED',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES `user`(id)
);

-- 日程参与人表
CREATE TABLE IF NOT EXISTS schedule_participant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    user_id BIGINT NOT NULL COMMENT '参与人ID',
    status VARCHAR(50) DEFAULT 'INVITED' COMMENT '状态：INVITED, ACCEPTED, DECLINED',
    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 初始数据

-- 插入默认部门
INSERT INTO department (name, description) VALUES ('总经办', '公司总部');
INSERT INTO department (name, description, parent_id) VALUES ('技术部', '负责技术开发', 1);
INSERT INTO department (name, description, parent_id) VALUES ('人事部', '负责人员管理', 1);
INSERT INTO department (name, description, parent_id) VALUES ('财务部', '负责财务管理', 1);

-- 插入默认用户（密码：123456）
INSERT INTO `user` (username, password, name, email, phone, department_id, position, role) 
VALUES ('admin', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '管理员', 'admin@example.com', '13800138000', 1, '总经理', 'ADMIN');

INSERT INTO `user` (username, password, name, email, phone, department_id, position, role) 
VALUES ('tech', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '技术经理', 'tech@example.com', '13800138001', 2, '技术经理', 'MANAGER');

INSERT INTO `user` (username, password, name, email, phone, department_id, position, role) 
VALUES ('user1', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '普通用户', 'user1@example.com', '13800138002', 2, '开发工程师', 'USER');
