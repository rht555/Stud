##github实验

##资料
www.spring.io
https://developer.github.com/apps/building-github-apps/creating-a-github-app/
##工具
https://git-scm.com

##脚本
```sql
create table user
(
    id           int auto_increment
        primary key,
    account_id   varchar(100) null,
    name         varchar(50)  null,
    token        char(36)     null,
    gmt_create   bigint       null,
    gmt_modified bigint       null
);


```