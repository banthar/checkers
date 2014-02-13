package checkers;

import junit.framework.Assert;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void positive() throws Exception {
		Assert.assertEquals(1, Utils.sign(10));
	}

	@Test
	public void negative() throws Exception {
		Assert.assertEquals(-1, Utils.sign(-10));
	}

	@Test
	public void zero() throws Exception {
		Assert.assertEquals(0, Utils.sign(0));
	}

}
