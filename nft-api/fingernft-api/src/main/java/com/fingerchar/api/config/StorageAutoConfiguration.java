package com.fingerchar.api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fingerchar.api.service.StorageService;
import com.fingerchar.core.storage.IpfsStorage;
import com.fingerchar.core.storage.LocalStorage;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

    private final StorageProperties properties;

    public StorageAutoConfiguration(StorageProperties properties) {
        this.properties = properties;
    }

    @Bean
    public StorageService storageService() {
        StorageService storageService = new StorageService();
        String active = this.properties.getActive();
        storageService.setActive(active);
        if (active.equals("local")) {
            storageService.setStorage(localStorage());
        } else if (active.equals("ipfs")) {
        	storageService.setStorage(ipfsStorage());
        } else {
            throw new RuntimeException("当前存储模式 " + active + " 不支持");
        }

        return storageService;
    }

    @Bean
    public LocalStorage localStorage() {
        LocalStorage localStorage = new LocalStorage();
        StorageProperties.Local local = this.properties.getLocal();
        localStorage.setAddress(local.getAddress());
        localStorage.setStoragePath(local.getStoragePath());
        return localStorage;
    }

    @Bean
    public IpfsStorage ipfsStorage() {
    	IpfsStorage ipfsStorage = new IpfsStorage();
        StorageProperties.Ipfs ipfs = this.properties.getIpfs();
        ipfsStorage.setHost(ipfs.getHost());
        ipfsStorage.setPort(ipfs.getPort());
        ipfsStorage.setLoclLocation(ipfs.getLocalPath());
        ipfsStorage.setRequestBase(ipfs.getRequestBase());
        ipfsStorage.setRemoteService(ipfs.getRemoteService());
        return ipfsStorage;
    }

}
