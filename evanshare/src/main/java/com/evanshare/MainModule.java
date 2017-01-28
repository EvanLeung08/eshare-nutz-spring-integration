package com.evanshare;

import com.evanshare.main.MainSetup;
import com.evanshare.provider.SpringIocProvider;
import org.nutz.mvc.annotation.*;


@ChainBy(args="conf/mvc/evanshare-mvc-chain.js")
@SetupBy(value=MainSetup.class)
//@IocBy(type=ComboIocProvider.class,args={"*js","conf/ioc/","*anno","com.evanshare","*tx"})
@IocBy(args = {} ,type = SpringIocProvider.class)
@Modules(scanPackage=true)
@Fail("jsp:jsp.500")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
public class MainModule {
	

}
