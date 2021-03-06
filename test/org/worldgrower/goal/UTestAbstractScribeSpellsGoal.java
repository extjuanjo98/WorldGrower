package org.worldgrower.goal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.magic.ScribeMagicSpellAction;
import org.worldgrower.attribute.WorldObjectContainer;

public class UTestAbstractScribeSpellsGoal {

	@Test
	public void testGetKnownSpellsInInventory() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.INVENTORY, new WorldObjectContainer());
		new ScribeMagicSpellAction(Actions.MINOR_HEAL_ACTION).execute(performer, performer, Args.EMPTY, world);
		
		assertEquals(1, AbstractScribeSpellsGoal.getKnownSpellsInInventory(performer, Actions.MINOR_HEAL_ACTION).size());
	}
}
