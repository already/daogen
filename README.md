原版本是http://code.google.com/p/codeauto/. 前同事根据公司业务做的一个数据库代码反向生成工具，运用jdbc获取的元数据来生成
相关dao成代码，和sql，ibatis的sqlmap。拥有功能增对mysql，oracle 生成一些文件，包括dao，sqlmap文件，测试用例，工程文件等

我感觉codeauto比较重，做了一个减法，适用人群为中小项目（mysql），想偷懒的coder。；》谢谢叶大。

生成代码运行（Obj2DbGen（正向生成，基于pojo+注解） Gen（反向生成，基于数据表））
默认生成在 project/gendir/dao/ibatisdao/src/main/
                                              |---->java/code
                                              |---->resources/sql
                                                 |->resources/sqlmap


daogen v2.
======
 v2.增加代码工具正向生成功能，pojo类+注解 ，生成ibatis的sqlmap文件、 dao、 pojo、 sql
脚本，保留版本v1的代码定制功能，相关的pojo类需要写在类包com.kifanle.daogen.obj2db.pojos下。
有两注解Table和Column。运行类Obj2DbGen即可。代码定制方法参照v1.版本说明。
例子如下
@Table(engine=Engine.INNODB,charset=Charset.UTF8)
public class User {
@Column(autoInc=true,comment="主键",primaryKey=true,nullable=false)
private int id = 2678;
@Column(length=255,comment="姓名")
private String name;
@Column(length=255,comment="昵称")
private String nickName;
@Column(length=255,comment="家庭住址",datatype=Datatype.Text,nullable=true)
private String address;
@Column(defaultVal="0",comment="测试字段iid",length=20,nullable=true)
private Long iid;
@Column(defaultVal="0",comment="测试字段iid2",length=20,nullable=false)
private Long iid2;
@Column(length=100,comment="邮件",nullable=false)
private String email;
@Column(length=4,comment="性别",nullable=false,defaultVal="0")
private byte gender;
}

======                     
daogen v1.
======
主要借用了codeauto的元数据解析那块的代码。
ibatis db反向生成工具。包括dao sqlmap文件
codeauto 的区别，去掉oracle的区别，重写了部分sql生成的vm模板。
增对表的增删改查，分页，和特殊方法查询，增加了TableConfig类，增对代码生成方面做到细粒度定制。

======

简单实用教程。
可以在db.properties里配置
DB_TYPE = Mysql
DB_SERVER = 127.0.0.1
DB_NAME = chifan
DB_USER = root
DB_PWD = root
JAVA_PACKAGE = com.kifanle.order

在Gen这个类，直接执行，会在gendir里生成相关代码。

在Gen可以找到如下代码，进行配置。
/**
* 对于dao的生成，暂时特殊定制，硬编码。key 为数据库表名，暂不全量配置
*/
static{
tconfig.put("userinfo", TableConfig.build("userinfo")
.setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
.addQueryMethodAndCol("getUserInfoByEmail", new String[]{"mobile","gender"})
//.addQueryMethodAndCol("getUserInfoByEmailList", new String[]{"mobile"})
);
// tconfig.put("passport", TableConfig.build("passport")
// .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
// .addQueryMethodAndCol("getUserPassportByEmail", new String[]{"email"})
// .addQueryMethodAndCol("getUserPassportByMobile", new String[]{"mobile"})
// .addQueryMethodAndCol("getUserPassportByLoginname", new String[]{"loginname"})
// .addQueryMethodAndCol("getUserPassportByNickname", new String[]{"nickname"})
// );
}

其中userinfo 代表数据库中的表名，对应每个表，都有自己的TableConfig，可以设置增删改查，和分页功能，
还可以通过addQueryMethodAndCol设置特殊方法，“getUserPassportByEmail”代表方法，email 代表数据库中的column

===============================
以后会有更多小功能加入。。：》
