var ioc = {
		conf : {
            type : "org.nutz.ioc.impl.PropertiesProxy",
            fields : {
                paths : ["custom/db.properties"]
            }
        },
        dataSource : {
            type : "com.alibaba.druid.pool.DruidDataSource",
            events : {
                create : "init",
                depose : 'close'
            },
            fields : {
                url : {java:"$conf.get('db.url')"},
                username : {java:"$conf.get('db.username')"},
                password : {java:"$conf.get('db.password')"},
                testWhileIdle : true,
                validationQuery : {java:"$conf.get('db.validationQuery')"} ,
                maxActive : {java:"$conf.get('db.maxActive')"},
                filters : "mergeStat",
                connectionProperties : "druid.stat.slowSqlMillis=2000"
            }
        },
        dao : {
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSource"}]
        }
};

/*var ioc = {
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
};*/