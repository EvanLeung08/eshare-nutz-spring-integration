package com.evanshare;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.evanshare.main.MainSetup;
import com.evanshare.module.UserModule;


@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class,args={"*js","conf/ioc/","*anno","com.evanshare","*tx"})
@Modules(scanPackage=true)
@Fail("jsp:jsp.500")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
public class MainModule {

}
