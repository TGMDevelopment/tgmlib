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

package xyz.matthewtgm.tgmlib.tweaker.util;

/**
 * Adapted from SkyBlockAddons under MIT license.
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 *
 * @author Biscuit
 */
public class ReturnValue<R> {

    private boolean cancelled;
    private R value;

    public void cancel(R value) {
        cancelled = true;
        this.value = value;
    }

    public void cancel() {
        cancel(null);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public R getValue() {
        return value;
    }

}