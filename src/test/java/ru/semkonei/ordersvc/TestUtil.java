package ru.semkonei.ordersvc;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.semkonei.ordersvc.model.User;

public class TestUtil {
    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors
                .httpBasic(user.getEmail(), user.getPassword());
    }
}
