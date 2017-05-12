-- Create table
create table T_PERSON
(
  ID                  NUMBER(5) not null,
  ID_CARD             VARCHAR2(30) not null,
  PERSON_NAME         VARCHAR2(30) not null,
  SEX                 VARCHAR2(10),
  BIRTHDAY            DATE,
  ADDRESS             VARCHAR2(100),
  RESIDENCE_NO        VARCHAR2(100),
  RESIDENCE_ADDRESS   VARCHAR2(100),
  PERSON_TEL          VARCHAR2(50),
  PERSON_MOBILE       VARCHAR2(50),
  EMAIL               VARCHAR2(100),
  QQ                  VARCHAR2(50),
  RELATIVES_NAME      VARCHAR2(50),
  RELATIVES_TEL       VARCHAR2(50),
  PHOTO               BLOB,
  ENTRY_TIME          DATE,
  DEPT_NAME           VARCHAR2(100),
  POST_NAME           VARCHAR2(50),
  SALARY              NUMBER(5),
  WORK_TEL            VARCHAR2(50),
  WORK_MOBILE         VARCHAR2(50),
  CONTRACT_BEGIN_TIME DATE,
  CONTRACT_END_TIME   DATE,
  REMARK              VARCHAR2(1000),
  SCHOOL_CODE         VARCHAR2(30) not null,
  SCHOOL_NAME         VARCHAR2(100),
  STATE               VARCHAR2(10) not null,
  INPUT_USER_NAME     VARCHAR2(50) default sysdate,
  INPUT_TIME          DATE,
  UPDATE_USER_NAME    VARCHAR2(50),
  UPDATE_TIME         DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table T_PERSON
  is '人员管理';
-- Add comments to the columns 
comment on column T_PERSON.ID
  is '序号 SEQ_PERSON_ID';
comment on column T_PERSON.ID_CARD
  is '身份证号码';
comment on column T_PERSON.PERSON_NAME
  is '姓名';
comment on column T_PERSON.SEX
  is '性别';
comment on column T_PERSON.BIRTHDAY
  is '出生日期';
comment on column T_PERSON.ADDRESS
  is '户籍地址';
comment on column T_PERSON.RESIDENCE_NO
  is '居住证号码';
comment on column T_PERSON.RESIDENCE_ADDRESS
  is '居住地址';
comment on column T_PERSON.PERSON_TEL
  is '个人电话';
comment on column T_PERSON.PERSON_MOBILE
  is '个人手机';
comment on column T_PERSON.EMAIL
  is '邮箱';
comment on column T_PERSON.QQ
  is 'QQ';
comment on column T_PERSON.RELATIVES_NAME
  is '家属姓名';
comment on column T_PERSON.RELATIVES_TEL
  is '家属联系电话';
comment on column T_PERSON.PHOTO
  is '相片';
comment on column T_PERSON.ENTRY_TIME
  is '入职时间';
comment on column T_PERSON.DEPT_NAME
  is '所属部门';
comment on column T_PERSON.POST_NAME
  is '岗位';
comment on column T_PERSON.SALARY
  is '薪水';
comment on column T_PERSON.WORK_TEL
  is '工作电话';
comment on column T_PERSON.WORK_MOBILE
  is '工作手机';
comment on column T_PERSON.CONTRACT_BEGIN_TIME
  is '合同起始日期';
comment on column T_PERSON.CONTRACT_END_TIME
  is '合同截止日期';
comment on column T_PERSON.REMARK
  is '备注';
comment on column T_PERSON.SCHOOL_CODE
  is '驾校代码';
comment on column T_PERSON.SCHOOL_NAME
  is '驾校名称';
comment on column T_PERSON.STATE
  is '状态 1:在职 2:离职';
comment on column T_PERSON.INPUT_USER_NAME
  is '录入人';
comment on column T_PERSON.INPUT_TIME
  is '录入时间';
comment on column T_PERSON.UPDATE_USER_NAME
  is '更新人';
comment on column T_PERSON.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PERSON
  add constraint PK_T_PERSON primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes 
create index IDX_PERSON_IDCARD on T_PERSON (ID_CARD)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
create index IDX_PERSON_NAME on T_PERSON (PERSON_NAME)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
create index IDX_PERSON_SCHOOL on T_PERSON (SCHOOL_CODE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
