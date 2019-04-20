/*
 * Copyright 2016-2018 www.jeesuite.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.smartapi.ext;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jeesuite.smartapi.core.staticapi.variables.SystemVariableHanlder;
import com.jeesuite.smartapi.core.staticapi.variables.SystemVariableProvider;
import com.oneplatform.base.LoginContext;

/**
 * 默认系统变量提供者
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年11月7日
 */
@Component
public class DefaultSystemVariableProvider implements SystemVariableProvider {

	@Override
	public Serializable currentLoginUserId() {
		return LoginContext.getLoginUserId();
	}

	@Override
	public String currentLoginUserName() {
		return LoginContext.getAndValidateLoginUser().getUserName();
	}

	@Override
	public List<SystemVariableHanlder> custom() {
		return null;
	}

}
