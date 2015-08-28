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
package org.worldgrower.gui;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.worldgrower.DungeonMaster;
import org.worldgrower.Main;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.actions.OrganizationNamer;
import org.worldgrower.deity.Deity;
import org.worldgrower.profession.Profession;
import org.worldgrower.profession.Professions;

public class GuiCreateOrganizationAction extends AbstractAction {

	private WorldObject playerCharacter;
	private ImageInfoReader imageInfoReader;
	private World world;
	private WorldPanel parent;
	private DungeonMaster dungeonMaster;
	
	public GuiCreateOrganizationAction(WorldObject playerCharacter, ImageInfoReader imageInfoReader, World world, WorldPanel parent, DungeonMaster dungeonMaster) {
		super();
		this.playerCharacter = playerCharacter;
		this.imageInfoReader = imageInfoReader;
		this.world = world;
		this.parent = parent;
		this.dungeonMaster = dungeonMaster;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] organizationTypes = { "profession", "religion" };
		String organizationType = (String) JOptionPane.showInputDialog(parent, "Choose Organization Type", "Organization Type", JOptionPane.QUESTION_MESSAGE, null, organizationTypes, organizationTypes[0]);
		if (organizationType != null) {
			if (organizationType.equals(organizationTypes[0])) {
				createProfessionOrganization();
			} else if (organizationType.equals(organizationTypes[1])) {
				createReligionOrganization();
			}
		}
	}

	private void createProfessionOrganization() {
		String[] professionNames = Professions.getDescriptions().toArray(new String[0]);
		String professionName = (String) JOptionPane.showInputDialog(parent, "Choose Profession", "Choose Profession", JOptionPane.QUESTION_MESSAGE, null, professionNames, professionNames[0]);
		if (professionName != null) {
			Profession profession = Professions.getProfessionByDescription(professionName);
			int professionIndex = Professions.indexOf(profession);
			
			String[] organizationNames = new OrganizationNamer().getProfessionOrganizationNames(profession, world).toArray(new String[0]);
			String organizationName = (String) JOptionPane.showInputDialog(parent, "Choose Organization name", "Choose Organization name", JOptionPane.QUESTION_MESSAGE, null, organizationNames, organizationNames[0]);
			
			if (organizationName != null) {
				int indexOfOrganization = Arrays.asList(organizationNames).indexOf(organizationName);
				
				Main.executeAction(playerCharacter, Actions.CREATE_PROFESSION_ORGANIZATION_ACTION, new int[] { professionIndex, indexOfOrganization}, world, dungeonMaster, playerCharacter, parent);
			}
		}
	}
	
	private void createReligionOrganization() {
		String[] deityNames = Deity.getNames().toArray(new String[0]);
		String deityName = (String) JOptionPane.showInputDialog(parent, "Choose Deity", "Choose Deity", JOptionPane.QUESTION_MESSAGE, null, deityNames, deityNames[0]);
		if (deityName != null) {
			Deity deity = Deity.getDeityByDescription(deityName);
			int deityIndex = Deity.ALL_DEITIES.indexOf(deity);
			
			String[] organizationNames = new OrganizationNamer().getDeityOrganizationNames(deity, world).toArray(new String[0]);
			String organizationName = (String) JOptionPane.showInputDialog(parent, "Choose Organization name", "Choose Organization name", JOptionPane.QUESTION_MESSAGE, null, organizationNames, organizationNames[0]);
			
			if (organizationName != null) {
				int indexOfOrganization = Arrays.asList(organizationNames).indexOf(organizationName);
				
				List<String> possibleGoalsList = deity.getOrganizationGoalDescriptions();
				possibleGoalsList.add(0, "No goal");
				String[] possibleGoals = possibleGoalsList.toArray(new String[0]);
				String possibleGoal = (String) JOptionPane.showInputDialog(parent, "Choose Goal", "Choose Goal", JOptionPane.QUESTION_MESSAGE, null, possibleGoals, possibleGoals[0]);
				if (possibleGoal != null) {
					int indexOfGoal = deity.getOrganizationGoalDescriptions().indexOf(possibleGoal);
					Main.executeAction(playerCharacter, Actions.CREATE_RELIGION_ORGANIZATION_ACTION, new int[] { deityIndex, indexOfOrganization, indexOfGoal}, world, dungeonMaster, playerCharacter, parent);
				}
			}
		}
	}
}