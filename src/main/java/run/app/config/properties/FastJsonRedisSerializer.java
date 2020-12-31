package run.app.config.properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/3 16:37
 * Description:阿里自定义序列化工具
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> type;


    public FastJsonRedisSerializer(Class<T> type) {
        super();
        this.type = type;
    }

    @Override
    public byte[] serialize(T t) {
        if (null == t) {

            return new byte[0];
        }
        try {

            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        } catch (SerializationException ex) {
            throw new SerializationException("Something wrong..." + ex.getMessage(), ex);
        }

    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {

            String str = new String(bytes, DEFAULT_CHARSET);
            return (T) JSON.parseObject(str, type);
        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }

    }
}
