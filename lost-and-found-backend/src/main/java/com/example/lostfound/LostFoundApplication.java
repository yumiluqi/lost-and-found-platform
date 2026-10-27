package com.example.lostfound;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@SpringBootApplication
@MapperScan("com.example.lostfound.mapper")
public class LostFoundApplication {
    public static void main(String[] args) {
        SpringApplication.run(LostFoundApplication.class, args);
    }

    @Bean
    public CommandLineRunner printServerUrl(Environment environment) {
        return args -> {
            String ip = getLocalIp();
            String port = environment.getProperty("server.port", "8080");
            System.out.println("========================================");
            System.out.println("  失物招领平台后端已启动，访问地址：");
            System.out.println("  局域网: http://" + ip + ":" + port);
            System.out.println("  本机:   http://localhost:" + port);
            System.out.println("========================================");
        };
    }

    private String getLocalIp() {
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();
                if (nif.isLoopback() || !nif.isUp() || nif.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addrs = nif.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) {
        }
        return "127.0.0.1";
    }
}
