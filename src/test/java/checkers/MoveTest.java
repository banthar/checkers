package checkers;

import java.awt.Point;

import junit.framework.Assert;

import org.junit.Test;

public class MoveTest {

	@Test
	public void testToString() throws Exception {
		Assert.assertEquals("A1-A1",
				new Move(new Point(), new Point(), false).toString());
	}

}
