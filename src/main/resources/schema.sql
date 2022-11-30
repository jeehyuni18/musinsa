DROP TABLE IF EXISTS point_history;
DROP TABLE IF EXISTS user_event_complete_log;
CREATE TABLE point_history (
                         pointSeq bigint not null auto_increment comment '포인트 일련번호',
                         pointLevel integer not null comment '포인트 레벨',
                         point integer not null comment '포인트 금액',
                         userSeq bigint not null comment '지급 사용자 일련번호' ,
                         regDate date default current_date not null comment '지급 날짜',
                         primary key (pointSeq)
);
CREATE INDEX idx_point_history_01 ON point_history (userSeq);
CREATE INDEX idx_point_history_02 ON point_history (regDate);


CREATE TABLE user_event_complete_log (
                               completeLogSeq bigint not null auto_increment comment '사용자 이벤트 완료로그 일련번호',
                               userSeq bigint not null comment '지급 사용자 일련번호' ,
                               lastEventCompleteDate date default current_date not null comment '완료 날짜',
                               primary key (completeLogSeq)
);
CREATE INDEX idx_user_event_complete_log_01 ON user_event_complete_log (userSeq);
CREATE INDEX idx_user_event_complete_log_02 ON user_event_complete_log (lastEventCompleteDate);