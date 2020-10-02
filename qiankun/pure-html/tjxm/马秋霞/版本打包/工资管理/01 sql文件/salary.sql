alter  table GZGL_SALARY add PROPERTY_FEE VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add HEATING_SUBSIDIES VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add TRAVEL_ALLOWANCE VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add PHONE_ALLOWANCE VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add PUBLIC_TRANSPORTATION VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add UNPAID_LEAVE_ALLOWANCE VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add DOUBLE_PAY VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add MERIT_PAY VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add HEATSTROKE_PREVENTION_SUBSIDY VARCHAR2(50 CHAR)
alter  table GZGL_SALARY add WINTER_HEATING_SUBSIDY VARCHAR2(50 CHAR)

comment on column GZGL_SALARY.property_fee  is '物业费';
comment on column GZGL_SALARY.heating_subsidies  is '采暖补贴';
comment on column GZGL_SALARY.travel_allowance  is '交通补贴';
comment on column GZGL_SALARY.phone_allowance  is '通讯补贴';
comment on column GZGL_SALARY.public_transportation  is '公务交通';
comment on column GZGL_SALARY.unpaid_leave_allowance is '未休假补贴';
comment on column GZGL_SALARY.double_pay  is '双薪';
comment on column GZGL_SALARY.merit_pay  is '绩效奖';
comment on column GZGL_SALARY.heatstroke_prevention_subsidy  is '防暑降温费';
comment on column GZGL_SALARY.winter_heating_subsidy  is '冬季采暖补贴';