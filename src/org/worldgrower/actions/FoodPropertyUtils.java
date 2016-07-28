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

import org.worldgrower.Constants;
import org.worldgrower.WorldObject;

public class FoodPropertyUtils {

	public static void checkFoodSourceExhausted(WorldObject foodSource) {
		int targetFoodSource = foodSource.getProperty(Constants.FOOD_SOURCE);
		if (targetFoodSource <= 200 && Constants.FOOD_PRODUCED.isAtMax(foodSource)) {
			foodSource.setProperty(Constants.HIT_POINTS, 0);
		}
	}
	
	public static boolean foodSourceHasEnoughFood(WorldObject target) {
		return (target.hasProperty(Constants.FOOD_SOURCE)) && (target.getProperty(Constants.FOOD_SOURCE) >= 100);
	}
}
