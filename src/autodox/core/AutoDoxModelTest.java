package autodox.core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.classextension.EasyMock.*;

public class AutoDoxModelTest {

	@Test
	public void does_not_do_anything_if_not_a_test() throws Exception {
		
		AutoDoxFile autoDoxFileMock = org.easymock.classextension.EasyMock.createMock(AutoDoxFile.class);
		expect(autoDoxFileMock.isTest()).andReturn(false);
		replay(autoDoxFileMock);
		
		AutoDoxModel autoDoxModel = new AutoDoxModel();
		assertThat(autoDoxModel.generateDox(autoDoxFileMock), is(false));
		verify(autoDoxFileMock);
	}
	
	@Test
	public void extracts_all_test_names_from_the_test_file() throws Exception {
		AutoDoxFile autoDoxFileMock = org.easymock.classextension.EasyMock.createMock(AutoDoxFile.class);
		expect(autoDoxFileMock.isTest()).andReturn(true);
		expect(autoDoxFileMock.tests()).andReturn(new ArrayList<String>());
		replay(autoDoxFileMock);
		
		AutoDoxModel autoDoxModel = new AutoDoxModel();
		assertThat(autoDoxModel.generateDox(autoDoxFileMock), is(false));
		
		verify(autoDoxFileMock);
		
		
	}
}
