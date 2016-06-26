package com.evanshare;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.evanshare.main.MainSetup;
import com.evanshare.module.UserModule;


@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class,args={"*js","config/ioc/","*anno","com.evanshare","*tx"})
@Modules(scanPackage=true)
public class MainModule {

}
