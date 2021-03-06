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
package org.worldgrower.personality;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.worldgrower.WorldObject;

class PersonalityTraitValue implements Serializable {
	private final PersonalityTrait personalityTrait;
	private int value;
	private List<String> reasons = new ArrayList<>();

	public PersonalityTraitValue(PersonalityTrait personalityTrait) {
		super();
		this.personalityTrait = personalityTrait;
	}
	
	public final int getValue() {
		return value;
	}

	public final void changeValue(int value, String reason) {
		this.value += value;
		
		if (this.value < -1000) {
			this.value = -1000;
		}
		
		if (this.value > 1000) {
			this.value = 1000;
		}
		
		reasons.add(reason);
	}

	public final List<String> getReasons() {
		return reasons;
	}

	public PersonalityTrait getPersonalityTrait() {
		return personalityTrait;
	}

	public void copy(PersonalityTraitValue other) {
		this.value = other.value;
		this.reasons.addAll(other.getReasons());
	}

	public void initialize(WorldObject performer) {
		this.value = personalityTrait.calculateInitialValue(performer);
	}
}