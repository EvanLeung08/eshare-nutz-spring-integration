var ioc = {
		conf : {
            type : "org.nutz.ioc.impl.PropertiesProxy",
            fields : {
                paths : ["conf/custom/db.properties"]
            }
        },
        dataSource : {
            type : "com.alibaba.druid.pool.DruidDataSource",
            events : {
                create : "init",
                depose : 'close'
            },
            fields : {
                driverClassName : {java :"$conf.get('db.driver')"},
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