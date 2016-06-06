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
package org.worldgrower.gui.inventory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.worldgrower.DungeonMaster;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.WorldPanel;
import org.worldgrower.gui.music.SoundIdReader;

public class GuiBarterAction extends AbstractAction {

	private WorldObject playerCharacter;
	private World world;
	private DungeonMaster dungeonMaster;
	private WorldPanel container;
	private WorldObject target;
	private InventoryDialog dialog;
	private ImageInfoReader imageInfoReader;
	private SoundIdReader soundIdReader;
	
	public GuiBarterAction(WorldObject playerCharacter, World world, DungeonMaster dungeonMaster, WorldPanel container, WorldObject target, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader) {
		super();
		this.playerCharacter = playerCharacter;
		this.world = world;
		this.dungeonMaster = dungeonMaster;
		this.container = container;
		this.target = target;
		this.imageInfoReader = imageInfoReader;
		this.soundIdReader = soundIdReader;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		InventoryActionFactory inventoryActionFactory = new InventoryActionFactory(playerCharacter, imageInfoReader, soundIdReader, world, dungeonMaster, container, target);
		dialog = new InventoryDialog(new InventoryDialogModel(playerCharacter, target), imageInfoReader, soundIdReader, inventoryActionFactory);
		inventoryActionFactory.setDialog(dialog);
		dialog.showMe();
	}
}