insert into team(id, NAME) values (1000, 'grepp');
insert into team(id, NAME) values (1001, 'programmers');

insert into team_member(id, TEAM_ID, USER_ID, ROLE, activated)
values(1000, 1000, 'super', 'LEADER', true);
insert into team_member(id, TEAM_ID, USER_ID, ROLE, activated)
values(1001, 1000, 'test', 'MEMBER', true);

insert into team_member(id, TEAM_ID, USER_ID, ROLE, activated)
values(1002, 1001, 'super', 'MEMBER', true);
insert into team_member(id, TEAM_ID, USER_ID, ROLE, activated)
values(1003, 1001, 'test', 'LEADER', true);

insert into book(id,author,amount,category,title,rent_cnt, activated)values(1000,'김애란','3','NOVEL','비행운',5, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1001,'조앤롤링','4','NOVEL','해리포터',4, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1002,'에리히프롬','5','HUMANITY','사랑의기술',3, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1003,'비트겐슈타인','6','HUMANITY','논리철학논고',2, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1004,'황정은','7','NOVEL','디디의우산',10, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1005,'test','7','NOVEL','test',10, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1006,'김애란','3','NOVEL','비행운',5, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1007,'조앤롤링','4','NOVEL','해리포터',4, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1008,'에리히프롬','5','HUMANITY','사랑의기술',3, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1009,'비트겐슈타인','6','HUMANITY','논리철학논고',2, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1010,'황정은','7','NOVEL','디디의우산',10, true);
insert into book(id,author,amount,category,title,rent_cnt, activated)values(1011,'test','7','NOVEL','test',10, true);


insert into rent(id,title,user_id) values(100001,'비행운 외 1권','test');
insert into rent(id,title,user_id) values(100002,'비행운 외 1권','jpa');
insert into rent(id,title,user_id) values(100003,'비행운 외 1권','security');
insert into rent(id,title,user_id) values(100004,'디디의 우산 외 1권','test');
insert into rent(id,title,user_id) values(100005,'논리철학논고 외 1권','test');

insert into rent_book(id,rent_id,book_id,state) values(200001,100001,1000,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200002,100001,1001,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200003,100002,1000,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200004,100002,1001,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200005,100003,1000,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200006,100003,1001,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200007,100004,1004,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200008,100004,1003,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200009,100005,1004,'RENT');
insert into rent_book(id,rent_id,book_id,state) values(200010,100005,1003,'RENT');
