/*var ioc = {
        dataSource : {
            type : "com.alibaba.druid.pool.DruidDataSource",
            events : {
                create : "init",
                depose : 'close'
            },
            fields : {
                url : "jdbc:mysql://127.0.0.1:3306/evanshare",
                username : "root",
                password : "root",
                testWhileIdle : true,
                validationQuery : "select 1" ,
                maxActive : 100
            }
        },
        dao : {
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSource"}]
        }
};*/

var ioc = {
        dataSource : {
            type : "com.alibaba.druid.pool.DruidDataSource",
            events : {
                create : "init",
                depose : 'close'
            },
            fields : {
                url : "jdbc:mysql://192.168.1.81:3306/eam_dev_xyk?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
                username : "root",
                password : "123456",
                testWhileIdle : true,
                validationQuery : "select 1" ,
                maxActive : 100
            }
        },
        dao : {
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSource"}]
        }
};