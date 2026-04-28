-- =============================================
-- 政务一体化平台数据库建表语句
-- 符合国密加密、等保三级、政务信创标准
-- 数据库: 达梦/人大金仓/华为高斯/PostgreSQL兼容
-- =============================================

-- 1. 组织架构管理（党政机关、事业单位）
CREATE TABLE sys_org (
    org_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_org'),
    org_code VARCHAR(50) NOT NULL UNIQUE COMMENT '组织机构代码',
    org_name VARCHAR(200) NOT NULL COMMENT '单位名称',
    org_type VARCHAR(20) NOT NULL COMMENT '单位类型: GOVERNMENT党政机关, INSTITUTION事业单位, ENTERPRISE国有企业',
    parent_id BIGINT COMMENT '上级单位ID',
    level_code VARCHAR(50) COMMENT '层级编码: 省-市-县-乡-村',
    unified_social_credit_code VARCHAR(50) COMMENT '统一社会信用代码',
    legal_person VARCHAR(100) COMMENT '法定代表人',
    contact_phone VARCHAR(30) COMMENT '联系电话',
    address VARCHAR(500) COMMENT '单位地址',
    latitude DECIMAL(10,6) COMMENT '经度',
    longitude DECIMAL(10,6) COMMENT '纬度',
    gm_encrypted_fields TEXT COMMENT '国密加密字段JSON',
    status SMALLINT DEFAULT 1 COMMENT '状态: 0停用 1正常',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_org_code UNIQUE (org_code)
);

CREATE INDEX idx_org_parent ON sys_org(parent_id);
CREATE INDEX idx_org_level ON sys_org(level_code);
CREATE INDEX idx_org_type ON sys_org(org_type);

-- 2. 用户表（支持国产加密）
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_user'),
    username VARCHAR(100) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(500) NOT NULL COMMENT '密码(国密SM3+SM2加密)',
    real_name VARCHAR(100) NOT NULL COMMENT '真实姓名',
    id_card VARCHAR(500) COMMENT '身份证号(加密存储)',
    phone VARCHAR(500) COMMENT '手机号(加密存储)',
    email VARCHAR(200) COMMENT '电子邮箱',
    org_id BIGINT NOT NULL COMMENT '所属单位ID',
    position VARCHAR(100) COMMENT '职务',
    rank_level VARCHAR(50) COMMENT '职级',
    user_type VARCHAR(20) NOT NULL COMMENT '用户类型: ADMIN系统管理员, LEADERSHIP领导, STAFF一般人员, SPECIAL专用角色',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级: 0普通 1内部 2秘密 3机密 4绝密',
    ai_model_permission VARCHAR(10) DEFAULT 'CLOSE' COMMENT 'AI权限: CLOSE关闭, READ_ONLY只读, FULL全功能',
    last_login_ip VARCHAR(50) COMMENT '最后登录IP',
    last_login_time TIMESTAMP,
    password_expire_time TIMESTAMP COMMENT '密码过期时间',
    password_change_required SMALLINT DEFAULT 0 COMMENT '是否需要改密',
    risk_level SMALLINT DEFAULT 0 COMMENT '风险等级: 0低风险 1中风险 2高风险',
    gm_public_key TEXT COMMENT '国密公钥',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT fk_user_org FOREIGN KEY (org_id) REFERENCES sys_org(org_id)
);

CREATE INDEX idx_user_org ON sys_user(org_id);
CREATE INDEX idx_user_type ON sys_user(user_type);
CREATE INDEX idx_user_secret ON sys_user(secret_level);

-- 3. 角色权限表（等保三级）
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_role'),
    role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    role_name VARCHAR(100) NOT NULL COMMENT '角色名称',
    role_type VARCHAR(20) NOT NULL COMMENT '角色类型: SYSTEM系统角色, BUSINESS业务角色, CUSTOM自定义',
    data_scope VARCHAR(20) DEFAULT 'SELF' COMMENT '数据权限范围: ALL全部, ORG本级及下级, DEPARTMENT本级, SELF本人',
    secret_level_required SMALLINT DEFAULT 0 COMMENT '最低涉密等级要求',
    permission_json TEXT COMMENT '菜单权限JSON',
    description VARCHAR(500) COMMENT '角色描述',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_role_code UNIQUE (role_code)
);

-- 4. 用户角色关联表
CREATE TABLE sys_user_role (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_user_role'),
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    org_id BIGINT COMMENT '角色授权的组织范围',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES sys_user(user_id),
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES sys_role(role_id)
);

CREATE INDEX idx_ur_user ON sys_user_role(user_id);
CREATE INDEX idx_ur_role ON sys_user_role(role_id);

-- 5. 菜单资源表
CREATE TABLE sys_menu (
    menu_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_menu'),
    menu_code VARCHAR(100) NOT NULL UNIQUE COMMENT '菜单编码',
    menu_name VARCHAR(100) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    menu_type VARCHAR(20) NOT NULL COMMENT '菜单类型: MODULE模块, MENU菜单, BUTTON按钮',
    path VARCHAR(200) COMMENT '路由路径',
    component VARCHAR(200) COMMENT '组件路径',
    icon VARCHAR(100) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序号',
    visible SMALLINT DEFAULT 1 COMMENT '是否显示: 0隐藏 1显示',
    cache SMALLINT DEFAULT 0 COMMENT '是否缓存: 0不缓存 1缓存',
    secret_level_required SMALLINT DEFAULT 0 COMMENT '最低涉密等级',
    permission_flag VARCHAR(100) COMMENT '权限标识',
    system_code VARCHAR(50) COMMENT '所属系统: GOV_PLATFORM政务一体化, GRID网格化, PETITION综治信访, SECURITY智慧安防, DOCUMENT公文机要',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0
);

CREATE INDEX idx_menu_parent ON sys_menu(parent_id);
CREATE INDEX idx_menu_system ON sys_menu(system_code);

-- 6. 操作日志表（等保三级审计）
CREATE TABLE sys_operate_log (
    log_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_operate_log'),
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(100) COMMENT '用户名',
    org_id BIGINT COMMENT '单位ID',
    org_name VARCHAR(200) COMMENT '单位名称',
    module VARCHAR(100) COMMENT '操作模块',
    business_type VARCHAR(50) COMMENT '业务类型',
    operation_type VARCHAR(50) COMMENT '操作类型',
    request_method VARCHAR(20) COMMENT '请求方式',
    request_url VARCHAR(500) COMMENT '请求地址',
    request_param TEXT COMMENT '请求参数',
    response_data TEXT COMMENT '响应数据',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    ip_location VARCHAR(200) COMMENT 'IP归属地',
    user_agent TEXT COMMENT '用户代理',
    operation_status SMALLINT COMMENT '操作状态: 0失败 1成功',
    error_msg TEXT COMMENT '错误信息',
    operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duration_ms BIGINT COMMENT '执行时长(毫秒)',
    gm_signature VARCHAR(500) COMMENT '国密签名'
);

CREATE INDEX idx_log_user ON sys_operate_log(user_id);
CREATE INDEX idx_log_time ON sys_operate_log(operation_time);
CREATE INDEX idx_log_module ON sys_operate_log(module);

-- 7. 登录日志表
CREATE TABLE sys_login_log (
    log_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_login_log'),
    username VARCHAR(100) NOT NULL COMMENT '登录用户名',
    user_id BIGINT COMMENT '用户ID',
    org_id BIGINT COMMENT '单位ID',
    login_type VARCHAR(20) COMMENT '登录方式: PASSWORD密码, SMS短信, CERT证书, FACE人脸, FINGERPRINT指纹',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    ip_location VARCHAR(200) COMMENT 'IP归属地',
    browser VARCHAR(200) COMMENT '浏览器',
    os VARCHAR(100) COMMENT '操作系统',
    device_uuid VARCHAR(200) COMMENT '设备唯一标识',
    location_lat DECIMAL(10,6) COMMENT '登录纬度',
    location_lng DECIMAL(10,6) COMMENT '登录经度',
    login_status SMALLINT NOT NULL COMMENT '登录状态: 0失败 1成功',
    fail_reason VARCHAR(200) COMMENT '失败原因',
    risk_assessment VARCHAR(50) COMMENT '风险评估: SAFE安全, LOW低风险, MEDIUM中风险, HIGH高风险',
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    gm_encrypted VARCHAR(500) COMMENT '国密加密验证字段'
);

CREATE INDEX idx_login_user ON sys_login_log(username);
CREATE INDEX idx_login_time ON sys_login_log(login_time);
CREATE INDEX idx_login_status ON sys_login_log(login_status);

-- 8. 网格化治理系统-网格单元表
CREATE TABLE grid_cell (
    cell_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_grid_cell'),
    cell_code VARCHAR(50) NOT NULL UNIQUE COMMENT '网格编码',
    cell_name VARCHAR(200) NOT NULL COMMENT '网格名称',
    cell_type VARCHAR(20) NOT NULL COMMENT '网格类型: REGION行政区域, COMMUNITY社区, GRID网格, BUILDING楼栋, UNIT单元',
    parent_id BIGINT COMMENT '上级网格ID',
    level_code VARCHAR(50) COMMENT '层级编码: 省-市-区-街道-社区-网格-楼栋-单元',
    boundary_geojson TEXT COMMENT '边界GeoJSON坐标',
    center_point VARCHAR(200) COMMENT '中心点坐标',
    area_sqm DECIMAL(12,2) COMMENT '面积(平方米)',
    population_count INT COMMENT '常住人口数',
    household_count INT COMMENT '户数',
    responsible_user_id BIGINT COMMENT '网格员用户ID',
    responsible_phone VARCHAR(100) COMMENT '网格员电话',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    status SMALLINT DEFAULT 1 COMMENT '状态: 0停用 1正常 2整治中',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_cell_code UNIQUE (cell_code)
);

CREATE INDEX idx_grid_parent ON grid_cell(parent_id);
CREATE INDEX idx_grid_level ON grid_cell(level_code);
CREATE INDEX idx_grid_type ON grid_cell(cell_type);
CREATE INDEX idx_grid_responsible ON grid_cell(responsible_user_id);

-- 9. 网格事件表
CREATE TABLE grid_event (
    event_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_grid_event'),
    event_code VARCHAR(50) NOT NULL UNIQUE COMMENT '事件编号',
    event_title VARCHAR(500) NOT NULL COMMENT '事件标题',
    event_type VARCHAR(50) NOT NULL COMMENT '事件类型: ENVIRONMENT环境, SECURITY治安, FACILITY设施, PETITION信访, DISPUTE纠纷, HEALTH卫生, OTHER其他',
    event_type_detail VARCHAR(100) COMMENT '事件细分类型',
    urgent_level VARCHAR(20) DEFAULT 'NORMAL' COMMENT '紧急程度: LOW较低, NORMAL一般, URGENT紧急, VERY_URGENT很紧急, EXTREME特别紧急',
    grid_cell_id BIGINT NOT NULL COMMENT '所属网格ID',
    grid_cell_code VARCHAR(50) COMMENT '网格编码',
    grid_cell_name VARCHAR(200) COMMENT '网格名称',
    address VARCHAR(500) COMMENT '事件地址',
    location_lat DECIMAL(10,6) COMMENT '纬度',
    location_lng DECIMAL(10,6) COMMENT '经度',
    description TEXT COMMENT '事件描述',
    reporter_name VARCHAR(100) COMMENT '上报人姓名',
    reporter_phone VARCHAR(100) COMMENT '上报人电话',
    reporter_type VARCHAR(20) COMMENT '上报人类型: CITIZEN市民, GRIDDER网格员, STAFF工作人员, SYSTEM系统',
    photos_json TEXT COMMENT '现场照片JSON数组',
    audio_url VARCHAR(500) COMMENT '录音文件URL',
    video_url VARCHAR(500) COMMENT '视频文件URL',
    handle_department_id BIGINT COMMENT '主办单位ID',
    handle_department_name VARCHAR(200) COMMENT '主办单位名称',
    cooperate_department_ids VARCHAR(500) COMMENT '协办单位ID列表',
    deadline TIMESTAMP COMMENT '办结期限',
    report_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '上报时间',
    assign_time TIMESTAMP COMMENT '分派时间',
    accept_time TIMESTAMP COMMENT '受理时间',
    start_handle_time TIMESTAMP COMMENT '开始处理时间',
    complete_time TIMESTAMP COMMENT '完成时间',
    close_time TIMESTAMP COMMENT '关闭时间',
    current_status VARCHAR(30) NOT NULL DEFAULT 'PENDING' COMMENT '当前状态: PENDING待分派, ASSIGNED已分派, ACCEPTED已受理, HANDLING处理中, COMPLETED已完成, CLOSED已关闭, REJECTED已驳回',
    handle_result VARCHAR(500) COMMENT '处理结果',
    handle_detail TEXT COMMENT '处理详情',
    satisfaction_score INT COMMENT '满意度评分(1-5)',
    citizen_feedback TEXT COMMENT '市民反馈',
    evaluation_time TIMESTAMP COMMENT '评价时间',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    gm_encrypted_fields TEXT COMMENT '国密加密字段',
    source_system VARCHAR(50) COMMENT '来源系统',
    source_event_id VARCHAR(100) COMMENT '来源事件ID',
    ai_suggestion TEXT COMMENT 'AI智能分派建议',
    ai_risk_level VARCHAR(20) COMMENT 'AI风险等级评估',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_event_code UNIQUE (event_code)
);

CREATE INDEX idx_event_grid ON grid_event(grid_cell_id);
CREATE INDEX idx_event_status ON grid_event(current_status);
CREATE INDEX idx_event_type ON grid_event(event_type);
CREATE INDEX idx_event_urgent ON grid_event(urgent_level);
CREATE INDEX idx_event_time ON grid_event(report_time);
CREATE INDEX idx_event_deadline ON grid_event(deadline);

-- 10. 网格员巡检记录表
CREATE TABLE grid_inspection (
    inspection_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_grid_inspection'),
    inspection_code VARCHAR(50) NOT NULL UNIQUE COMMENT '巡检编号',
    gridder_user_id BIGINT NOT NULL COMMENT '网格员用户ID',
    gridder_name VARCHAR(100) COMMENT '网格员姓名',
    grid_cell_id BIGINT NOT NULL COMMENT '网格ID',
    grid_cell_code VARCHAR(50) COMMENT '网格编码',
    grid_cell_name VARCHAR(200) COMMENT '网格名称',
    inspection_type VARCHAR(30) NOT NULL COMMENT '巡检类型: ROUTINE例行巡检, SPECIAL专项巡检, EMERGENCY应急巡检, VIDEO视频巡检',
    route_geojson TEXT COMMENT '巡检路线GeoJSON',
    start_time TIMESTAMP NOT NULL COMMENT '开始时间',
    end_time TIMESTAMP COMMENT '结束时间',
    duration_minutes INT COMMENT '巡检时长(分钟)',
    check_points_json TEXT COMMENT '检查点JSON: [{pointId, pointName, checkResult, remark}]',
   发现问题_json TEXT COMMENT '发现问题JSON',
    event_count INT DEFAULT 0 COMMENT '发现事件数',
    photos_json TEXT COMMENT '巡检照片JSON',
    location_track TEXT COMMENT '位置轨迹JSON数组',
    end_lat DECIMAL(10,6) COMMENT '结束纬度',
    end_lng DECIMAL(10,6) COMMENT '结束经度',
    distance_km DECIMAL(8,2) COMMENT '巡检距离(公里)',
    health_status VARCHAR(20) COMMENT '身体状态: NORMAL正常, TIRED疲劳, UNWELL不适',
    weather_condition VARCHAR(50) COMMENT '天气情况',
    temperature DECIMAL(5,1) COMMENT '温度',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_inspection_code UNIQUE (inspection_code)
);

CREATE INDEX idx_inspection_grider ON grid_inspection(gridder_user_id);
CREATE INDEX idx_inspection_grid ON grid_inspection(grid_cell_id);
CREATE INDEX idx_inspection_time ON grid_inspection(start_time);

-- 11. 综治信访系统-信访登记表
CREATE TABLE petition_register (
    petition_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_petition_register'),
    petition_code VARCHAR(50) NOT NULL UNIQUE COMMENT '信访编号',
    petition_type VARCHAR(30) NOT NULL COMMENT '信访类型: LETTER来信, VISIT来访, EMAIL邮件, TELEPHONE电话, SMS短信, INTERNET网购, OTHER其他',
    petition_category VARCHAR(50) NOT NULL COMMENT '信访类别: SUGGESTION建议, COMPLAINT投诉, REQUEST请求, REPORT举报, CONSULTATION咨询',
    title VARCHAR(500) NOT NULL COMMENT '信访标题',
    content TEXT NOT NULL COMMENT '信访内容',
    appeal_items TEXT COMMENT '诉求事项JSON数组',
    evidence_materials TEXT COMMENT '证据材料JSON',
    petitioner_name VARCHAR(100) NOT NULL COMMENT '信访人姓名',
    petitioner_phone VARCHAR(200) COMMENT '信访人电话(加密)',
    petitioner_id_card VARCHAR(500) COMMENT '信访人身份证(加密)',
    petitioner_address VARCHAR(500) COMMENT '信访人地址',
    petitioner_type VARCHAR(20) COMMENT '信访人类别: NORMAL正常, REPETITIVE反复, KEY重点, SPECIAL专项',
    is_anonymous SMALLINT DEFAULT 0 COMMENT '是否匿名',
    is_key_case SMALLINT DEFAULT 0 COMMENT '是否重点督办',
    is_repetitive_smallint DEFAULT 0 COMMENT '是否重复信访',
    related_petition_ids VARCHAR(500) COMMENT '关联信访ID列表',
    happen_date DATE COMMENT '信访发生日期',
    happen_place VARCHAR(500) COMMENT '信访发生地点',
    happen_region_code VARCHAR(50) COMMENT '信访发生地区编码',
    involved_organization_ids VARCHAR(500) COMMENT '涉及单位ID列表',
    involved_organization_names VARCHAR(500) COMMENT '涉及单位名称',
    involved_amount DECIMAL(15,2) COMMENT '涉及金额',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    accept_department_id BIGINT COMMENT '受理单位ID',
    accept_department_name VARCHAR(200) COMMENT '受理单位名称',
    accept_time TIMESTAMP COMMENT '受理时间',
    accept_way VARCHAR(30) COMMENT '受理方式',
    accept_result VARCHAR(50) COMMENT '受理结果: ACCEPT受理, NOT_ACCEPT不予受理, TRANSFER转送, RECOMMEND建议',
    guide_department_id BIGINT COMMENT '分流导向单位ID',
    guide_time TIMESTAMP COMMENT '分流时间',
    deadline TIMESTAMP COMMENT '办理期限',
    handle_department_id BIGINT COMMENT '办理单位ID',
    handle_department_name VARCHAR(200) COMMENT '办理单位名称',
    handle_user_id BIGINT COMMENT '承办人ID',
    handle_user_name VARCHAR(100) COMMENT '承办人姓名',
    current_status VARCHAR(30) NOT NULL DEFAULT 'PENDING' COMMENT '当前状态: PENDING待受理, ACCEPTED已受理, HANDLING办理中, COMPLETED待评价, CLOSED已办结, REJECTED不予受理',
    process_nodes_json TEXT COMMENT '流程节点JSON: [{nodeName, nodeStatus, operatorId, operatorName, operateTime, remark}]',
    handle_result TEXT COMMENT '办理结果',
    handle_measures TEXT COMMENT '办理措施',
    satisfaction_score INT COMMENT '满意度评分',
    petition_feedback TEXT COMMENT '信访人反馈',
    close_time TIMESTAMP COMMENT '办结时间',
    ai_analyze_result TEXT COMMENT 'AI分析结果',
    ai_similar_cases TEXT COMMENT 'AI相似案例',
    ai_risk_level VARCHAR(20) COMMENT 'AI风险等级',
    ai_suggestion TEXT COMMENT 'AI办理建议',
    gm_encrypted_fields TEXT COMMENT '国密加密字段',
    source_channel VARCHAR(50) COMMENT '来源渠道',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_petition_code UNIQUE (petition_code)
);

CREATE INDEX idx_petition_status ON petition_register(current_status);
CREATE INDEX idx_petition_type ON petition_register(petition_type);
CREATE INDEX idx_petition_petitioner ON petition_register(petitioner_name);
CREATE INDEX idx_petition_time ON petition_register(create_time);
CREATE INDEX idx_petition_deadline ON petition_register(deadline);

-- 12. 信访事项办理表
CREATE TABLE petition_work (
    work_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_petition_work'),
    work_code VARCHAR(50) NOT NULL UNIQUE COMMENT '办理编号',
    petition_id BIGINT NOT NULL COMMENT '信访ID',
    petition_code VARCHAR(50) COMMENT '信访编号',
    work_phase VARCHAR(30) NOT NULL COMMENT '办理阶段: INVESTIGATION调查, COORDINATION协调, DECISION决定, RESPONSE答复, FOLLOWUP跟踪, CLOSURE结案',
    work_type VARCHAR(50) COMMENT '办理方式: INDEPENDENT独立办理, JOINT联合办理, SUPERVISED督办办理',
    department_id BIGINT NOT NULL COMMENT '办理单位ID',
    department_name VARCHAR(200) NOT NULL COMMENT '办理单位名称',
    user_id BIGINT COMMENT '承办人ID',
    user_name VARCHAR(100) COMMENT '承办人姓名',
    user_position VARCHAR(100) COMMENT '承办人职务',
    work_content TEXT COMMENT '工作内容',
    work_measures TEXT COMMENT '采取措施',
    investigation_detail TEXT COMMENT '调查情况',
    legal_basis TEXT COMMENT '法律依据',
    handling_suggestion TEXT COMMENT '处理建议',
    leader_approval_text TEXT COMMENT '领导批示',
    leader_approval_user_id BIGINT COMMENT '批示领导ID',
    leader_approval_user_name VARCHAR(100) COMMENT '批示领导姓名',
    approval_time TIMESTAMP COMMENT '批示时间',
    meeting_discussion_text TEXT COMMENT '会议讨论意见',
    coordination_records TEXT COMMENT '协调会记录',
    field_verification_text TEXT COMMENT '现场核实情况',
    evidence_review_text TEXT COMMENT '证据审查情况',
    legal_review_result VARCHAR(50) COMMENT '法制审核结果: PASS通过, REVISE修改, REJECT退回',
    final_decision TEXT COMMENT '最终决定',
    response_content TEXT COMMENT '答复内容',
    response_way VARCHAR(30) COMMENT '答复方式: LETTER书面, PHONE电话, FACE面对面, MEETING会议',
    response_time TIMESTAMP COMMENT '答复时间',
    petitioner_acknowledge VARCHAR(20) COMMENT '信访人知悉情况: ACKNOWLEDGED已知悉, NOT_YET未告知, REFUSED拒绝',
    appeal_info TEXT COMMENT '申诉信息',
    close_info TEXT COMMENT '结案信息',
    is_key_supervision SMALLINT DEFAULT 0 COMMENT '是否重点督办',
    supervision_level VARCHAR(20) COMMENT '督办级别: CITY市级, DISTRICT区级, PROVINCE省级, CENTRAL中央',
    ai_suggestion TEXT COMMENT 'AI辅助建议',
    ai_risk_warning TEXT COMMENT 'AI风险预警',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    deadline TIMESTAMP COMMENT '阶段期限',
    actual_complete_time TIMESTAMP COMMENT '实际完成时间',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_work_code UNIQUE (work_code),
    CONSTRAINT fk_work_petition FOREIGN KEY (petition_id) REFERENCES petition_register(petition_id)
);

CREATE INDEX idx_work_petition ON petition_work(petition_id);
CREATE INDEX idx_work_department ON petition_work(department_id);
CREATE INDEX idx_work_phase ON petition_work(work_phase);

-- 13. 公文机要系统-公文表
CREATE TABLE official_document (
    doc_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_official_document'),
    doc_code VARCHAR(100) NOT NULL UNIQUE COMMENT '公文编号',
    doc_type VARCHAR(30) NOT NULL COMMENT '公文类型: NOTICE通知, COMMAND命令, DECISION决定, PROCLAMATION公告, REPORT报告, PROPOSAL议案, LETTER函, PASSPORT证件',
    doc_title VARCHAR(500) NOT NULL COMMENT '公文标题',
    doc_subject VARCHAR(200) COMMENT '主题词',
    secret_level SMALLINT NOT NULL DEFAULT 0 COMMENT '密级: 0普通 1内部 2秘密 3机密 4绝密',
    urgency_level VARCHAR(20) DEFAULT 'NORMAL' COMMENT '紧急程度: LOW低, NORMAL一般, URGENT紧急, VERY_URGENT特急',
    main送_unit_ids TEXT COMMENT '主送单位ID列表',
    main送_unit_names TEXT COMMENT '主送单位名称',
    cc_unit_ids TEXT COMMENT '抄送单位ID列表',
    cc_unit_names TEXT COMMENT '抄送单位名称',
    issue_department_id BIGINT NOT NULL COMMENT '发文单位ID',
    issue_department_name VARCHAR(200) NOT NULL COMMENT '发文单位名称',
    issue_user_id BIGINT NOT NULL COMMENT '拟稿人ID',
    issue_user_name VARCHAR(100) NOT NULL COMMENT '拟稿人姓名',
    draft_content TEXT COMMENT '起草内容',
    attachments_json TEXT COMMENT '附件JSON: [{name, url, size, type}]',
    page_count INT COMMENT '页数',
    edition_count INT COMMENT '份数',
    word_count INT COMMENT '字数',
    reference_doc_codes VARCHAR(500) COMMENT '参考公文编号',
    related_doc_ids TEXT COMMENT '关联公文ID列表',
    approval_flow_id BIGINT COMMENT '审批流程ID',
    current_approval_node VARCHAR(100) COMMENT '当前审批节点',
    approval_status VARCHAR(30) DEFAULT 'DRAFT' COMMENT '审批状态: DRAFT草稿, APPROVING审批中, APPROVED已批准, PUBLISHED已发布, ARCHIVED已归档, RECALLED已撤回',
    issue_date DATE COMMENT '发文日期',
    issue_time TIMESTAMP COMMENT '发文时间',
    sign_user_id BIGINT COMMENT '签发人ID',
    sign_user_name VARCHAR(100) COMMENT '签发人姓名',
    sign_time TIMESTAMP COMMENT '签发时间',
    print_user_id BIGINT COMMENT '印制人ID',
    print_user_name VARCHAR(100) COMMENT '印制人姓名',
    print_time TIMESTAMP COMMENT '印制时间',
    print_count INT COMMENT '印制份数',
    receive_confirm_json TEXT COMMENT '收文确认JSON',
    read_confirm_json TEXT COMMENT '阅读确认JSON',
    archive_info TEXT COMMENT '归档信息',
    archive_id BIGINT COMMENT '归档ID',
    gm_encrypted_content TEXT COMMENT '国密加密正文',
    gm_signature VARCHAR(500) COMMENT '国密签名',
    gm_hash VARCHAR(200) COMMENT '国密哈希值',
    ai_summary TEXT COMMENT 'AI公文摘要',
    ai_key_points TEXT COMMENT 'AI要点提取',
    ai_extract_tasks TEXT COMMENT 'AI提取任务',
    ai_deadline_warning TEXT COMMENT 'AI期限预警',
    digital_signature VARCHAR(500) COMMENT '数字签名',
    blockchain_hash VARCHAR(200) COMMENT '区块链哈希',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_doc_code UNIQUE (doc_code)
);

CREATE INDEX idx_doc_type ON official_document(doc_type);
CREATE INDEX idx_doc_secret ON official_document(secret_level);
CREATE INDEX idx_doc_status ON official_document(approval_status);
CREATE INDEX idx_doc_issue_dept ON official_document(issue_department_id);
CREATE INDEX idx_doc_issue_date ON official_document(issue_date);

-- 14. 公文审批流程表
CREATE TABLE document_approval_flow (
    flow_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_doc_approval_flow'),
    flow_code VARCHAR(50) NOT NULL UNIQUE COMMENT '流程编码',
    flow_name VARCHAR(100) NOT NULL COMMENT '流程名称',
    flow_type VARCHAR(30) NOT NULL COMMENT '流程类型: DRAFT起草, ISSUE发文, RECEIVE收文, TRANSFER流转',
    doc_type VARCHAR(30) COMMENT '适用公文类型',
    secret_level_required SMALLINT COMMENT '所需最低密级',
    nodes_json TEXT NOT NULL COMMENT '流程节点JSON: [{nodeId, nodeName, nodeType, approverType, approverIds, approverNames, isRequired, timeoutHours, timeoutAction}]',
    rules_json TEXT COMMENT '流转规则JSON',
    timeout_hours INT COMMENT '默认超时小时数',
    timeout_action VARCHAR(30) COMMENT '超时动作: AUTO_PASS自动通过, AUTO_REJECT自动驳回, REMIND提醒, ESCALATE升级',
    allow_withdraw SMALLINT DEFAULT 1 COMMENT '是否允许撤回',
    allow_transfer SMALLINT DEFAULT 0 COMMENT '是否允许转办',
    allow_countersign SMALLINT DEFAULT 0 COMMENT '是否允许会签',
    version INT DEFAULT 1 COMMENT '版本号',
    is_active SMALLINT DEFAULT 1 COMMENT '是否启用',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0
);

-- 15. 公文审批记录表
CREATE TABLE document_approval_record (
    record_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_doc_approval_record'),
    record_code VARCHAR(50) NOT NULL UNIQUE COMMENT '审批记录编号',
    doc_id BIGINT NOT NULL COMMENT '公文ID',
    doc_code VARCHAR(100) COMMENT '公文编号',
    flow_id BIGINT COMMENT '流程ID',
    node_id VARCHAR(50) NOT NULL COMMENT '节点ID',
    node_name VARCHAR(100) NOT NULL COMMENT '节点名称',
    node_type VARCHAR(30) NOT NULL COMMENT '节点类型: START发起, APPROVAL审批, SIGN会签, CIRCULATE传阅, END结束',
    approver_user_id BIGINT NOT NULL COMMENT '审批人ID',
    approver_user_name VARCHAR(100) NOT NULL COMMENT '审批人姓名',
    approver_department_id BIGINT COMMENT '审批人单位ID',
    approver_department_name VARCHAR(200) COMMENT '审批人单位名称',
    approval_opinion TEXT COMMENT '审批意见',
    approval_result VARCHAR(20) NOT NULL COMMENT '审批结果: APPROVE同意, REJECT不同意, TRANSFER转办, ADD_INFO补正, SUSPEND暂缓',
    is_qualified VARCHAR(20) DEFAULT 'QUALIFIED' COMMENT '是否符合: QUALIFIED符合, UNQUALIFIED不符合',
    sign_info TEXT COMMENT '签批信息',
    attachment_urls TEXT COMMENT '附件URL列表',
    operate_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    operate_ip VARCHAR(50) COMMENT '操作IP',
    gm_signature VARCHAR(500) COMMENT '国密签名',
    next_node_id VARCHAR(50) COMMENT '下一节点ID',
    next_approver_ids TEXT COMMENT '下一审批人ID列表',
    delegation_user_id BIGINT COMMENT '委托办理人ID',
    delegation_user_name VARCHAR(100) COMMENT '委托办理人姓名',
    delegate_reason TEXT COMMENT '委托原因',
    timeout_action_taken VARCHAR(30) COMMENT '超时处理方式',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT fk_record_doc FOREIGN KEY (doc_id) REFERENCES official_document(doc_id)
);

CREATE INDEX idx_record_doc ON document_approval_record(doc_id);
CREATE INDEX idx_record_approver ON document_approval_record(approver_user_id);
CREATE INDEX idx_record_time ON document_approval_record(operate_time);

-- 16. 智慧安防系统-设备表
CREATE TABLE security_device (
    device_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_security_device'),
    device_code VARCHAR(100) NOT NULL UNIQUE COMMENT '设备编码',
    device_name VARCHAR(200) NOT NULL COMMENT '设备名称',
    device_type VARCHAR(30) NOT NULL COMMENT '设备类型: CAMERA摄像头, ACCESS_CONTROL门禁, ALARM报警, SPEAKER广播, ENVIRONMENT环境监测, FIRE消防, GAS燃气, WATER水浸',
    device_sub_type VARCHAR(50) COMMENT '设备子类型',
    manufacturer VARCHAR(200) COMMENT '生产厂家',
    model VARCHAR(100) COMMENT '型号',
    serial_number VARCHAR(200) COMMENT '序列号',
    mac_address VARCHAR(100) COMMENT 'MAC地址',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    port_number INT COMMENT '端口号',
    install_location VARCHAR(500) COMMENT '安装位置',
    install_grid_cell_id BIGINT COMMENT '所属网格ID',
    install_lat DECIMAL(10,6) COMMENT '安装纬度',
    install_lng DECIMAL(10,6) COMMENT '安装经度',
    direction_angle DECIMAL(5,2) COMMENT '朝向角度',
    coverage_radius DECIMAL(8,2) COMMENT '覆盖半径(米)',
    install_date DATE COMMENT '安装日期',
    warranty_end_date DATE COMMENT '保修截止日期',
    associated_device_ids VARCHAR(500) COMMENT '关联设备ID列表',
    linked_channel_ids VARCHAR(500) COMMENT '关联通道ID列表',
    video_stream_url VARCHAR(500) COMMENT '视频流地址',
    snapshot_interval INT DEFAULT 5 COMMENT '抓拍间隔(秒)',
    record_storage_days INT DEFAULT 30 COMMENT '录像存储天数',
    alarm_threshold_json TEXT COMMENT '报警阈值JSON',
    ai_analysis_enabled SMALLINT DEFAULT 0 COMMENT '是否启用AI分析',
    ai_analysis_types VARCHAR(200) COMMENT 'AI分析类型: FACE人脸, VEHICLE车辆, BEHAVIOR行为, CROWD人群, INTRUSION入侵',
    device_status VARCHAR(20) DEFAULT 'ONLINE' COMMENT '设备状态: ONLINE在线, OFFLINE离线, FAULT故障, MAINTENANCE维护, UPGRADING升级中',
    last_heartbeat_time TIMESTAMP COMMENT '最后心跳时间',
    last_online_time TIMESTAMP COMMENT '最后在线时间',
    health_score INT COMMENT '健康评分(0-100)',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    gm_encrypted_fields TEXT COMMENT '国密加密字段',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_device_code UNIQUE (device_code)
);

CREATE INDEX idx_device_type ON security_device(device_type);
CREATE INDEX idx_device_grid ON security_device(install_grid_cell_id);
CREATE INDEX idx_device_status ON security_device(device_status);
CREATE INDEX idx_device_location ON security_device(install_lat, install_lng);

-- 17. 安防告警记录表
CREATE TABLE security_alarm (
    alarm_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_security_alarm'),
    alarm_code VARCHAR(50) NOT NULL UNIQUE COMMENT '告警编号',
    alarm_type VARCHAR(50) NOT NULL COMMENT '告警类型: INTRUSION入侵, CROWD异常聚集, PERIMETER周界入侵, FACE人脸识别, VEHICLE车辆识别, FIRE火情, SMOKE烟雾, GAS燃气泄漏, WATER漏水, TEMPERATURE温度异常, ANTI_TAMPER防拆, OFFLINE设备离线',
    alarm_level VARCHAR(20) NOT NULL DEFAULT 'MEDIUM' COMMENT '告警级别: LOW低, MEDIUM中, HIGH高, CRITICAL危急',
    alarm_title VARCHAR(500) NOT NULL COMMENT '告警标题',
    alarm_content TEXT COMMENT '告警内容',
    device_id BIGINT COMMENT '关联设备ID',
    device_code VARCHAR(100) COMMENT '设备编码',
    device_name VARCHAR(200) COMMENT '设备名称',
    grid_cell_id BIGINT COMMENT '所属网格ID',
    grid_cell_name VARCHAR(200) COMMENT '网格名称',
    location_address VARCHAR(500) COMMENT '告警位置',
    location_lat DECIMAL(10,6) COMMENT '纬度',
    location_lng DECIMAL(10,6) COMMENT '经度',
    alarm_time TIMESTAMP NOT NULL COMMENT '告警时间',
    alarm_source_type VARCHAR(30) COMMENT '告警来源: DEVICE设备, AI_ANALYSIS AI分析, MANUAL人工, SYSTEM系统',
    alarm_source_id VARCHAR(100) COMMENT '告警来源ID',
    alarm_pic_urls TEXT COMMENT '告警图片URL列表',
    alarm_video_url VARCHAR(500) COMMENT '告警录像URL',
    ai_confidence DECIMAL(5,2) COMMENT 'AI置信度',
    ai_analyze_result TEXT COMMENT 'AI分析结果',
    ai_target_info TEXT COMMENT 'AI目标信息: {type, count, features}',
    target_tracking_json TEXT COMMENT '目标追踪JSON',
    handle_department_id BIGINT COMMENT '处理单位ID',
    handle_department_name VARCHAR(200) COMMENT '处理单位名称',
    handle_user_id BIGINT COMMENT '处理人ID',
    handle_user_name VARCHAR(100) COMMENT '处理人姓名',
    dispatch_time TIMESTAMP COMMENT '分派时间',
    dispatch_user_id BIGINT COMMENT '分派人ID',
    dispatch_user_name VARCHAR(100) COMMENT '分派人姓名',
    arrive_time TIMESTAMP COMMENT '到场时间',
    handle_start_time TIMESTAMP COMMENT '开始处理时间',
    handle_end_time TIMESTAMP COMMENT '处理完成时间',
    current_status VARCHAR(30) NOT NULL DEFAULT 'PENDING' COMMENT '当前状态: PENDING待处理, DISPATCHED已分派, ARRIVED已到场, HANDLING处理中, RESOLVED已解决, FALSE_ALARM误报, CLOSED已关闭',
    handle_result VARCHAR(50) COMMENT '处理结果: REAL真警, FALSE误报, TEST测试',
    handle_detail TEXT COMMENT '处理详情',
    handle_measures TEXT COMMENT '处置措施',
    feedback_content TEXT COMMENT '反馈内容',
    evaluate_score INT COMMENT '满意度评分',
    evaluate_content TEXT COMMENT '评价内容',
    response_time_minutes INT COMMENT '响应时长(分钟)',
    resolve_time_minutes INT COMMENT '解决时长(分钟)',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    gm_encrypted_fields TEXT COMMENT '国密加密字段',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_alarm_code UNIQUE (alarm_code)
);

CREATE INDEX idx_alarm_type ON security_alarm(alarm_type);
CREATE INDEX idx_alarm_level ON security_alarm(alarm_level);
CREATE INDEX idx_alarm_time ON security_alarm(alarm_time);
CREATE INDEX idx_alarm_status ON security_alarm(current_status);
CREATE INDEX idx_alarm_device ON security_alarm(device_id);
CREATE INDEX idx_alarm_grid ON security_alarm(grid_cell_id);

-- 18. 数据权限管理表
CREATE TABLE sys_data_permission (
    permission_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_data_permission'),
    permission_code VARCHAR(50) NOT NULL UNIQUE COMMENT '权限编码',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    permission_type VARCHAR(20) NOT NULL COMMENT '权限类型: ORG_LEVEL层级, REGION区域, SECRET_LEVEL密级, BUSINESS业务',
    rule_json TEXT NOT NULL COMMENT '规则JSON: {type, value, conditions}',
    description VARCHAR(500) COMMENT '描述',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0
);

-- 19. 系统配置表
CREATE TABLE sys_config (
    config_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_sys_config'),
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    config_type VARCHAR(30) COMMENT '配置类型: STRING字符串, NUMBER数字, BOOLEAN布尔, JSON JSON对象',
    config_group VARCHAR(50) COMMENT '配置分组',
    description VARCHAR(500) COMMENT '描述',
    secret_level SMALLINT DEFAULT 0 COMMENT '涉密等级',
    gm_encrypted SMALLINT DEFAULT 0 COMMENT '是否国密加密',
    status SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    CONSTRAINT uk_config_key UNIQUE (config_key)
);

-- 20. 序列生成器（用于国产数据库兼容）
CREATE SEQUENCE seq_sys_org START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_user START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_role START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_user_role START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_menu START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_operate_log START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_login_log START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_grid_cell START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_grid_event START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_grid_inspection START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_petition_register START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_petition_work START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_official_document START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_doc_approval_flow START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_doc_approval_record START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_security_device START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_security_alarm START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_data_permission START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_sys_config START WITH 1 INCREMENT BY 1;

-- 21. 初始化系统管理员（国密SM2加密密码: Admin@123456）
INSERT INTO sys_org (org_id, org_code, org_name, org_type, parent_id, level_code, unified_social_credit_code, status) VALUES
(1, 'GOV_001', '测试党政机关', 'GOVERNMENT', NULL, '00', '111111111111111111', 1);

INSERT INTO sys_user (user_id, username, password, real_name, id_card, phone, email, org_id, position, user_type, secret_level, status) VALUES
(1, 'admin', 'SM2:xxxxxxxxxxxxxxencrypted', '系统管理员', 'SM2:encrypted', 'SM2:encrypted', 'admin@gov.cn', 1, '系统管理员', 'ADMIN', 0, 1);

INSERT INTO sys_role (role_id, role_code, role_name, role_type, data_scope, permission_json, status) VALUES
(1, 'SUPER_ADMIN', '超级管理员', 'SYSTEM', 'ALL', '{"menuIds":[1,2,3,4,5,6,7,8,9,10]}', 1),
(2, 'GRID_MANAGER', '网格管理员', 'BUSINESS', 'ORG', '{"menuIds":[10,11,12,13,14]}', 1),
(3, 'PETITION_HANDLER', '信访办理员', 'BUSINESS', 'ORG', '{"menuIds":[20,21,22,23,24]}', 1),
(4, 'SECURITY_GUARD', '安防监控员', 'BUSINESS', 'ORG', '{"menuIds":[30,31,32,33,34]}', 1),
(5, 'DOCUMENT_MANAGER', '公文管理员', 'BUSINESS', 'ORG', '{"menuIds":[40,41,42,43,44]}', 1);

INSERT INTO sys_menu (menu_id, menu_code, menu_name, parent_id, menu_type, path, component, system_code, sort_order, status) VALUES
(1, 'DASHBOARD', '工作台', 0, 'MODULE', '/dashboard', 'Layout', 'GOV_PLATFORM', 0, 1),
(2, 'GRID_SYSTEM', '网格化治理', 0, 'MODULE', '/grid', 'Layout', 'GRID', 1, 1),
(3, 'PETITION_SYSTEM', '综治信访', 0, 'MODULE', '/petition', 'Layout', 'PETITION', 2, 1),
(4, 'SECURITY_SYSTEM', '智慧安防', 0, 'MODULE', '/security', 'Layout', 'SECURITY', 3, 1),
(5, 'DOCUMENT_SYSTEM', '公文机要', 0, 'MODULE', '/document', 'Layout', 'DOCUMENT', 4, 1);

-- 初始化系统配置
INSERT INTO sys_config (config_key, config_value, config_type, config_group, description) VALUES
('sys.gm.enabled', 'true', 'BOOLEAN', 'SECURITY', '国密加密启用'),
('sys.ai.enabled', 'true', 'BOOLEAN', 'AI', 'AI功能启用'),
('sys.ai.model.provider', 'local', 'STRING', 'AI', 'AI模型提供商'),
('sys.password.expire.days', '90', 'NUMBER', 'SECURITY', '密码过期天数'),
('sys.login.max.fail.count', '5', 'NUMBER', 'SECURITY', '最大登录失败次数'),
('sys.login.fail.lock.minutes', '30', 'NUMBER', 'SECURITY', '登录失败锁定分钟数');
