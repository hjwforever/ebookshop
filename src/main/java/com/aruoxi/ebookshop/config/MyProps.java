package com.aruoxi.ebookshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="ebookshop")
@Data
public class MyProps {
	private String appId;
	private String appKey;
	private String[] arrayProps;
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
	private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
	private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
}