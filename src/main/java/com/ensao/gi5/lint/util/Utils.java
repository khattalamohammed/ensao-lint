package com.ensao.gi5.lint.util;

import com.ensao.gi5.lint.rules.violations.Violation;
import com.ensao.gi5.lint.wrapper.CompilationUnitWrapper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

public class Utils {
	Utils() {
		throw new IllegalStateException("not to be instantiated");
	}

	public static String convertFQNToSimpleClassName(String fqn) {
		final String result;
		if (fqn == null) {
			result = null;
		} else {
			int lastIndexOfDot = fqn.lastIndexOf('.');
			if (lastIndexOfDot > 0) {
				result = fqn.substring(lastIndexOfDot + 1);
			} else {
				result = fqn;
			}
		}
		return result;
	}

	public static CompilationUnitWrapper getCompilationUnit(File file) {
		try {
			JavaParser javaParser = new JavaParser();
			ParseResult<CompilationUnit> result = javaParser.parse(file);
			return result.getResult().map(cu -> {
				CompilationUnitWrapper compilationUnitWrapper = new CompilationUnitWrapper(cu, file.getAbsolutePath());
				compilationUnitWrapper.addProblems(result.getProblems());
				return compilationUnitWrapper;
			}).orElse(new CompilationUnitWrapper(new CompilationUnit(), file.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Collection<File> getFilesFromDirectory(String directoryPath) {
		return getFilesFromDirectory(new File(directoryPath));
	}

	public static Collection<File> getFilesFromDirectory(File directory) {
		return FileUtils.listFiles(directory, new String[] { "java" }, true);
	}

	public static void writeCsv(Collection<Violation> violations, Writer writer) throws IOException {
		for (Violation violation : violations) {
			writer.append(violation.toString()).append('\n');
		}
		violations.stream().map(Violation::toString).forEach(t -> {
			try {
				writer.write(t);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.flush();
		writer.close();
	}

	public static void writeJson(Collection<Violation> violations, Writer writer) throws IOException {
		Gson gson = new Gson();
		String obj = gson.toJson(violations);
		writer.append(obj);
		writer.flush();
		writer.close();
	}

	public static void writeHtml(Collection<Violation> violations, Writer writer) throws IOException {
		for (Violation violation : violations) {
			writer.append(violation.toString()).append('\n');
		}
	}

	public static void writeMarkdown(Collection<Violation> violations, Writer writer) throws IOException {
		for (Violation violation : violations) {
			writer.append(violation.toString()).append('\n');
		}
	}
}
