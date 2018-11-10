/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.base.util;

import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.io.CharStreams;
import com.oneplatform.base.GlobalContants;
import com.oneplatform.base.GlobalContants.ModuleType;
import com.oneplatform.base.annotation.ApiScanIgnore;
import com.oneplatform.base.model.ApiInfo;
import com.oneplatform.base.model.ModuleMetadata;

import io.swagger.annotations.ApiOperation;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月17日
 */
public class ModuleMetadataHolder {

	private static List<ModuleMetadata> metadatas = new ArrayList<>();
	
	private synchronized static void scanApiInfos(ModuleMetadata metadata) {

		if (!metadata.getApis().isEmpty())
			return;
		String scanBasePackage = metadata.getApiBasePackages();
		
		if(StringUtils.isBlank(scanBasePackage)){
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase("oneplatform")){
				scanBasePackage = "com.oneplatform.system.controller";
			}else if(ModuleType.service.name().equals(metadata.getType())){
				String[] parts = StringUtils.split(ModuleMetadataHolder.class.getPackage().getName(),".");
				scanBasePackage = parts[0] + "." + parts[1];
			}else{
				return;
			}
		}
		

		String classPattern = "/**/*.class";
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

		try {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(scanBasePackage) + classPattern;
			org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

			Method[] methods;
			String baseUri;
			ApiInfo apiInfo;
			for (org.springframework.core.io.Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					Class<?> clazz = Class.forName(className);
					if(clazz.isAnnotationPresent(ApiScanIgnore.class))continue;
					if (clazz.isAnnotationPresent(Controller.class)
							|| clazz.isAnnotationPresent(RestController.class)) {
						if (clazz.isAnnotationPresent(RequestMapping.class)) {
							baseUri = clazz.getAnnotation(RequestMapping.class).value()[0];
							if (!baseUri.startsWith("/"))
								baseUri = "/" + baseUri;
							if (baseUri.endsWith("/"))
								baseUri = baseUri.substring(0, baseUri.length() - 1);
						} else {
							baseUri = "";
						}
						methods = clazz.getDeclaredMethods();
						methodLoop: for (Method method : methods) {
							if(method.isAnnotationPresent(ApiScanIgnore.class))continue methodLoop;
							if (!method.isAnnotationPresent(RequestMapping.class))
								continue methodLoop;
							RequestMapping annotation = method.getAnnotation(RequestMapping.class);
							apiInfo = new ApiInfo();
							String methodUri = annotation.value()[0];
							if (!methodUri.startsWith("/"))
								methodUri = "/" + methodUri;
							apiInfo.setUrl(baseUri + methodUri);
							if (annotation.method() != null && annotation.method().length > 0) {
								apiInfo.setMethod(annotation.method()[0].name());
							}
							if (method.isAnnotationPresent(ApiOperation.class)) {
								apiInfo.setName(method.getAnnotation(ApiOperation.class).value());
							} else {
								apiInfo.setName(apiInfo.getUrl());
							}
							
							apiInfo.setKey(clazz.getName() + "." + method.getName());

							metadata.getApis().add(apiInfo);
						}
					}
				}
			}
		} catch (Exception e) {
		}

	}


	private static void loadModuleInfo() {
		try {
			if(GlobalContants.MODULE_NAME.equalsIgnoreCase("oneplatform")){
				ModuleMetadata metadata = new ModuleMetadata();
				metadata.setType(ModuleType.service.name());
				metadata.setIdentifier(GlobalContants.MODULE_NAME);
				scanApiInfos(metadata);
				metadatas.add(metadata);
			}
			DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath*:metadata.json");
			if(resources == null)return;
			for (Resource resource : resources) {
				String contents = CharStreams.toString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
				ModuleMetadata metadata = JSON.parseObject(contents, ModuleMetadata.class);
				scanApiInfos(metadata);
				metadatas.add(metadata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<ModuleMetadata> getMetadatas() {
		if(!metadatas.isEmpty())return metadatas;
		synchronized (ModuleMetadataHolder.class) {
			if(!metadatas.isEmpty())return metadatas;
			loadModuleInfo();
		}
     return metadatas;
	}

}
