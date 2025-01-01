// package com.github.mustsd;
//
// import cn.hutool.core.io.FileUtil;
// import com.github.mustsd.common.util.GeneratorUtil;
// import com.github.mustsd.common.util.MailUtil;
// import com.github.mustsd.common.util.RedisUtil;
// import com.github.mustsd.modules.code.service.IGeneratorService;
// import com.github.mustsd.modules.code.vo.TableColumn;
// import com.github.mustsd.modules.quartz.service.IQuartzJobService;
// import com.github.mustsd.modules.system.service.ISysPermissionService;
// import lombok.extern.slf4j.Slf4j;
// import org.beetl.core.GroupTemplate;
// import org.beetl.core.Template;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import java.io.File;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// @Slf4j
// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class TankApplicationTests {
//
//  @Autowired RedisUtil redisUtil;
//  @Autowired GroupTemplate groupTemplate;
//
//  @Autowired IGeneratorService generatorService;
//
//  @Autowired
//  ISysPermissionService permissionService;
//
//  @Autowired IQuartzJobService quartzJobService;
//
//  @Autowired MailUtil mailUtil;
//
//  @Test
//  public void test02() throws Exception {
//    mailUtil.sendSimpleMail("18410147796@163.com", "测试邮件", "你好啊");
//  }
//
//  @Test
//  public void test01() {
//    List<TableColumn> columns = generatorService.listColumn("manage", "m_user");
//    Map<String, Object> map = new HashMap<>();
//    map.put("basePackage", TankApplication.class.getPackage().getName());
//    map.put("module", "too");
//    map.put("tableName", "m_account");
//    map.put("desc", "测试模板");
//    map.put("entityName", "MAccount");
//    map.put("columns", columns);
//
//    List<File> tempFiles = GeneratorUtil.loadCodeTemplate();
//    for (File temp : tempFiles) {
//      String tempNameStr = GeneratorUtil.getTargetFilePath(temp);
//      Template nameTemplate = groupTemplate.getTemplate(tempNameStr.replace("\\", "/"));
//      nameTemplate.binding(map);
//      String targetPath = nameTemplate.render();
//      String tempStr = FileUtil.readString(temp, "utf8");
//      Template template = groupTemplate.getTemplate(tempStr);
//      template.binding(map);
//      String str = template.render();
//      FileUtil.writeString(str, targetPath, "utf8");
//    }
//  }
// }
