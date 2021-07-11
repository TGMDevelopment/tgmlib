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

package xyz.matthewtgm.tgmlib.tweaker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.tweaker.transformers.*;

import java.util.Collection;

@IFMLLoadingPlugin.SortingIndex(1001)
public class TGMLibClassTransformer implements IClassTransformer {

    private static boolean created;
    private static Logger logger;
    private static Multimap<String, TGMLibTransformer> transformerMap;

    @Getter private static boolean deobfuscated;
    @Getter private static boolean usingNotchMappings;

    public TGMLibClassTransformer() {
        logger = LogManager.getLogger(TGMLib.NAME + " (TGMLibClassTransformer)");
        transformerMap = ArrayListMultimap.create();
        if (created) return;
        created = true;
        registerTransformer(new AbstractClientPlayerTransformer());
        registerTransformer(new EntityLivingBaseTransformer());
        registerTransformer(new EntityPlayerSPTransformer());
        registerTransformer(new FontRendererTransformer());
        registerTransformer(new GuiIngameForgeTransformer());
        registerTransformer(new GuiNewChatTransformer());
        registerTransformer(new MinecraftTransformer());
        registerTransformer(new NetHandlerPlayClientTransformer());
        registerTransformer(new NetworkManagerTransformer());
        registerTransformer(new RenderTransformer());
        registerTransformer(new TimerTransformer());
    }

    private void registerTransformer(TGMLibTransformer transformer) {
        for (String className : transformer.getClassNames()) transformerMap.put(className, transformer);
    }

    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;
        Collection<TGMLibTransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;
        logger.info("Found {} transformer(s) for {}", transformers.size(), transformedName);
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, 8);
        for (TGMLibTransformer transformer : transformers) {
            logger.info("Applying transformer {} to {}", transformer.getClass().getName(), transformedName);
            transformer.transform(node, transformedName);
        }
        ClassWriter writer = new ClassWriter(3);
        try {
            node.accept(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

    static {
        deobfuscated = false;
        deobfuscated = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        usingNotchMappings = !deobfuscated;
    }

}