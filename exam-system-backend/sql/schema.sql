-- CRM客户管理系统
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    contact_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(200),
    industry VARCHAR(50),
    scale VARCHAR(20),
    source VARCHAR(50),
    level VARCHAR(20),
    total_amount DECIMAL(18,2),
    order_count INT,
    status VARCHAR(20),
    remark TEXT,
    create_by BIGINT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE customer_contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    contact_type VARCHAR(50),
    content TEXT,
    result VARCHAR(50),
    next_action TEXT,
    create_by BIGINT,
    create_time DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE customer_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    title VARCHAR(100),
    description TEXT,
    priority VARCHAR(20),
    status VARCHAR(20),
    due_time DATETIME,
    assignee_id BIGINT,
    create_by BIGINT,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- SCM供应链管理系统
CREATE TABLE supplier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    contact_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(200),
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    tax_number VARCHAR(50),
    business_license VARCHAR(200),
    rating VARCHAR(20),
    status VARCHAR(20),
    credit_limit DECIMAL(18,2),
    remark TEXT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE purchase_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_code VARCHAR(50) NOT NULL,
    supplier_id BIGINT NOT NULL,
    supplier_name VARCHAR(100),
    order_date DATETIME,
    delivery_date DATETIME,
    status VARCHAR(20),
    total_amount DECIMAL(18,2),
    item_count INT,
    remark TEXT,
    create_by BIGINT,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);

CREATE TABLE purchase_order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_code VARCHAR(50),
    product_name VARCHAR(100),
    spec VARCHAR(100),
    unit VARCHAR(20),
    quantity INT,
    unit_price DECIMAL(18,2),
    amount DECIMAL(18,2),
    FOREIGN KEY (order_id) REFERENCES purchase_order(id)
);

-- 电商商城系统
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    spec VARCHAR(100),
    unit VARCHAR(20),
    price DECIMAL(18,2),
    stock INT,
    sales INT,
    description TEXT,
    image_url VARCHAR(200),
    status VARCHAR(20),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL,
    user_id BIGINT,
    user_name VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(200),
    total_amount DECIMAL(18,2),
    discount_amount DECIMAL(18,2),
    pay_amount DECIMAL(18,2),
    pay_method VARCHAR(50),
    pay_status VARCHAR(20),
    status VARCHAR(20),
    order_time DATETIME,
    pay_time DATETIME,
    ship_time DATETIME,
    complete_time DATETIME
);

CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT,
    product_code VARCHAR(50),
    product_name VARCHAR(100),
    spec VARCHAR(100),
    quantity INT,
    unit_price DECIMAL(18,2),
    amount DECIMAL(18,2),
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

-- 会员管理系统
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_no VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    gender VARCHAR(10),
    birthday DATE,
    level VARCHAR(20),
    points DECIMAL(18,2),
    total_consume DECIMAL(18,2),
    order_count INT,
    register_date DATE,
    expire_date DATE,
    status VARCHAR(20),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE member_level (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level_code VARCHAR(20) NOT NULL,
    level_name VARCHAR(50) NOT NULL,
    min_points DECIMAL(18,2),
    discount DECIMAL(5,2),
    points_rate INT,
    description TEXT,
    sort_order INT,
    create_time DATETIME
);

CREATE TABLE member_points_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    type VARCHAR(20),
    points DECIMAL(18,2),
    balance DECIMAL(18,2),
    source VARCHAR(50),
    remark TEXT,
    create_time DATETIME,
    FOREIGN KEY (member_id) REFERENCES member(id)
);

-- 索引
CREATE INDEX idx_customer_code ON customer(customer_code);
CREATE INDEX idx_customer_status ON customer(status);
CREATE INDEX idx_supplier_code ON supplier(supplier_code);
CREATE INDEX idx_purchase_order_code ON purchase_order(order_code);
CREATE INDEX idx_product_code ON product(product_code);
CREATE INDEX idx_order_no ON `order`(order_no);
CREATE INDEX idx_member_no ON member(member_no);
CREATE INDEX idx_member_phone ON member(phone);