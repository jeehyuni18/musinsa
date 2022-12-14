DROP TABLE IF EXISTS point_history;
create table point_history (
                         pointSeq bigint not null auto_increment comment '포인트 일련번호',
                         pointLevel integer not null comment '포인트 레벨',
                         point integer not null comment '포인트 금액',
                         userSeq bigint not null comment '지급 사용자 일련번호' ,
                         regDate datetime default current_timestamp not null comment '지급 시간',
                         primary key (pointSeq),
)