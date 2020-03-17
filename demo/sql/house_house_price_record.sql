CREATE TABLE house.house_price_record
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    houseId varchar(15) NOT NULL,
    totalPrice double COMMENT '总价',
    unitPrice double COMMENT '每平方价格',
    isDelete int(11) DEFAULT '0',
    createTime date,
    updateTime timestamp
);