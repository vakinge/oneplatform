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
package com.oneplatform.common.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.SelectOption;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.filesystem.FileSystemClient;
import com.jeesuite.filesystem.UploadTokenParam;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor.PageDataLoader;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.PageResult;
import com.oneplatform.base.model.UploadResult;
import com.oneplatform.common.dao.entity.UploadFileEntity;
import com.oneplatform.common.dao.mapper.UploadFileEntityMapper;
import com.oneplatform.common.dto.param.UploadCallbackParam;
import com.oneplatform.common.dto.param.UploadQueryParam;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 文件上传接口
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月24日
 */
@Controller
@RequestMapping("/file")
public class FileController implements InitializingBean{

	private @Autowired UploadFileEntityMapper uploadFileMapper;
	
	private static List<String> allow_upload_suffix = new ArrayList<>(Arrays.asList("png","jpg","zip","pdf","xls","xlsx","rar","doc","docx"));
	private List<String> uploadGroups = new ArrayList<>();
	private List<String> uploadProviders = new ArrayList<>();
	
	@RequestMapping(value = "uptoken", method = RequestMethod.GET)
	@ApiOperation(value = "生成上传凭证")
	@ApiImplicitParams({
		 @ApiImplicitParam(paramType="query",name="appId",dataType="String",required=true,value="应用ID"),
		 @ApiImplicitParam(paramType="query",name="group",dataType="String",required=true,value="对应配置中心配置上传组名(如：public，private等)"),
		 @ApiImplicitParam(paramType="query",name="fileName",dataType="String",required=false,value="上传文件名(覆盖上传必填)"),
		 @ApiImplicitParam(paramType="query",name="uploadDir",dataType="String",required=false,value="上传目录"),
		 @ApiImplicitParam(paramType="query",name="deleteAfterDays",dataType="Integer",required=false,value="上传多少天自动删除")
	})
	public @ResponseBody Map<String, Object> getUploadToken(HttpServletResponse response
			,@RequestParam(value="appId") String appId
			,@RequestParam(value="group") String group
			,@RequestParam(value="fileName",required = false) String fileName
			,@RequestParam(value="uploadDir") String uploadDir
			,@RequestParam(value="deleteAfterDays",required = false) Integer deleteAfterDays) {
		
		//TODO 验证 appid
		
//		if("redirect".equals(group)){
//			Map<String, Object> map = new HashMap<>();
//			map.put("uptoken", "12345556f");
//			map.put("uploadDir", uploadDir);
//			return map;
//		}
		UploadTokenParam param = new UploadTokenParam();
		param.setFileName(fileName);
		param.setUploadDir(uploadDir);
		param.setDeleteAfterDays(deleteAfterDays);
		Map<String, Object> map = FileSystemClient.getClient(group).createUploadToken(param);
		map.put("appId", appId);
		return map;
	}
	
	@ApiOperation(value = "上传成功回调")
	@RequestMapping(value = "upload_callback", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> uploadCallback(@RequestBody UploadCallbackParam param){
		//TODO
		UploadFileEntity entity = new UploadFileEntity();
		entity.setAppId(param.getAppId());
		entity.setFileName(param.getFileName());
		entity.setFileUrl(param.getUrl());
		entity.setFileLocation(param.getLocation());
		entity.setMimeType(param.getMimeType());
		entity.setGroupName(param.getGroup());
		entity.setProvider(param.getProvider());
		entity.setCreatedAt(new Date());
		uploadFileMapper.insertSelective(entity);
		return new WrapperResponse<>();
	}
	
	@ApiOperation(value = "获取文件地址", notes = "获取文件地址")
	@RequestMapping(value = "geturl", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<String> getUploadFileUrl(HttpServletResponse response
			,@RequestParam("group") String group
			,@RequestParam(value="file") String fileKey) {
		
		String downloadUrl = FileSystemClient.getClient(group).getDownloadUrl(fileKey);
		return new WrapperResponse<>(downloadUrl);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ApiOperation(value="上传文件")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="appId",dataType="String",required=true,value="应用ID"),
		 @ApiImplicitParam(paramType="form",name="group",dataType="String",required=true,value="对应配置中心配置上传组名(如：public，private等)"),
		 @ApiImplicitParam(paramType="form",name="fileName",dataType="String",required=true,value="文件原名称"),
		 @ApiImplicitParam(paramType="form",name="file",dataType="File",required=true,value="文件内容")
	})
	public @ResponseBody UploadResult uploadConfigFile(@RequestParam("file") MultipartFile file,@RequestParam(value="appId") String appId,@RequestParam("group") String group,@RequestParam("fileName") String originFileName){
		try {
			
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			if(!allow_upload_suffix.contains(suffix)){
				throw new JeesuiteBaseException(9999, "不允许上传该文件类型");
			}
			String fileKey = UUID.randomUUID().toString() + "."+suffix;
			//file.transferTo(new File("/Users/jiangwei/"+fileKey));
			
			FileSystemClient client = FileSystemClient.getClient(group);
			String url = client.upload(fileKey, file.getBytes());
			
			UploadFileEntity entity = new UploadFileEntity();
			entity.setAppId(appId);
			entity.setGroupName(group);
			entity.setFileName(originFileName);
			entity.setFileUrl(url);
			entity.setMimeType(file.getContentType());
			entity.setProvider(client.getProvider().name());
			entity.setCreatedAt(new Date());
			uploadFileMapper.insertSelective(entity);
            
			return new UploadResult(url, originFileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JeesuiteBaseException(ExceptionCode.SYSTEM_ERROR.code, "上传失败");
		}
	}
	
	@ApiOperation(value = "查询所有上传通道")
	@RequestMapping(value = "groups", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> getAllGroups() {
		List<SelectOption> result = new ArrayList<>();
		for (String group : uploadGroups) {
			result.add(new SelectOption(group, group));
		}
		return new WrapperResponse<>(result);
	}
	
	@ApiOperation(value = "查询所有上传服务供应商")
	@RequestMapping(value = "providers", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<SelectOption>> getAllProviders() {
		List<SelectOption> result = new ArrayList<>();
		for (String group : uploadProviders) {
			result.add(new SelectOption(group, group));
		}
		return new WrapperResponse<>(result);
	}
	
	@ApiOperation(value = "分页查询上传文件信息")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public @ResponseBody PageResult<UploadFileEntity> pageQueryUploadFiles(@ModelAttribute UploadQueryParam param){
		Page<UploadFileEntity> page = PageExecutor.pagination(param, new PageDataLoader<UploadFileEntity>() {
			@Override
			public List<UploadFileEntity> load() {
				return uploadFileMapper.findByParam(param);
			}
		});
		return new PageResult<UploadFileEntity>(page.getPageNo(), page.getPageSize(), page.getTotal(), page.getData());
	}
	
	
	@ApiOperation(value = "获取文件地址", notes = "获取文件地址")
	@RequestMapping(value = "geturl/{id}", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<String> getUploadFileUrlById(@PathVariable("id") int id) {
		UploadFileEntity uploadFileEntity = uploadFileMapper.selectByPrimaryKey(id);
		String downloadUrl = null;
		if(uploadFileEntity != null){			
			downloadUrl = FileSystemClient.getClient(uploadFileEntity.getGroupName()).getDownloadUrl(uploadFileEntity.getFileUrl());
		}else{
			throw new JeesuiteBaseException(4001, "文件不存在");
		}
		return new WrapperResponse<>(downloadUrl);
	}
	
	@ApiOperation(value = "删除文件")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody WrapperResponse<String> deleteFile(@PathVariable("id") int id) {
		
		UploadFileEntity uploadFileEntity = uploadFileMapper.selectByPrimaryKey(id);
		if(uploadFileEntity != null){
			FileSystemClient.getClient(uploadFileEntity.getGroupName()).delete(uploadFileEntity.getFileUrl());
		}
		return new WrapperResponse<>();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Properties properties = ResourceUtils.getAllProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			if(entry.getKey().toString().endsWith(".filesystem.provider")){
				uploadGroups.add(entry.getKey().toString().split("\\.")[0]);
				if(!uploadProviders.contains(entry.getValue().toString()))uploadProviders.add(entry.getValue().toString());
			}
		}
	}

}
