insert into T_USER(id,name,password,create_time,update_time) values(1,'admin','123456',now(), now());
insert into T_USER(id,name,password,create_time,update_time) values(2,'user','123456',now(), now());


insert into T_ROLE(rid,name,desc) values (1,'ADMIN','管理员');
insert into T_ROLE(rid,name,desc) values (2,'NORMAL','普通用户');

insert into user_role(uid,role_id) values (1,1);
insert into user_role(uid,role_id) values (1,2);
insert into user_role(uid,role_id) values (2,2);

insert into T_PERMISSION(id,name,desc,url,pid) values (3,'普通用户的url','允许普通用户和管理员访问','/hello/helloUser',null );
insert into T_PERMISSION(id,name,desc,url,pid) values (4,'管理员的url','允许普通用户和管理员访问','/hello/helloAdmin',null );


insert into role_perm(perm_id,role_id) values (3,1);
insert into role_perm(perm_id,role_id) values (3,2);
insert into role_perm(perm_id,role_id) values (4,1);
