/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.generator.Item;

public class UTestPoisonInventoryWaterWithSleepingPotionAction {

	@Test
	public void testExecuteLastPoison() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		WorldObjectContainer performerInventory = performer.getProperty(Constants.INVENTORY);
		performerInventory.addQuantity(Item.WATER.generate(1f));
		performerInventory.addQuantity(Item.SLEEPING_POTION.generate(1f));
		
		assertEquals(false, performerInventory.get(0).hasProperty(Constants.SLEEP_INDUCING_DRUG_STRENGTH));
		Actions.POISON_INVENTORY_WATER_WITH_SLEEPING_POTION_ACTION.execute(performer, performer, new int[] {0}, world);
		
		assertEquals(1, performerInventory.getQuantityFor(Constants.SLEEP_INDUCING_DRUG_STRENGTH));
		int indexOfPoison = performerInventory.getIndexFor(Constants.SLEEP_INDUCING_DRUG_STRENGTH);
		assertEquals(0, indexOfPoison);
		assertEquals(true, performerInventory.get(indexOfPoison).hasProperty(Constants.WATER));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		WorldObject target = TestUtils.createSkilledWorldObject(3, Constants.INVENTORY, new WorldObjectContainer());
		
		assertEquals(true, Actions.POISON_INVENTORY_WATER_WITH_SLEEPING_POTION_ACTION.isValidTarget(performer, performer, world));
		assertEquals(false, Actions.POISON_INVENTORY_WATER_WITH_SLEEPING_POTION_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createSkilledWorldObject(2, Constants.INVENTORY, new WorldObjectContainer());
		WorldObjectContainer performerInventory = performer.getProperty(Constants.INVENTORY);
		performerInventory.addQuantity(Item.WATER.generate(1f));
		performerInventory.addQuantity(Item.SLEEPING_POTION.generate(1f));
		
		assertEquals(true, Actions.POISON_INVENTORY_WATER_WITH_SLEEPING_POTION_ACTION.isActionPossible(performer, performer, new int[] {0}, world));
	}
}