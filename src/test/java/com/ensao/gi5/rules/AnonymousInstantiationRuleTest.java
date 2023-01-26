package com.ensao.gi5.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ensao.gi5.lint.rules.AnonymousInstantiationRule;
import com.ensao.gi5.test.constantes.Paths;
import com.ensao.gi5.test.utils.Utils;

public class AnonymousInstantiationRuleTest {

	@Test
	public void testAnonymousInstantiationRule() {
		int numViolations = Utils.getRuleViolationsNumber(new AnonymousInstantiationRule(),
				new File(Paths.ANONYMOUS_INSTANTIATION_FILE_PATH));
		assertEquals(1, numViolations);
	}
}