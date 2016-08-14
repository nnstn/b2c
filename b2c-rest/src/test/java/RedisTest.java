import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;


public class RedisTest {
	@Test
	public void redisSingle(){
		Jedis jedis = new Jedis("192.168.128.128", 7002);
		jedis.set("name", "hhahahah");
		String name = jedis.get("name");
		jedis.close();
		System.out.println(name);
	}
	@Test
	public void redisPool(){
		JedisPool jedisPool = new JedisPool("192.168.128.128", 7002);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("mymap", "num1", "bbabavalue1");
		String hget = jedis.hget("mymap", "num1");
		System.out.println(hget);
		jedis.close();
	}
	@Test
	public void redisCluster(){
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.128.128", 7001));
		nodes.add(new HostAndPort("192.168.128.128", 7002));
		nodes.add(new HostAndPort("192.168.128.128", 7003));
		nodes.add(new HostAndPort("192.168.128.128", 7004));
		nodes.add(new HostAndPort("192.168.128.128", 7005));
		nodes.add(new HostAndPort("192.168.128.128", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.hset("mymap1", "num1","12312321");
		String hget = cluster.hget("mymap1", "num1");
		System.out.println(hget);
	}
}
