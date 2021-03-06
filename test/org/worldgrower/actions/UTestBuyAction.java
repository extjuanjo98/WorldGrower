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
import org.worldgrower.Args;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.ItemCountMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.generator.Item;

public class UTestBuyAction {

	@Test
	public void testExecuteBuy() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f));
		
		assertEquals(100, performer.getProperty(Constants.GOLD).intValue());
		assertEquals(100, target.getProperty(Constants.GOLD).intValue());
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		Actions.BUY_ACTION.execute(performer, target, new int[] { indexOfWater, 1, 1, Item.WATER.ordinal() }, world);
		
		assertEquals(99, performer.getProperty(Constants.GOLD).intValue());
		assertEquals(101, target.getProperty(Constants.GOLD).intValue());
		assertEquals(0, performer.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER));
		assertEquals(-1, target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER));
	}
	
	@Test
	public void testExecuteDecreaseQuantity() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f), 20);
		
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		Actions.BUY_ACTION.execute(performer, target, new int[] { indexOfWater, 10, 1, Item.WATER.ordinal() }, world);
		
		assertEquals(1, performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WATER));
		assertEquals(19, target.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WATER));
	}
	
	@Test
	public void testIsValidTarget() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		target.setProperty(Constants.CREATURE_TYPE, CreatureType.HUMAN_CREATURE_TYPE);
		assertEquals(true, Actions.BUY_ACTION.isValidTarget(performer, target, world));
		
		target.setProperty(Constants.CREATURE_TYPE, CreatureType.FISH_CREATURE_TYPE);
		assertEquals(false, Actions.BUY_ACTION.isValidTarget(performer, target, world));
	}
	
	@Test
	public void testIsActionPossible() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f));
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		assertEquals(true, Actions.BUY_ACTION.isActionPossible(performer, target, new int[] { indexOfWater, 10, 1 }, world));
	}
	
	@Test
	public void testDistance() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		assertEquals(0, Actions.BUY_ACTION.distance(performer, target, Args.EMPTY, world));
	}
	
	@Test
	public void testIsActionPossibleAlreadySold() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f));
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		target.getProperty(Constants.INVENTORY).remove(indexOfWater);
		assertEquals(false, Actions.BUY_ACTION.isActionPossible(performer, target, new int[] { indexOfWater, 10, 1 }, world));
	}
	
	@Test
	public void testIsActionPossiblePerformerCannotBuyGoods() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		performer.setProperty(Constants.GOLD, 0);
		
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f));
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		assertEquals(false, Actions.BUY_ACTION.isActionPossible(performer, target, new int[] { indexOfWater, 10, 1 }, world));
	}
	
	@Test
	public void testIsActionPossibleInsufficientQuantity() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = createPerformer(2);
		WorldObject target = createPerformer(3);
		performer.setProperty(Constants.GOLD, 1000);
		
		target.getProperty(Constants.INVENTORY).addQuantity(Item.WATER.generate(1f), 5);
		int indexOfWater = target.getProperty(Constants.INVENTORY).getIndexFor(Constants.WATER);
		assertEquals(true, Actions.BUY_ACTION.isActionPossible(performer, target, new int[] { indexOfWater, 10, 1 }, world));
		assertEquals(false, Actions.BUY_ACTION.isActionPossible(performer, target, new int[] { indexOfWater, 10, 10 }, world));
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.INVENTORY, new WorldObjectContainer());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.GOLD, 100);
		performer.setProperty(Constants.ITEMS_SOLD, new ItemCountMap());
		return performer;
	}
}