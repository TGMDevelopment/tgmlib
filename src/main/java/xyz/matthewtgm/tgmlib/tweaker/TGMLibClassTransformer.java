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
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.tweaker.transformers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(1001)
public class TGMLibClassTransformer implements IClassTransformer {

    private static boolean created;
    private static boolean bytecodeDebug = false;
    private final Logger logger = LogManager.getLogger(TGMLib.NAME + " (TGMLibClassTransformer)");
    private final Multimap<String, TGMLibTransformer> transformerMap = ArrayListMultimap.create();

    public TGMLibClassTransformer() {
        if (created) {
            logger.warn("TGMLibClassTransformer was already created, returning.");
            return;
        }
        created = true;

        registerTransformer(new AbstractClientPlayerTransformer());
        registerTransformer(new BossStatusTransformer());
        registerTransformer(new EntityLivingBaseTransformer());
        registerTransformer(new EntityPlayerSPTransformer());
        registerTransformer(new EntityPlayerTransformer());
        registerTransformer(new FontRendererTransformer());
        registerTransformer(new GuiContainerTransformer());
        registerTransformer(new GuiIngameForgeTransformer());
        registerTransformer(new GuiIngameTransformer());
        registerTransformer(new GuiNewChatTransformer());
        registerTransformer(new GuiScreenTransformer());
        registerTransformer(new MinecraftTransformer());
        registerTransformer(new NBTTagCompoundTransformer());
        registerTransformer(new NetHandlerPlayClientTransformer());
        registerTransformer(new NetworkManagerTransformer());
        registerTransformer(new PositionedSoundTransformer());
        registerTransformer(new RenderTransformer());
    }

    private void registerTransformer(TGMLibTransformer transformer) {
        for (String name : transformer.classes())
            transformerMap.put(name, transformer);
    }

    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null)
            return null;
        Collection<TGMLibTransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty())
            return bytes;
        logger.info("Found {} transformer(s) for {}", transformers.size(), transformedName);
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);
        for (TGMLibTransformer transformer : transformers) {
            logger.info("Applying transformer {} to {}", transformer.getClass().getName(), transformedName);
            transformer.transform(node, transformedName);
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        try {
            node.accept(writer);
        } catch (Exception e) {
            outputBytecode(transformedName, writer);
            e.printStackTrace();
        }
        outputBytecode(transformedName, writer);
        return writer.toByteArray();
    }

    /**
     * Taken from SkyBlockAddons under MIT license
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/development/LICENSE
     *
     * @author Biscuit/Phoube
     */
    private void outputBytecode(String transformedName, ClassWriter writer) {
        try {
            if (!bytecodeDebug)
                return;
            File bytecodeDirectory = new File("bytecode");
            if (!bytecodeDirectory.exists() && !bytecodeDirectory.mkdirs())
                throw new IllegalStateException("Unable to create bytecode storage directory...");
            File bytecodeOutput = new File(bytecodeDirectory, transformedName + ".class");
            if (!bytecodeOutput.exists() && !bytecodeOutput.createNewFile())
                throw new IllegalStateException("Unable to create bytecode output file...");
            FileOutputStream os = new FileOutputStream(bytecodeOutput);
            os.write(writer.toByteArray());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        TGMLibTransformationChecks.check();
    }

}