import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisOperator {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public long ttl(String key) {
        return this.redisTemplate.getExpire((Object)key);
    }

    public void expire(String key, long timeout) {
        this.redisTemplate.expire((Object)key, timeout, TimeUnit.SECONDS);
    }

    public long incr(String key, long delta) {
        return this.redisTemplate.opsForValue().increment((Object)key, delta);
    }

    public Set<String> keys(String pattern) {
        return this.redisTemplate.keys((Object)pattern);
    }

    public void del(String key) {
        this.redisTemplate.delete((Object)key);
    }

    public void set(String key, String value) {
        this.redisTemplate.opsForValue().set((Object)key, (Object)value);
    }

    public void set(String key, String value, long timeout) {
        this.redisTemplate.opsForValue().set((Object)key, (Object)value, timeout, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return (String)this.redisTemplate.opsForValue().get((Object)key);
    }

    public void hset(String key, String field, Object value) {
        this.redisTemplate.opsForHash().put((Object)key, (Object)field, value);
    }

    public String hget(String key, String field) {
        return (String)this.redisTemplate.opsForHash().get((Object)key, (Object)field);
    }

    public void hdel(String key, Object ... fields) {
        this.redisTemplate.opsForHash().delete((Object)key, fields);
    }

    public Map<Object, Object> hgetall(String key) {
        return this.redisTemplate.opsForHash().entries((Object)key);
    }

    public long lpush(String key, String value) {
        return this.redisTemplate.opsForList().leftPush((Object)key, (Object)value);
    }

    public String lpop(String key) {
        return (String)this.redisTemplate.opsForList().leftPop((Object)key);
    }

    public long rpush(String key, String value) {
        return this.redisTemplate.opsForList().rightPush((Object)key, (Object)value);
    }
}
