package me.laudukang.persistence;

import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.config.MvcConfig;
import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lau on 2016/1/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, MvcConfig.class, PersistenceJPAConfig.class})
public class ApplicationTest {
    // see http://websystique.com/springmvc/spring-4-mvc-and-hibernate4-integration-testing-example-using-annotations/
    // 2016年1月22日15:50:23
    // laudukang
    @Test
    public void sampleTestCase() {
    }
}
