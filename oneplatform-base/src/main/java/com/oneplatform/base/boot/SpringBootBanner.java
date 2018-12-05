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
package com.oneplatform.base.boot;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年11月30日
 */
public class SpringBootBanner implements Banner {

	private static final String[] BANNER = { "",
			"   ___                _      _    __  ",
			"  / _ \\ _ _  ___ _ __| |__ _| |_ / _|___ _ _ _ __  ",
			" | (_) | ' \\/ -_) '_ \\ / _` |  _|  _/ _ \\ '_| '  \\ ",
			"  \\___/|_||_\\___| .__/_\\__,_|\\__|_| \\___/_| |_|_|_|",
			"                |_|" };

	private static final String SPRING_BOOT = "Jeesuite-libs With Spring Boot";

	private static final int STRAP_LINE_SIZE = 42;

	@Override
	public void printBanner(Environment environment, Class<?> sourceClass,
			PrintStream printStream) {
		for (String line : BANNER) {
			printStream.println(line);
		}
		String version = SpringBootVersion.getVersion();
		version = (version == null ? "" : " (v" + version + ")");
		String padding = "";
		while (padding.length() < STRAP_LINE_SIZE
				- (version.length() + SPRING_BOOT.length())) {
			padding += " ";
		}

		printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT,
				AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version));
		printStream.println();
	}

}
