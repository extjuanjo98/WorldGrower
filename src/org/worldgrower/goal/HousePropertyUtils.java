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
package org.worldgrower.goal;

import java.util.ArrayList;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.BuildingType;
import org.worldgrower.generator.Item;

public class HousePropertyUtils {

	public static List<WorldObject> getHousingOfOwners(List<WorldObject> owners, World world) {
		List<WorldObject> result = new ArrayList<>();
		for(WorldObject owner : owners) {
			if (owner.hasProperty(Constants.BUILDINGS) && owner.getProperty(Constants.BUILDINGS) != null) {
				List<Integer> houseIds = owner.getProperty(Constants.BUILDINGS).getIds(BuildingType.SHACK, BuildingType.HOUSE);
				for(int houseId : houseIds) {
					result.add(world.findWorldObject(Constants.ID, houseId));
				}
			}
		}
		
		return result;
	}

	public static WorldObject getBestHouse(WorldObject performer, World world) {
		int bestId = -1;
		int bestValue = Integer.MIN_VALUE;
		List<Integer> houseIds = performer.getProperty(Constants.BUILDINGS).getIds(BuildingType.SHACK, BuildingType.HOUSE);
		for(int houseId : houseIds) {
			WorldObject house = world.findWorldObject(Constants.ID, houseId);
			int sleepComfort = house.getProperty(Constants.SLEEP_COMFORT);
			if (sleepComfort > bestValue) {
				bestId = houseId;
				bestValue = sleepComfort;
			}
		}
		
		if (bestId != -1) {
			return world.findWorldObject(Constants.ID, bestId);
		} else {
			return null;
		}
	}
	
	public static boolean hasHouses(WorldObject performer) {
		return performer.hasProperty(Constants.BUILDINGS) && performer.getProperty(Constants.BUILDINGS).getIds(BuildingType.HOUSE).size() > 0;
	}

	public static boolean hasHouseForSale(WorldObject target, World world) {
		WorldObject houseForSale = getHouseForSale(target, world);
		return houseForSale != null;
	}

	public static WorldObject getHouseForSale(WorldObject target, World world) {
		if (target.hasProperty(Constants.BUILDINGS)) {
			List<Integer> houseIds = target.getProperty(Constants.BUILDINGS).getIds(BuildingType.HOUSE);
			for(int houseId : houseIds) {
				WorldObject house = world.findWorldObject(Constants.ID, houseId);
				if (house.hasProperty(Constants.SELLABLE) && house.getProperty(Constants.SELLABLE)) {
					return house;
				}
			}
		}
		return null;
	}

	public static boolean allHousesButFirstSellable(WorldObject performer, World world) {
		List<Integer> houseIds = performer.getProperty(Constants.BUILDINGS).getIds(BuildingType.HOUSE);
		boolean isFirstHouse = true;
		for(int houseId : houseIds) {
			WorldObject house = world.findWorldObject(Constants.ID, houseId);
			
			if (!isFirstHouse) {
				if (!(house.hasProperty(Constants.SELLABLE) && house.getProperty(Constants.SELLABLE))) {
					return false;
				}
			}
			
			if (isFirstHouse) {
				isFirstHouse = false;
			}
		}
		
		return true;
	}

	public static boolean hasHouseWithBed(WorldObject performer, World world) {
		List<WorldObject> housesWithBed = performer.getProperty(Constants.BUILDINGS).mapToWorldObjects(world, BuildingType.HOUSE, w -> w.getProperty(Constants.INVENTORY).getWorldObjects(Constants.NAME, Item.BED_NAME).size() > 0);
		return housesWithBed.size() > 0;
	}
}
