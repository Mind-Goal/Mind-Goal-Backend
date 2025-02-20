package com.mindgoal.backend.support.annotation;

import com.mindgoal.backend.support.DatabaseCleanerExtension;
import com.mindgoal.backend.support.config.TestConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = TestConfig.class)
@ExtendWith({DatabaseCleanerExtension.class})
@PropertySource("classpath:/application-test.yml")
public @interface ServiceTest {
}
