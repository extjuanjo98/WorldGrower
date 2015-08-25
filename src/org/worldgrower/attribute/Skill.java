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
package org.worldgrower.attribute;

import java.io.Serializable;

public class Skill implements Serializable {

	private static final int USAGE_OFFSET = 22;
	
	private int level;
	private int currentUsageCount;
	private int maxUsageCount;
	private int usageOffset;
	
	// governingAttributeValue has value 0 to 20
	public Skill(int governingAttributeValue) {
		if (governingAttributeValue <= 10) {
			this.level = 0;
			this.usageOffset = USAGE_OFFSET - governingAttributeValue;
			this.currentUsageCount = 0;
			this.maxUsageCount = calculateMaxUsageCount();
		} else {
			this.level = governingAttributeValue - 10;
			this.usageOffset = USAGE_OFFSET - governingAttributeValue;
			this.currentUsageCount = 0;
			this.maxUsageCount = calculateMaxUsageCount();
		}
	}

	private Skill(int level, int currentUsageCount, int maxUsageCount, int usageOffset) {
		super();
		this.level = level;
		this.currentUsageCount = currentUsageCount;
		this.maxUsageCount = maxUsageCount;
		this.usageOffset = usageOffset;
	}

	private int calculateMaxUsageCount() {
		return 3 * level * level + usageOffset;
	}
	
	public void use() {
		currentUsageCount++;
		
		if (currentUsageCount >= maxUsageCount) {
			level++;
			currentUsageCount = 0;
			maxUsageCount = calculateMaxUsageCount();
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getPercentageUntilNextLevelUp() {
		return (currentUsageCount * 100) / maxUsageCount;
	}

	public Skill copy() {
		return new Skill(level, currentUsageCount, maxUsageCount, usageOffset);
	}
	
	@Override
	public String toString() {
		return Integer.toString(level);
	}
}