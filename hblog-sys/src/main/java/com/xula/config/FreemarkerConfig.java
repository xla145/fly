package com.xula.config;

import com.yuelinghui.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 自定义freemaker 标签
 * @author xla
 */
@Component
public class FreemarkerConfig {

  @Autowired
  private Configuration configuration;

  @PostConstruct
  public void setSharedVariable() {
    configuration.setSharedVariable("shiro", new ShiroTags());
  }

}