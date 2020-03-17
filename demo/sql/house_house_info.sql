CREATE TABLE house.house_info
(
    id varchar(15) PRIMARY KEY NOT NULL,
    link varchar(255) COMMENT '链接',
    areaId int(11) NOT NULL COMMENT '地区id',
    districtName varchar(31) COMMENT '所在区名称',
    streetName varchar(31) COMMENT '所在街道名称',
    communityName varchar(50) COMMENT '小区名称',
    subwayInfo varchar(31) COMMENT '附近地铁信息',
    geoHash varchar(63) COMMENT '经纬度的geoHash',
    totalPrice double COMMENT '总价(万元)',
    unitPrice double COMMENT '单价/㎡',
    houseType varchar(31) COMMENT '房屋户型',
    area double COMMENT '建筑面积',
    houseLevel varchar(31) COMMENT '所在楼层',
    decoration varchar(15) COMMENT '装修情况',
    forword varchar(31) COMMENT '房屋朝向',
    createTime date,
    updateTime timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    isDelete int(11) DEFAULT '0'
);
CREATE UNIQUE INDEX house_Info_id_uindex ON house.house_info (id);