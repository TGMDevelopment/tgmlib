/*
 * Copyright (C) MatthewTGM
 * This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
 *
 * TGMLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TGMLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TGMLib. If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.tgmlib.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ComputerHelper {

    /**
     * @return The string currently copied to the clipboard.
     * @author MatthewTGM
     */
    public static String getClipboardString() {
        try {
            Transferable transferable = getClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
                return (String) transferable.getTransferData(DataFlavor.stringFlavor);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param input The string to set the clipboard's content to.
     * @return Whether or not the process was successful.
     * @author MatthewTGM
     */
    public static boolean setClipboardString(String input) {
        try {
            StringSelection selection = new StringSelection(input);
            getClipboard().setContents(selection, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return The system clipboard.
     * @author MatthewTGM
     */
    public static Clipboard getClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

}