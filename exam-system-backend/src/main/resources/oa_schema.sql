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

-- HRM人事管理系统表结构

-- 员工信息表（扩展user表）
ALTER TABLE `user` ADD COLUMN employee_id VARCHAR(50) COMMENT '员工编号';
ALTER TABLE `user` ADD COLUMN gender VARCHAR(10) COMMENT '性别';
ALTER TABLE `user` ADD COLUMN birthday DATE COMMENT '出生日期';
ALTER TABLE `user` ADD COLUMN join_date DATE COMMENT '入职日期';
ALTER TABLE `user` ADD COLUMN leave_date DATE COMMENT '离职日期';
ALTER TABLE `user` ADD COLUMN education VARCHAR(50) COMMENT '学历';
ALTER TABLE `user` ADD COLUMN specialty VARCHAR(100) COMMENT '专业';
ALTER TABLE `user` ADD COLUMN emergency_contact VARCHAR(100) COMMENT '紧急联系人';
ALTER TABLE `user` ADD COLUMN emergency_phone VARCHAR(20) COMMENT '紧急联系电话';

-- 招聘申请表
CREATE TABLE IF NOT EXISTS recruitment_application (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    age INT COMMENT '年龄',
    education VARCHAR(50) COMMENT '学历',
    specialty VARCHAR(100) COMMENT '专业',
    phone VARCHAR(20) NOT NULL COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    position_applied VARCHAR(100) NOT NULL COMMENT '应聘职位',
    work_experience TEXT COMMENT '工作经验',
    education_experience TEXT COMMENT '教育经历',
    status VARCHAR(50) DEFAULT 'APPLIED' COMMENT '状态：APPLIED, SCREENED, INTERVIEWED, OFFERED, HIRED, REJECTED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 培训计划表
CREATE TABLE IF NOT EXISTS training_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '培训标题',
    description TEXT COMMENT '培训描述',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE NOT NULL COMMENT '结束日期',
    location VARCHAR(200) COMMENT '培训地点',
    trainer VARCHAR(100) COMMENT '培训师',
    status VARCHAR(50) DEFAULT 'PLANNED' COMMENT '状态：PLANNED, IN_PROGRESS, COMPLETED, CANCELLED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 培训参与表
CREATE TABLE IF NOT EXISTS training_participant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    training_id BIGINT NOT NULL COMMENT '培训ID',
    user_id BIGINT NOT NULL COMMENT '参与人ID',
    attendance VARCHAR(50) DEFAULT 'REGISTERED' COMMENT '出勤状态：REGISTERED, ATTENDED, ABSENT',
    score DECIMAL(5,2) COMMENT '培训得分',
    feedback TEXT COMMENT '培训反馈',
    FOREIGN KEY (training_id) REFERENCES training_plan(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 绩效评估表
CREATE TABLE IF NOT EXISTS performance_evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '员工ID',
    evaluator_id BIGINT NOT NULL COMMENT '评估人ID',
    period VARCHAR(50) NOT NULL COMMENT '评估周期',
    score DECIMAL(5,2) COMMENT '评估得分',
    strengths TEXT COMMENT '优势',
    weaknesses TEXT COMMENT '不足',
    suggestions TEXT COMMENT '改进建议',
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态：PENDING, COMPLETED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (evaluator_id) REFERENCES `user`(id)
);

-- 薪酬表
CREATE TABLE IF NOT EXISTS salary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '员工ID',
    base_salary DECIMAL(10,2) NOT NULL COMMENT '基本工资',
    bonus DECIMAL(10,2) DEFAULT 0 COMMENT '奖金',
    allowance DECIMAL(10,2) DEFAULT 0 COMMENT '津贴',
    deduction DECIMAL(10,2) DEFAULT 0 COMMENT '扣除',
    total_salary DECIMAL(10,2) COMMENT '总工资',
    period VARCHAR(50) NOT NULL COMMENT '工资周期',
    payment_date DATE COMMENT '发放日期',
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态：PENDING, PAID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 考勤表
CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '员工ID',
    date DATE NOT NULL COMMENT '考勤日期',
    check_in_time TIME COMMENT '上班时间',
    check_out_time TIME COMMENT '下班时间',
    status VARCHAR(50) DEFAULT 'NORMAL' COMMENT '状态：NORMAL, LATE, EARLY_LEAVE, ABSENT, Overtime',
    hours_worked DECIMAL(5,2) COMMENT '工作时长',
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 财务管控系统表结构

-- 预算表
CREATE TABLE IF NOT EXISTS budget (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL COMMENT '预算名称',
    department_id BIGINT COMMENT '部门ID',
    amount DECIMAL(15,2) NOT NULL COMMENT '预算金额',
    used_amount DECIMAL(15,2) DEFAULT 0 COMMENT '已使用金额',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE NOT NULL COMMENT '结束日期',
    status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE, EXPIRED, CLOSED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 费用报销表
CREATE TABLE IF NOT EXISTS expense_reimbursement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '申请人ID',
    department_id BIGINT COMMENT '部门ID',
    amount DECIMAL(10,2) NOT NULL COMMENT '报销金额',
    purpose VARCHAR(500) NOT NULL COMMENT '报销用途',
    status VARCHAR(50) DEFAULT 'SUBMITTED' COMMENT '状态：SUBMITTED, APPROVED, REJECTED, PAID',
    submit_date DATE NOT NULL COMMENT '提交日期',
    approval_date DATE COMMENT '审批日期',
    payment_date DATE COMMENT '支付日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 报销明细表
CREATE TABLE IF NOT EXISTS expense_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reimbursement_id BIGINT NOT NULL COMMENT '报销单ID',
    item_name VARCHAR(200) NOT NULL COMMENT '费用项目',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    date DATE NOT NULL COMMENT '费用日期',
    description VARCHAR(500) COMMENT '描述',
    FOREIGN KEY (reimbursement_id) REFERENCES expense_reimbursement(id)
);

-- 凭证表
CREATE TABLE IF NOT EXISTS voucher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voucher_no VARCHAR(50) NOT NULL UNIQUE COMMENT '凭证编号',
    date DATE NOT NULL COMMENT '凭证日期',
    total_debit DECIMAL(15,2) NOT NULL COMMENT '借方合计',
    total_credit DECIMAL(15,2) NOT NULL COMMENT '贷方合计',
    status VARCHAR(50) DEFAULT 'DRAFT' COMMENT '状态：DRAFT, POSTED, CANCELLED',
    creator_id BIGINT NOT NULL COMMENT '制单人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES `user`(id)
);

-- 凭证明细表
CREATE TABLE IF NOT EXISTS voucher_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voucher_id BIGINT NOT NULL COMMENT '凭证ID',
    account_code VARCHAR(50) NOT NULL COMMENT '科目代码',
    account_name VARCHAR(100) NOT NULL COMMENT '科目名称',
    debit DECIMAL(15,2) DEFAULT 0 COMMENT '借方金额',
    credit DECIMAL(15,2) DEFAULT 0 COMMENT '贷方金额',
    description VARCHAR(500) COMMENT '摘要',
    FOREIGN KEY (voucher_id) REFERENCES voucher(id)
);

-- 银行账户表
CREATE TABLE IF NOT EXISTS bank_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_name VARCHAR(100) NOT NULL COMMENT '账户名称',
    account_number VARCHAR(50) NOT NULL UNIQUE COMMENT '账号',
    bank_name VARCHAR(100) NOT NULL COMMENT '银行名称',
    balance DECIMAL(15,2) DEFAULT 0 COMMENT '余额',
    status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE, INACTIVE',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 银行流水表
CREATE TABLE IF NOT EXISTS bank_transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL COMMENT '账户ID',
    transaction_date DATE NOT NULL COMMENT '交易日期',
    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    type VARCHAR(50) NOT NULL COMMENT '交易类型：INCOME, EXPENSE',
    description VARCHAR(500) COMMENT '交易描述',
    status VARCHAR(50) DEFAULT 'UNRECONCILED' COMMENT '状态：UNRECONCILED, RECONCILED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES bank_account(id)
);

-- 税务申报表
CREATE TABLE IF NOT EXISTS tax_declaration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    declaration_period VARCHAR(50) NOT NULL COMMENT '申报期间',
    tax_type VARCHAR(100) NOT NULL COMMENT '税种',
    tax_amount DECIMAL(15,2) NOT NULL COMMENT '税额',
    declaration_date DATE NOT NULL COMMENT '申报日期',
    payment_date DATE COMMENT '缴款日期',
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT '状态：PENDING, DECLARED, PAID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 电子档案系统表结构

-- 档案分类表
CREATE TABLE IF NOT EXISTS archive_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT NULL COMMENT '上级分类ID',
    code VARCHAR(50) NOT NULL COMMENT '分类编码',
    description VARCHAR(500) COMMENT '分类描述',
    retention_period INT COMMENT '保管期限（年）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 档案信息表
CREATE TABLE IF NOT EXISTS archive (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    archive_no VARCHAR(100) NOT NULL UNIQUE COMMENT '档案编号',
    title VARCHAR(200) NOT NULL COMMENT '档案标题',
    category_id BIGINT NOT NULL COMMENT '档案分类ID',
    content TEXT COMMENT '档案内容/描述',
    file_path VARCHAR(500) COMMENT '文件存储路径',
    file_name VARCHAR(200) COMMENT '原始文件名',
    file_size BIGINT COMMENT '文件大小',
    secret_level VARCHAR(50) DEFAULT 'NORMAL' COMMENT '密级：NORMAL, CONFIDENTIAL, SECRET, TOP_SECRET',
    status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE, ARCHIVED, DESTROYED',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    destroy_time DATETIME COMMENT '销毁时间',
    FOREIGN KEY (category_id) REFERENCES archive_category(id),
    FOREIGN KEY (creator_id) REFERENCES `user`(id)
);

-- 档案借阅表
CREATE TABLE IF NOT EXISTS archive_borrow (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    user_id BIGINT NOT NULL COMMENT '借阅人ID',
    borrow_date DATE NOT NULL COMMENT '借阅日期',
    return_date DATE COMMENT '归还日期',
    purpose VARCHAR(500) COMMENT '借阅用途',
    status VARCHAR(50) DEFAULT 'BORROWED' COMMENT '状态：BORROWED, RETURNED, OVERDUE',
    approver_id BIGINT COMMENT '审批人ID',
    approval_comment TEXT COMMENT '审批意见',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (archive_id) REFERENCES archive(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 档案操作日志表
CREATE TABLE IF NOT EXISTS archive_operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    user_id BIGINT NOT NULL COMMENT '操作用户ID',
    operation VARCHAR(100) NOT NULL COMMENT '操作类型',
    description VARCHAR(500) COMMENT '操作描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (archive_id) REFERENCES archive(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 企业沟通系统表结构

-- 内部消息表
CREATE TABLE IF NOT EXISTS internal_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL COMMENT '发送人ID',
    receiver_id BIGINT NOT NULL COMMENT '接收人ID',
    title VARCHAR(200) NOT NULL COMMENT '消息标题',
    content TEXT NOT NULL COMMENT '消息内容',
    type VARCHAR(50) DEFAULT 'PERSONAL' COMMENT '类型：PERSONAL, SYSTEM, BROADCAST',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    read_time DATETIME COMMENT '阅读时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES `user`(id),
    FOREIGN KEY (receiver_id) REFERENCES `user`(id)
);

-- 公告通知表
CREATE TABLE IF NOT EXISTS announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    type VARCHAR(50) DEFAULT 'NOTICE' COMMENT '类型：NOTICE, ANNOUNCEMENT, ALERT',
    priority VARCHAR(50) DEFAULT 'NORMAL' COMMENT '优先级：LOW, NORMAL, HIGH, URGENT',
    publisher_id BIGINT NOT NULL COMMENT '发布人ID',
    publish_time DATETIME NOT NULL COMMENT '发布时间',
    start_time DATETIME COMMENT '生效时间',
    end_time DATETIME COMMENT '失效时间',
    status VARCHAR(50) DEFAULT 'PUBLISHED' COMMENT '状态：DRAFT, PUBLISHED, ARCHIVED',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (publisher_id) REFERENCES `user`(id)
);

-- 公告阅读记录表
CREATE TABLE IF NOT EXISTS announcement_read (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    announcement_id BIGINT NOT NULL COMMENT '公告ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    read_time DATETIME NOT NULL COMMENT '阅读时间',
    FOREIGN KEY (announcement_id) REFERENCES announcement(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 讨论组表
CREATE TABLE IF NOT EXISTS discussion_group (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '讨论组名称',
    description VARCHAR(500) COMMENT '讨论组描述',
    owner_id BIGINT NOT NULL COMMENT '创建人ID',
    type VARCHAR(50) DEFAULT 'GROUP' COMMENT '类型：GROUP, CHANNEL',
    status VARCHAR(50) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE, INACTIVE, ARCHIVED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES `user`(id)
);

-- 讨论组成员表
CREATE TABLE IF NOT EXISTS group_member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL COMMENT '讨论组ID',
    user_id BIGINT NOT NULL COMMENT '成员ID',
    role VARCHAR(50) DEFAULT 'MEMBER' COMMENT '角色：OWNER, ADMIN, MEMBER',
    join_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES discussion_group(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 群聊消息表
CREATE TABLE IF NOT EXISTS group_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL COMMENT '讨论组ID',
    sender_id BIGINT NOT NULL COMMENT '发送人ID',
    content TEXT NOT NULL COMMENT '消息内容',
    type VARCHAR(50) DEFAULT 'TEXT' COMMENT '类型：TEXT, IMAGE, FILE',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES discussion_group(id),
    FOREIGN KEY (sender_id) REFERENCES `user`(id)
);

-- 固定资产系统表结构

-- 资产分类表
CREATE TABLE IF NOT EXISTS asset_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT NULL COMMENT '上级分类ID',
    code VARCHAR(50) NOT NULL COMMENT '分类编码',
    depreciation_rate DECIMAL(5,2) COMMENT '折旧率',
    description VARCHAR(500) COMMENT '分类描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 资产信息表
CREATE TABLE IF NOT EXISTS asset (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_no VARCHAR(100) NOT NULL UNIQUE COMMENT '资产编号',
    name VARCHAR(200) NOT NULL COMMENT '资产名称',
    category_id BIGINT NOT NULL COMMENT '资产分类ID',
    model VARCHAR(100) COMMENT '规格型号',
    serial_number VARCHAR(100) COMMENT '序列号',
    purchase_date DATE COMMENT '购置日期',
    purchase_price DECIMAL(15,2) COMMENT '购置价格',
    current_value DECIMAL(15,2) COMMENT '当前价值',
    useful_life INT COMMENT '使用年限（月）',
    status VARCHAR(50) DEFAULT 'IN_USE' COMMENT '状态：IN_USE, IDLE, MAINTENANCE, SCRAPPED',
    location VARCHAR(200) COMMENT '存放地点',
    custodian_id BIGINT COMMENT '保管人ID',
    department_id BIGINT COMMENT '所属部门ID',
    description VARCHAR(500) COMMENT '资产描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES asset_category(id),
    FOREIGN KEY (custodian_id) REFERENCES `user`(id),
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 资产领用表
CREATE TABLE IF NOT EXISTS asset_requisition (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    user_id BIGINT NOT NULL COMMENT '领用人ID',
    requisition_date DATE NOT NULL COMMENT '领用日期',
    return_date DATE COMMENT '归还日期',
    purpose VARCHAR(500) COMMENT '领用用途',
    status VARCHAR(50) DEFAULT 'APPROVED' COMMENT '状态：PENDING, APPROVED, REJECTED, RETURNED',
    approver_id BIGINT COMMENT '审批人ID',
    approval_comment TEXT COMMENT '审批意见',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_id) REFERENCES asset(id),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- 资产维修表
CREATE TABLE IF NOT EXISTS asset_maintenance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    maintenance_date DATE NOT NULL COMMENT '维修日期',
    maintenance_type VARCHAR(50) NOT NULL COMMENT '维修类型：PREVENTIVE, CORRECTIVE, EMERGENCY',
    description VARCHAR(500) COMMENT '故障描述',
    result VARCHAR(500) COMMENT '维修结果',
    cost DECIMAL(10,2) COMMENT '维修费用',
    maintenance_staff VARCHAR(100) COMMENT '维修人员',
    status VARCHAR(50) DEFAULT 'COMPLETED' COMMENT '状态：PENDING, IN_PROGRESS, COMPLETED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_id) REFERENCES asset(id)
);

-- 资产报废表
CREATE TABLE IF NOT EXISTS asset_scrap (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    scrap_date DATE NOT NULL COMMENT '报废日期',
    scrap_reason VARCHAR(500) NOT NULL COMMENT '报废原因',
    scrap_type VARCHAR(50) DEFAULT 'NORMAL' COMMENT '报废类型：NORMAL, DAMAGED, OBSOLETE',
    residual_value DECIMAL(10,2) COMMENT '残值',
    approval_status VARCHAR(50) DEFAULT 'PENDING' COMMENT '审批状态：PENDING, APPROVED, REJECTED',
    approver_id BIGINT COMMENT '审批人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_id) REFERENCES asset(id)
);

-- 初始数据

-- 插入默认部门
INSERT INTO department (name, description) VALUES ('总经办', '公司总部');
INSERT INTO department (name, description, parent_id) VALUES ('技术部', '负责技术开发', 1);
INSERT INTO department (name, description, parent_id) VALUES ('人事部', '负责人员管理', 1);
INSERT INTO department (name, description, parent_id) VALUES ('财务部', '负责财务管理', 1);

-- 插入默认用户（密码：123456）
INSERT INTO `user` (username, password, name, email, phone, department_id, position, role, employee_id, join_date) 
VALUES ('admin', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '管理员', 'admin@example.com', '13800138000', 1, '总经理', 'ADMIN', 'EMP001', '2024-01-01');

INSERT INTO `user` (username, password, name, email, phone, department_id, position, role, employee_id, join_date) 
VALUES ('tech', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '技术经理', 'tech@example.com', '13800138001', 2, '技术经理', 'MANAGER', 'EMP002', '2024-02-01');

INSERT INTO `user` (username, password, name, email, phone, department_id, position, role, employee_id, join_date) 
VALUES ('user1', '$2a$10$7wJ5P4e2Q6rT8uV9iW0xYz1AbCdEfGhIjKlMnOpQrStUvWxYz', '普通用户', 'user1@example.com', '13800138002', 2, '开发工程师', 'USER', 'EMP003', '2024-03-01');
