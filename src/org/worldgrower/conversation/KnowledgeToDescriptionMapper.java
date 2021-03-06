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
package org.worldgrower.conversation;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.attribute.EventKnowledge;
import org.worldgrower.attribute.Knowledge;
import org.worldgrower.attribute.Location;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.attribute.PropertyKnowledge;
import org.worldgrower.deity.Deity;
import org.worldgrower.generator.BuildingGenerator;
import org.worldgrower.history.HistoryItem;
import org.worldgrower.profession.Profession;
import org.worldgrower.util.SentenceUtils;

public class KnowledgeToDescriptionMapper {

	public String getQuestionDescription(Knowledge knowledge, World world) {
		return "Did you know that " + getDescription(knowledge, world) + "?";
	}

	private String getDescription(Knowledge knowledge, World world) {
		if (knowledge instanceof PropertyKnowledge) {
			PropertyKnowledge propertyKnowledge = (PropertyKnowledge) knowledge;
			ManagedProperty<?> property = propertyKnowledge.getManagedProperty();
			Object value = propertyKnowledge.getValue();
			if (property == Constants.DEATH_REASON) {
				return (String)value;
			}
			
			WorldObject subject = world.findWorldObjectById(knowledge.getSubjectId());
			if (BuildingGenerator.isWell(subject) && property == Constants.POISON_DAMAGE) {
				return "the well is poisoned";
			} else if (BuildingGenerator.isWell(subject) && property == Constants.SLEEP_INDUCING_DRUG_STRENGTH) {
				return "the well contains sleeping potion";
			} else if (property == Constants.CHILD_BIRTH_ID) {
				WorldObject child = world.findWorldObjectById((Integer) value);
				return subject.getProperty(Constants.NAME) + " gave birth to " + child.getProperty(Constants.NAME);
			} else if (property == Constants.DEITY) {
				if (value != null) {
					return subject.getProperty(Constants.NAME) + " worships " + ((Deity) value).getName();
				} else {
					return subject.getProperty(Constants.NAME) + " doesn't worship a deity";
				}
			} else if (property == Constants.PROFESSION) {
				if (value != null) {
					String professionDescription = ((Profession) value).getDescription();
					String article = SentenceUtils.getArticle(professionDescription);
					return subject.getProperty(Constants.NAME) + " is " + article + " " + professionDescription;
				} else {
					return subject.getProperty(Constants.NAME) + " doesn't have a profession";
				}
			} else if (property == Constants.ORGANIZATION_LEADER_ID) {
				if (value != null) {
					int leaderId = (Integer) value;
					WorldObject leader = world.findWorldObjectById(leaderId);
					return leader.getProperty(Constants.NAME) + " is the leader of the " + subject.getProperty(Constants.NAME);
				} else {
					return subject.getProperty(Constants.NAME) + " doesn't have a leader";
				}
			} else if (property == Constants.LOCATION) {
				Location location = (Location) value;
				return subject.getProperty(Constants.NAME) + " is located at x " + location.getX() + " and y " + location.getY();
			} else if (property == Constants.CREATURE_TYPE) {
				String creatureTypeDescription = subject.getProperty(Constants.CREATURE_TYPE).getDescription();
				String article = SentenceUtils.getArticle(creatureTypeDescription);
				return subject.getProperty(Constants.NAME) + " is " + article + " " + creatureTypeDescription;
			} else if (property == Constants.TRAPPED_CONTAINER_DAMAGE) {
				return subject.getProperty(Constants.NAME) + " is magically trapped";
			} else if (property == Constants.ILLUSION_CREATOR_ID) {
				return subject.getProperty(Constants.NAME) + " is an illusion";
			} else {
				throw new IllegalStateException("No mapping found for property " + property + " and value " + value);
			}
		} else if (knowledge instanceof EventKnowledge) {
			EventKnowledge eventKnowledge = (EventKnowledge) knowledge;
			int historyId = eventKnowledge.getHistoryId();
			HistoryItem historyItem = world.getHistory().getHistoryItem(historyId);
			return historyItem.getThirdPersonDescription(world);
		} else {
			throw new IllegalStateException("No mapping found for knowledge " + knowledge);
		}
	}
	
	public String getStatementDescription(Knowledge knowledge, World world) {
		return getDescription(knowledge, world);
	}
}
