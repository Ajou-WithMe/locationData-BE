package ajou.withme.locationData.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebConfig {

    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter c = new CommonsRequestLoggingFilter();
        c.setIncludeHeaders(true);
        c.setIncludeQueryString(true);
        c.setIncludePayload(true);
        c.setIncludeClientInfo(true);
        c.setMaxPayloadLength(100000);
        return c;

    }
}
