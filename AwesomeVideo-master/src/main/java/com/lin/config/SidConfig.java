import org.n3r.idworker.Sid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SidConfig {
    @Bean
    public Sid sid() {
        return new Sid();
    }
}
