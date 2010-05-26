package autodox.core;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.easymock.classextension.EasyMock.replay;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.createMock;

import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AutoDoxFileTest {

	@Test
	public void understands_that_any_file_with_the_test_annotation_is_a_test_case() throws Exception {

		IAnnotation aTestAnnotation = aTestAnnotation();
		IType aTestFixtureMock = aTestFixtureMock(aTestAnnotation);
		AutoDoxFile autoDoxFile = new AutoDoxFile(aTestFixtureMock);

		assertThat("AutoDoxFile.isTest() returned false for a test file", autoDoxFile.isTest(), is(true));
		verify(aTestFixtureMock);
	}

	@Test
	public void extracts_names_of_test_cases() throws Exception {

		IAnnotation aTestAnnotation = aTestAnnotation();
		IType aTestFixtureMock = aTestFixtureMock(aTestAnnotation);
		AutoDoxFile autoDoxFile = new AutoDoxFile(aTestFixtureMock);
		
		List<String> testNames = autoDoxFile.tests();

		assertNotNull(testNames);
		assertThat(testNames.contains("a_test_method"), is(true));
		assertThat("a_non_test_method is not a test",testNames.contains("a_non_test_method"), is(false));
		assertThat(testNames.size(), is(1));
		verify(aTestFixtureMock);
	}

	private IType aTestFixtureMock(IAnnotation aTestAnnotation) {
		IType aTestFixtureMock = PowerMock.createMock(IType.class);
		try {
			IMethod aTestCase = aTestCaseMock("a_test_method");
			IMethod aRegularMethod = aRegularMethod("a_non_test_method");
			IMethod[] allMethods = new IMethod[] { aTestCase  , aRegularMethod };
			
			expect(aTestFixtureMock.getMethods()).andReturn(allMethods);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		replay(aTestFixtureMock);
		return aTestFixtureMock;
	}

	private IMethod aRegularMethod(String methodName) {
		return aMethod(false, methodName);
	}

	private IMethod aTestCaseMock(String methodName) {
		return aMethod(true, methodName);
	}

	private IMethod aMethod(boolean isTest, String methodName) {
		IMethod aMethod = createMock(IMethod.class);
		expect(aMethod.getElementName()).andReturn(methodName);
		expect(aMethod.getAnnotation("Test")).andReturn(aTestAnnotation(isTest));
		replay(aMethod);
		return aMethod;
	}

	private IAnnotation aTestAnnotation(boolean isTest) {
		IAnnotation testAnnotation = createMock(IAnnotation.class);

		expect(testAnnotation.exists()).andReturn(isTest).anyTimes();
		replay(testAnnotation);
		return testAnnotation;
	}

	private IAnnotation aTestAnnotation() {
		IAnnotation testAnnotationMock = PowerMock.createMock(IAnnotation.class);
		expect(testAnnotationMock.exists()).andReturn(true).anyTimes();
		replay(testAnnotationMock);
		return testAnnotationMock;
	}

}
