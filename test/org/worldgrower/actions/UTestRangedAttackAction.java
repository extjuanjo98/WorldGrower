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
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.generator.CommonerGenerator;
import org.worldgrower.generator.Item;
import org.worldgrower.goal.GroupPropertyUtils;
import org.worldgrower.gui.CommonerImageIds;

public class UTestRangedAttackAction {

	private final CommonerGenerator commonerGenerator = new CommonerGenerator(666, new CommonerImageIds(), new MockCommonerNameGenerator());
	
	@Test
	public void testExecute() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		assertEquals(26, target.getProperty(Constants.HIT_POINTS).intValue());
		Actions.RANGED_ATTACK_ACTION.execute(performer, target, new int[0], world);
		
		assertEquals(24, target.getProperty(Constants.HIT_POINTS).intValue());
	}
	
	@Test
	public void testDistanceNoRangedWeapon() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		assertEquals(1, Actions.RANGED_ATTACK_ACTION.distance(performer, target, new int[0], world));
	}
	
	@Test
	public void testDistanceMeleeWeapon() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.IRON_GREATAXE.generate(1f));
		
		assertEquals(1, Actions.RANGED_ATTACK_ACTION.distance(performer, target, new int[0], world));
	}
	
	@Test
	public void testDistanceNextToTarget() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.LONGBOW.generate(1f));
		
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		target.setProperty(Constants.X, 0);
		target.setProperty(Constants.Y, 1);
		
		assertEquals(0, Actions.RANGED_ATTACK_ACTION.distance(performer, target, new int[0], world));
	}
	
	@Test
	public void testDistanceAwayFromTarget() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject organization = GroupPropertyUtils.create(null, "TestOrg", world);
		WorldObject performer = createPerformer(world, organization);
		WorldObject target = createPerformer(world, organization);
		
		performer.setProperty(Constants.LEFT_HAND_EQUIPMENT, Item.LONGBOW.generate(1f));
		
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		target.setProperty(Constants.X, 0);
		target.setProperty(Constants.Y, 20);
		
		assertEquals(16, Actions.RANGED_ATTACK_ACTION.distance(performer, target, new int[0], world));
	}
	
	private WorldObject createPerformer(World world, WorldObject organization) {
		int performerId = commonerGenerator.generateCommoner(0, 0, world, organization);
		WorldObject performer = world.findWorldObject(Constants.ID, performerId);
		return performer;
	}
}