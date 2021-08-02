/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.util.maths;

/**
 * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
public class LerpingInteger {

    private int timeSpent;
    private long lastMillis;
    private int timeToReachTarget;

    private int targetValue;
    private int lerpValue;

    public LerpingInteger(int initialValue, int timeToReachTarget) {
        this.targetValue = this.lerpValue = initialValue;
        this.timeToReachTarget = timeToReachTarget;
    }

    public LerpingInteger(int initialValue) {
        this(initialValue, 200);
    }

    public void tick() {
        int lastTimeSpent = timeSpent;
        this.timeSpent += System.currentTimeMillis() - lastMillis;
        float lastDistPercentToTarget = lastTimeSpent / (float) timeToReachTarget;
        float distPercentToTarget = timeSpent / (float) timeToReachTarget;
        float fac = (1 - lastDistPercentToTarget) / lastDistPercentToTarget;
        int startValue = lerpValue - (int) ((targetValue - lerpValue) / fac);
        int dist = targetValue - startValue;
        if (dist == 0) return;
        int oldLerpValue = lerpValue;
        if (distPercentToTarget >= 1) lerpValue = targetValue;
        else lerpValue = startValue + (int) (dist * distPercentToTarget);
        if (lerpValue == oldLerpValue) timeSpent = lastTimeSpent;
        else this.lastMillis = System.currentTimeMillis();
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void resetTimer() {
        this.timeSpent = 0;
        this.lastMillis = System.currentTimeMillis();
    }

    public void setTimeToReachTarget(int timeToReachTarget) {
        this.timeToReachTarget = timeToReachTarget;
    }

    public int getValue() {
        return lerpValue;
    }

    public void setValue(int value) {
        this.targetValue = this.lerpValue = value;
    }

    public int getTarget() {
        return targetValue;
    }

    public void setTarget(int targetValue) {
        this.targetValue = targetValue;
    }

}